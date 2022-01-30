package com.spiros.campaign.common.transformer;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.common.model.CampaignGroup;
import com.spiros.campaign.common.model.Recommendation;
import com.spiros.campaign.persistence.entity.CampaignEntity;
import com.spiros.campaign.persistence.entity.CampaignGroupEntity;
import com.spiros.campaign.persistence.entity.RecommendationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CampaignTransformerTest {

    @Autowired
    CampaignTransformer campaignTransformer;

    @Test
    void fromEntityToTransfer() {
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setId(56L);
        campaignEntity.setCampaignGroup(new CampaignGroupEntity());
        campaignEntity.setName("Campaign Test Name");
        campaignEntity.setBudget(BigDecimal.TEN);
        campaignEntity.setImpressions(50L);
        campaignEntity.setRevenue(BigDecimal.ONE);

        Campaign result =  campaignTransformer.fromEntityToTransfer(campaignEntity)
                .orElseThrow(IllegalStateException::new);

        assertNotNull(result.getCampaignGroup());
        assertEquals(56L, result.getId());
        assertEquals("Campaign Test Name", result.getName());
        assertThat(result.getBudget()).isEqualByComparingTo(BigDecimal.TEN);
        assertEquals( 50L, result.getImpressions());
        assertThat(result.getRevenue()).isEqualByComparingTo(BigDecimal.ONE);

        assertFalse(campaignTransformer.fromEntityToTransfer(null).isPresent());
    }

    @Test
    void fromTransferToEntity() {

        Campaign campaign = new Campaign();
        campaign.setId(56L);
        campaign.setCampaignGroup(new CampaignGroup());
        campaign.setName("Campaign Test Name");
        campaign.setBudget(BigDecimal.TEN);
        campaign.setImpressions(50L);
        campaign.setRevenue(BigDecimal.ONE);

        CampaignEntity result =  campaignTransformer.fromTransferToEntity(campaign)
                .orElseThrow(IllegalStateException::new);

        assertNotNull(result.getCampaignGroup());
        assertEquals(56L, result.getId());
        assertEquals("Campaign Test Name", result.getName());
        assertThat(result.getBudget()).isEqualByComparingTo(BigDecimal.TEN);
        assertEquals( 50L, result.getImpressions());
        assertThat(result.getRevenue()).isEqualByComparingTo(BigDecimal.ONE);

        assertFalse(campaignTransformer.fromTransferToEntity(null).isPresent());
    }
}