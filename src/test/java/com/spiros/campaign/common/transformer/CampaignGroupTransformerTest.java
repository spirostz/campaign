package com.spiros.campaign.common.transformer;

import com.spiros.campaign.common.model.CampaignGroup;
import com.spiros.campaign.persistence.entity.CampaignGroupEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class CampaignGroupTransformerTest {

    @Autowired
    CampaignGroupTransformer campaignGroupTransformer;

    @Test
    void fromEntityToTransfer() {
        CampaignGroupEntity campaignGroupEntity = new CampaignGroupEntity();
        campaignGroupEntity.setId(56L);
        campaignGroupEntity.setName("CampaignGroup Sample Name");

        CampaignGroup result = campaignGroupTransformer.fromEntityToTransfer(campaignGroupEntity)
                .orElseThrow(IllegalStateException::new);

        assertEquals(56L, result.getId());
        assertEquals("CampaignGroup Sample Name", result.getName());

        assertFalse(campaignGroupTransformer.fromEntityToTransfer(null).isPresent());

    }

    @Test
    void fromTransferToEntity() {
        CampaignGroup campaignGroup = new CampaignGroup();
        campaignGroup.setId(56L);
        campaignGroup.setName("CampaignGroup Sample Name");

        CampaignGroupEntity result = campaignGroupTransformer.fromTransferToEntity(campaignGroup)
                .orElseThrow(IllegalStateException::new);

        assertEquals(56L, result.getId());
        assertEquals("CampaignGroup Sample Name", result.getName());

        assertFalse(campaignGroupTransformer.fromTransferToEntity(null).isPresent());

    }
}