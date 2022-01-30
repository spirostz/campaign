package com.spiros.campaign.core.logic;

import com.spiros.campaign.common.model.Campaign;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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

        assertEquals(0, new BigDecimal("10")
                .compareTo(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign1)));
        assertEquals(0, new BigDecimal("20")
                .compareTo(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign2)));
        assertEquals(0, new BigDecimal("40")
                .compareTo(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign3)));
        assertEquals(0, new BigDecimal("20")
                .compareTo(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign4)));
        assertEquals(0, new BigDecimal("10")
                .compareTo(importCampaignProcessService.calculateRecommendedBudgetForCampaign(campaignList, campaign5)));
    }

    @NotNull
    private Campaign sampleCampaign(BigDecimal budget, Long impressions) {
        Campaign campaign = new Campaign();
        campaign.setBudget(budget);
        campaign.setImpressions(impressions);
        return campaign;
    }
}