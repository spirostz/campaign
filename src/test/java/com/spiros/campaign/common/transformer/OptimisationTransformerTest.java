package com.spiros.campaign.common.transformer;

import com.spiros.campaign.common.enums.OptimisationStatusType;
import com.spiros.campaign.common.model.CampaignGroup;
import com.spiros.campaign.common.model.Optimisation;
import com.spiros.campaign.common.model.Recommendation;
import com.spiros.campaign.persistence.entity.CampaignGroupEntity;
import com.spiros.campaign.persistence.entity.OptimisationEntity;
import com.spiros.campaign.persistence.entity.RecommendationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OptimisationTransformerTest {

    @Autowired
    OptimisationTransformer optimisationTransformer;

    @Test
    void fromEntityToTransfer() {
        OptimisationEntity optimisationEntity = new OptimisationEntity();
        optimisationEntity.setOptimisationStatus(OptimisationStatusType.APPLIED);
        optimisationEntity.setCampaignGroup(new CampaignGroupEntity());
        optimisationEntity.setRecommendations(Arrays.asList(new RecommendationEntity(), new RecommendationEntity()));

        Optimisation result = optimisationTransformer.fromEntityToTransfer(optimisationEntity)
                .orElseThrow(IllegalStateException::new);

        assertEquals(OptimisationStatusType.APPLIED, result.getOptimisationStatus());
        assertNotNull(result.getCampaignGroup());
        assertEquals(2, result.getRecommendations().size());

        assertFalse(optimisationTransformer.fromEntityToTransfer(null).isPresent());

    }

    @Test
    void fromTransferToEntity() {
        Optimisation optimisation = new Optimisation();
        optimisation.setOptimisationStatus(OptimisationStatusType.APPLIED);
        optimisation.setCampaignGroup(new CampaignGroup());
        optimisation.setRecommendations(Arrays.asList(new Recommendation(), new Recommendation()));

        OptimisationEntity result = optimisationTransformer.fromTransferToEntity(optimisation)
                .orElseThrow(IllegalStateException::new);

        assertEquals(OptimisationStatusType.APPLIED, result.getOptimisationStatus());
        assertNotNull(result.getCampaignGroup());
        assertEquals(2, result.getRecommendations().size());

        assertFalse(optimisationTransformer.fromTransferToEntity(null).isPresent());

    }
}