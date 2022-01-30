package com.spiros.campaign.core.logic;

import com.spiros.campaign.common.enums.OptimisationStatusType;
import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.common.model.Recommendation;
import com.spiros.campaign.common.transformer.CampaignTransformer;
import com.spiros.campaign.common.transformer.RecommendationTransformer;
import com.spiros.campaign.persistence.entity.CampaignEntity;
import com.spiros.campaign.persistence.entity.CampaignGroupEntity;
import com.spiros.campaign.persistence.entity.OptimisationEntity;
import com.spiros.campaign.persistence.entity.RecommendationEntity;
import com.spiros.campaign.persistence.repository.CampaignGroupRepo;
import com.spiros.campaign.persistence.repository.CampaignRepo;
import com.spiros.campaign.persistence.repository.OptimisationRepo;
import com.spiros.campaign.persistence.repository.RecommendationRepo;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImportCampaignProcessService {

    Logger logger = LoggerFactory.getLogger(ImportCampaignProcessService.class);

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
        final CampaignGroupEntity campaignGroupEntity = new CampaignGroupEntity();
        List<Recommendation> recommendations = enforceRecommendationsToCampaigns(campaigns);
        campaignGroupEntity.setName(campaignGroupName);
        //List<CampaignEntity> campaignEntities = prepareCampaignForPersistence(campaigns, campaignGroupEntity);

        OptimisationEntity optimisationEntity = prepareOptimisationForPersistence(recommendations, campaignGroupEntity);

        optimisationEntity.setCampaignGroup(campaignGroupEntity);
        campaignGroupEntity.setOptimisation(optimisationEntity);


        campaignGroupEntity.setCampaigns(null);

        CampaignGroupEntity savedCampaignGroupEntity = campaignGroupRepo.save(campaignGroupEntity);

        //TODO: Decimal places global

        //Use try catch since is always visible
        logger.info("Campaign Group with id: {} and name: {} persisted successfully",
                savedCampaignGroupEntity.getId(),
                savedCampaignGroupEntity.getName());
    }

    private OptimisationEntity prepareOptimisationForPersistence(List<Recommendation> recommendations, CampaignGroupEntity campaignGroupEntity) {

        List<RecommendationEntity> recommendationEntities = recommendations.stream()
                .map(recommendation -> recommendationTransformer.fromTransferToEntity(recommendation)
                        .orElseThrow(IllegalStateException::new))
                .collect(Collectors.toList());

        recommendationEntities.forEach(recommendationEntity -> recommendationEntity.getCampaign().setCampaignGroup(campaignGroupEntity));

        OptimisationEntity optimisationEntity = new OptimisationEntity();
        optimisationEntity.setRecommendations(recommendationEntities);
        optimisationEntity.setOptimisationStatus(OptimisationStatusType.NOT_APPLIED);

        return optimisationEntity;
    }

    private List<CampaignEntity> prepareCampaignForPersistence(List<Campaign> campaigns, CampaignGroupEntity campaignGroupEntity) {

        List<CampaignEntity> campaignEntities = campaigns.stream()
                .map(campaign -> campaignTransformer.fromTransferToEntity(campaign)
                        .orElseThrow(IllegalStateException::new))
                .collect(Collectors.toList());

        campaignEntities.forEach(campaignEntity -> campaignEntity.setCampaignGroup(campaignGroupEntity));

        return campaignEntities;
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

        //Budgets[x] = (Impressions[x] / sum(impressions)) * sum(budgets)
        return impressionsOfExaminedCampaign
                .divide(sumImpressionsOfAllCampaigns, MathContext.DECIMAL128)
                .multiply(sumBudgetOfAllCampaigns);

    }

}
