package com.spiros.campaign.core.api;

import com.spiros.campaign.common.model.Campaign;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CampaignApiServiceTest extends LoadSampleCampaignsForApiServiceTest {

    @Autowired
    private CsvHandlingService csvHandlingService;

    @Autowired
    private CampaignApiService campaignApiService;

    Long campaignGroupId;

    @BeforeEach
    public void beforeEach() throws IOException {
        campaignGroupId = loadSampleCampaigns();
    }

    @Test
    void retrieveAllCampaignsByCampaignGroupId() throws IOException {

        List<Campaign> campaignList = campaignApiService.retrieveAllCampaignsByCampaignGroupId(campaignGroupId);

        Campaign campaign1 = campaignList.get(0);
        Campaign campaign2 = campaignList.get(1);

        assertEquals("campaign1", campaign1.getName());
        assertThat(campaign1.getBudget()).isEqualByComparingTo(BigDecimal.valueOf(20));
        assertEquals(100L, campaign1.getImpressions());

        assertEquals("campaign2", campaign2.getName());
        assertThat(campaign2.getBudget()).isEqualByComparingTo(BigDecimal.valueOf(30));
        assertEquals(200L, campaign2.getImpressions());

    }

}