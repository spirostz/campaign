package com.spiros.campaign.common.transformer;

import com.spiros.campaign.persistence.entity.CampaignEntity;
import com.spiros.campaign.persistence.entity.CampaignGroupEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CampaignGroupTransformerTest {

    @Autowired
    CampaignGroupTransformer campaignGroupTransformer;

    @Test
    void fromEntityToTransfer() {
        CampaignGroupEntity campaignGroupEntity = new CampaignGroupEntity();
        CampaignEntity campaign1 = new CampaignEntity();
        CampaignEntity campaign2 = new CampaignEntity();

        campaignGroupEntity.setCampaigns(Arrays.asList(campaign1, campaign2));

    }

    @Test
    void fromTransferToEntity() {
    }
}