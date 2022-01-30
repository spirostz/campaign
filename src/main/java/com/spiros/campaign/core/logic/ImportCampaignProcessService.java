package com.spiros.campaign.core.logic;

import com.spiros.campaign.common.model.Campaign;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImportCampaignProcessService {


    public void processIncomingData(@NotNull List<Campaign> campaigns, String campaignGroupName) {

        //TODO: validate no data/ empty campaignGroupName / missing fields


        enforceRecommendationsToCampaigns(campaigns);

    }

    private void enforceRecommendationsToCampaigns(List<Campaign> campaigns) {

        //TODO

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
