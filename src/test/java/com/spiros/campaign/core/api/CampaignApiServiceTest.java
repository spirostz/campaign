package com.spiros.campaign.core.api;

import com.spiros.campaign.common.model.Campaign;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CampaignApiServiceTest extends LoadSampleCampaignsForApiServiceTest {

    @Autowired
    private CampaignApiService campaignApiService;

    private Long campaignGroupId;

    @BeforeEach
    public void beforeEach() throws IOException {
        campaignGroupId = loadSampleCampaigns("groupName1");
    }

    @Test
    void retrieveAllCampaignsByCampaignGroupId() {

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