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
import com.spiros.campaign.persistence.repository.OptimisationRepo;
import com.spiros.campaign.view.api.v1.FileController;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Transactional
    public void processIncomingData(@NotNull List<Campaign> campaigns, String campaignGroupName) {

        //TODO: validate no data/ empty campaignGroupName / missing fields

        List<Recommendation> recommendations = enforceRecommendationsToCampaigns(campaigns);

        CampaignGroupEntity campaignGroupEntity = prepareCampaignGroupForPersistence(campaigns, campaignGroupName);
        OptimisationEntity optimisationEntity = prepareOptimisationForPersistence(recommendations);
        optimisationEntity = optimisationRepo.save(optimisationEntity);
        campaignGroupEntity.setOptimisation(optimisationEntity);
        campaignGroupEntity = campaignGroupRepo.save(campaignGroupEntity);
        logger.info("Campaign Group with id: {} and name: {} persisted successfully",
                campaignGroupEntity.getId(),
                campaignGroupEntity.getName());
    }

    private OptimisationEntity prepareOptimisationForPersistence(List<Recommendation> recommendations) {

        List<RecommendationEntity> recommendationEntities = recommendations.stream()
                .map(recommendation -> recommendationTransformer.fromTransferToEntity(recommendation)
                        .orElseThrow(IllegalStateException::new))
                .collect(Collectors.toList());
        OptimisationEntity optimisationEntity = new OptimisationEntity();
        optimisationEntity.setRecommendations(recommendationEntities);
        optimisationEntity.setOptimisationStatus(OptimisationStatusType.NOT_APPLIED);

        return optimisationEntity;
    }

    private CampaignGroupEntity prepareCampaignGroupForPersistence(List<Campaign> campaigns, String campaignGroupName) {

        List<CampaignEntity> campaignEntities = campaigns.stream()
                .map(campaign -> campaignTransformer.fromTransferToEntity(campaign)
                        .orElseThrow(IllegalStateException::new))
                .collect(Collectors.toList());
        CampaignGroupEntity campaignGroupEntity = new CampaignGroupEntity();
        campaignGroupEntity.setName(campaignGroupName);
        campaignGroupEntity.setCampaigns(campaignEntities);

        return campaignGroupEntity;
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

        return impressionsOfExaminedCampaign
                .divide(sumImpressionsOfAllCampaigns, MathContext.DECIMAL128)
                .multiply(sumBudgetOfAllCampaigns);

    }

}
