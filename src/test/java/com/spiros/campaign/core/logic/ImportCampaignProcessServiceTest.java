package com.spiros.campaign.core.logic;

import com.spiros.campaign.common.model.Campaign;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImportCampaignProcessServiceTest {


    @InjectMocks
    private ImportCampaignProcessService importCampaignProcessService;

    @Test
    void processIncomingData() {

    }

    @Test
    void calculateRecommendedBudgetForCampaign() {
        Campaign campaign1 = sampleCampaign(new BigDecimal("20"), 100L);
        Campaign campaign2 = sampleCampaign(new BigDecimal("20"), 200L);
        Campaign campaign3 = sampleCampaign(new BigDecimal("20"), 400L);
        Campaign campaign4 = sampleCampaign(new BigDecimal("20"), 200L);
        Campaign campaign5 = sampleCampaign(new BigDecimal("20"), 100L);

        List<Campaign> campaignList = Arrays.asList(
                campaign1,
                campaign2,
                campaign3,
                campaign4,
                campaign5
        );

        assertThat(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign1))
                .isEqualByComparingTo(new BigDecimal("10"));
        assertThat(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign2))
                .isEqualByComparingTo(new BigDecimal("20"));
        assertThat(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign3))
                .isEqualByComparingTo(new BigDecimal("40"));
        assertThat(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign4))
                .isEqualByComparingTo(new BigDecimal("20"));
        assertThat(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign5))
                .isEqualByComparingTo(new BigDecimal("10"));

    }

    @Test
    void calculateRecommendedBudgetForCampaign_withDecimals() {
        Campaign campaign1 = sampleCampaign(new BigDecimal("20.1"), 100L);
        Campaign campaign2 = sampleCampaign(new BigDecimal("20.2"), 200L);
        Campaign campaign3 = sampleCampaign(new BigDecimal("20.3"), 400L);
        Campaign campaign4 = sampleCampaign(new BigDecimal("20.4"), 200L);
        Campaign campaign5 = sampleCampaign(new BigDecimal("20"), 100L);

        List<Campaign> campaignList = Arrays.asList(
                campaign1,
                campaign2,
                campaign3,
                campaign4,
                campaign5
        );

        assertThat(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign1))
                .isEqualByComparingTo(new BigDecimal("10.1"));
        assertThat(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign2))
                .isEqualByComparingTo(new BigDecimal("20.2"));
        assertThat(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign3))
                .isEqualByComparingTo(new BigDecimal("40.4"));
        assertThat(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign4))
                .isEqualByComparingTo(new BigDecimal("20.2"));
        assertThat(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign5))
                .isEqualByComparingTo(new BigDecimal("10.1"));
    }

    @Test
    void calculateRecommendedBudgetForCampaign_withJustOneCampaign() {
        Campaign campaign1 = sampleCampaign(new BigDecimal("20.1123456"), 100L);

        List<Campaign> campaignList = Arrays.asList(
                campaign1
        );

        assertThat(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign1))
                .isEqualByComparingTo(new BigDecimal("20.1123456"));
    }

    @NotNull
    private Campaign sampleCampaign(BigDecimal budget, Long impressions) {
        Campaign campaign = new Campaign();
        campaign.setBudget(budget);
        campaign.setImpressions(impressions);
        return campaign;
    }
}