package com.spiros.campaign.common.transformer;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.common.model.CampaignGroup;
import com.spiros.campaign.common.model.Optimisation;
import com.spiros.campaign.persistence.entity.CampaignEntity;
import com.spiros.campaign.persistence.entity.CampaignGroupEntity;
import com.spiros.campaign.persistence.entity.OptimisationEntity;
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
        campaignGroupEntity.setName("CampaignGroup Sample Name");
        campaignGroupEntity.setCampaigns(Arrays.asList(new CampaignEntity(), new CampaignEntity()));
        campaignGroupEntity.setOptimisation(new OptimisationEntity());

        CampaignGroup result = campaignGroupTransformer.fromEntityToTransfer(campaignGroupEntity)
                .orElseThrow(IllegalStateException::new);

        assertEquals("CampaignGroup Sample Name", result.getName());
        assertEquals(2, result.getCampaigns().size());
        assertNotNull(result.getOptimisation());
    }

    @Test
    void fromTransferToEntity() {
        CampaignGroup campaignGroup = new CampaignGroup();
        campaignGroup.setName("CampaignGroup Sample Name");
        campaignGroup.setCampaigns(Arrays.asList(new Campaign(), new Campaign()));
        campaignGroup.setOptimisation(new Optimisation());

        CampaignGroupEntity result = campaignGroupTransformer.fromTransferToEntity(campaignGroup)
                .orElseThrow(IllegalStateException::new);

        assertEquals("CampaignGroup Sample Name", result.getName());
        assertEquals(2, result.getCampaigns().size());
        assertNotNull(result.getOptimisation());


    }
}