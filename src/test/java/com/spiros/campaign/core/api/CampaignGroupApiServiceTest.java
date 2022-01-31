package com.spiros.campaign.core.api;

import com.spiros.campaign.common.model.CampaignGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CampaignGroupApiServiceTest extends LoadSampleCampaignsForApiServiceTest {

    @Autowired
    private CampaignGroupApiService campaignGroupApiService;

    private Long campaignGroupId1;
    private Long campaignGroupId2;

    @BeforeEach
    public void beforeEach() throws IOException {
        campaignGroupId1 = loadSampleCampaigns("groupName1");
        campaignGroupId2 = loadSampleCampaigns("groupName2");

        //Data will be replaced since same groupName
        campaignGroupId1 = loadSampleCampaigns("groupName1");

    }

    @Test
    void retrieveAllCampaignGroups() {

        List<CampaignGroup> campaignGroupList = campaignGroupApiService.retrieveAllCampaignGroups();

        CampaignGroup campaignGroup1 = campaignGroupList.get(0);
        CampaignGroup campaignGroup2 = campaignGroupList.get(1);

        assertEquals(2, campaignGroupList.size());

        assertEquals(campaignGroupId1, campaignGroup1.getId());
        assertEquals("groupName1", campaignGroup1.getName());

        assertEquals(campaignGroupId2, campaignGroup2.getId());
        assertEquals("groupName2", campaignGroup2.getName());


    }
}