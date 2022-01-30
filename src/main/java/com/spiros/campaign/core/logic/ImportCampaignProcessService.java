package com.spiros.campaign.core.logic;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.common.model.CampaignGroup;
import com.spiros.campaign.common.model.Recommendation;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImportCampaignProcessService {


    public void processIncomingData(@NotNull List<Campaign> campaigns, String campaignGroupName) {

        //TODO: validate no data/ empty campaignGroupName / missing fields


        List<Recommendation> recommendations = enforceRecommendationsToCampaigns(campaigns);

        //TODO:
        //prepareCampaignGroupToPersist();
    }

    //TODO: Test
    @NotNull
    private List<Recommendation> enforceRecommendationsToCampaigns(@NotNull List<Campaign> campaigns) {
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
