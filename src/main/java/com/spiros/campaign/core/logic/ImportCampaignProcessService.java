package com.spiros.campaign.core.logic;

import com.spiros.campaign.common.enums.OptimisationStatusType;
import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.common.model.Recommendation;
import com.spiros.campaign.common.transformer.CampaignTransformer;
import com.spiros.campaign.common.transformer.RecommendationTransformer;
import com.spiros.campaign.persistence.entity.CampaignGroupEntity;
import com.spiros.campaign.persistence.entity.OptimisationEntity;
import com.spiros.campaign.persistence.entity.RecommendationEntity;
import com.spiros.campaign.persistence.repository.CampaignGroupRepo;
import com.spiros.campaign.persistence.repository.CampaignRepo;
import com.spiros.campaign.persistence.repository.OptimisationRepo;
import com.spiros.campaign.persistence.repository.RecommendationRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImportCampaignProcessService {

    @Autowired
    private CampaignTransformer campaignTransformer;

    @Autowired
    private RecommendationTransformer recommendationTransformer;

    @Autowired
    private CampaignGroupRepo campaignGroupRepo;

    @Autowired
    private OptimisationRepo optimisationRepo;

    @Autowired
    private CampaignRepo campaignRepo;

    @Autowired
    private RecommendationRepo recommendationRepo;

    @Transactional
    public void processIncomingData(@NotNull List<Campaign> campaigns, String campaignGroupName) {

        //TODO: validate no data/ empty campaignGroupName / missing fields

        deleteCampaignGroupIfExists(campaignGroupName);

        List<Recommendation> recommendations = enforceRecommendationsToCampaigns(campaigns);

        CampaignGroupEntity campaignGroupEntity = prepareDataForPersistence(campaignGroupName, recommendations);

        //TODO: Decimal places global

        campaignGroupRepo.save(campaignGroupEntity);
    }

    private void deleteCampaignGroupIfExists(String campaignGroupName) {
        Optional<CampaignGroupEntity> existingCampaignGroup = campaignGroupRepo.findFirstByName(campaignGroupName);
        existingCampaignGroup.ifPresent(campaignGroupEntity -> {
            campaignGroupRepo.delete(campaignGroupEntity);
            campaignGroupRepo.flush();
        });
    }

    @NotNull
    private CampaignGroupEntity prepareDataForPersistence(String campaignGroupName, List<Recommendation> recommendations) {
        CampaignGroupEntity campaignGroupEntity = new CampaignGroupEntity();
        campaignGroupEntity.setName(campaignGroupName);

        OptimisationEntity optimisationEntity = prepareOptimisationForPersistence(recommendations, campaignGroupEntity);

        optimisationEntity.setCampaignGroup(campaignGroupEntity);
        campaignGroupEntity.setOptimisation(optimisationEntity);

        campaignGroupEntity.setCampaigns(optimisationEntity.getRecommendations().stream()
                .map(RecommendationEntity::getCampaign)
                .collect(Collectors.toList()));
        return campaignGroupEntity;
    }

    private OptimisationEntity prepareOptimisationForPersistence(
            List<Recommendation> recommendations,
            CampaignGroupEntity campaignGroupEntity) {

        OptimisationEntity optimisationEntity = new OptimisationEntity();

        List<RecommendationEntity> recommendationEntities = recommendations.stream()
                .map(recommendation -> recommendationTransformer.fromTransferToEntity(recommendation)
                        .orElseThrow(IllegalStateException::new))
                .collect(Collectors.toList());

        recommendationEntities.forEach(recommendationEntity -> {
            recommendationEntity.setOptimisation(optimisationEntity);
            recommendationEntity.getCampaign().setCampaignGroup(campaignGroupEntity);
        });

        optimisationEntity.setRecommendations(recommendationEntities);
        optimisationEntity.setOptimisationStatus(OptimisationStatusType.NOT_APPLIED);

        return optimisationEntity;
    }

    @NotNull
    List<Recommendation> enforceRecommendationsToCampaigns(@NotNull List<Campaign> campaigns) {
        List<Recommendation> recommendations = new ArrayList<>();
        campaigns.forEach(campaign -> {
            BigDecimal recommendedBudget = calculateRecommendedBudgetForCampaign(campaigns, campaign);
            Recommendation recommendation = new Recommendation();
            recommendation.setCampaign(campaign);
            recommendation.setRecommendedBudget(recommendedBudget);
            recommendations.add(recommendation);
        });

        return recommendations;
    }

    /**
     * Formula to calculate recommendations based on math function:
     * Budgets[x] = (Impressions[x] / sum(impressions)) * sum(budgets)
     */
    BigDecimal calculateRecommendedBudgetForCampaign(
            @NotNull List<Campaign> allCampaignsInTheGroup,
            @NotNull Campaign examinedCampaign) {

        BigDecimal sumBudgetOfAllCampaigns = allCampaignsInTheGroup.stream()
                .map(Campaign::getBudget)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal sumImpressionsOfAllCampaigns = BigDecimal.valueOf(
                allCampaignsInTheGroup.stream()
                        .map(Campaign::getImpressions)
                        .mapToLong(Long::longValue).sum()
        );

        BigDecimal impressionsOfExaminedCampaign = BigDecimal.valueOf(examinedCampaign.getImpressions());

        return impressionsOfExaminedCampaign
                .divide(sumImpressionsOfAllCampaigns, MathContext.DECIMAL128)
                .multiply(sumBudgetOfAllCampaigns);

    }

}
