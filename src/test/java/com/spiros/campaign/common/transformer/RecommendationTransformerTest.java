package com.spiros.campaign.common.transformer;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.common.model.Optimisation;
import com.spiros.campaign.common.model.Recommendation;
import com.spiros.campaign.persistence.entity.CampaignEntity;
import com.spiros.campaign.persistence.entity.OptimisationEntity;
import com.spiros.campaign.persistence.entity.RecommendationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecommendationTransformerTest {

    @Autowired
    RecommendationTransformer recommendationTransformer;

    @Test
    void fromEntityToTransfer() {
        RecommendationEntity recommendationEntity = new RecommendationEntity();
        recommendationEntity.setRecommendedBudget(BigDecimal.ONE);
        recommendationEntity.setOptimisation(new OptimisationEntity());
        recommendationEntity.setCampaign(new CampaignEntity());

        Recommendation result = recommendationTransformer.fromEntityToTransfer(recommendationEntity)
                .orElseThrow(IllegalStateException::new);

        assertEquals( 0, BigDecimal.ONE.compareTo(result.getRecommendedBudget()), "Equal value check");
        assertNotNull(result.getCampaign());
        assertNotNull(result.getOptimisation());

        assertFalse(recommendationTransformer.fromEntityToTransfer(null).isPresent());

    }

    @Test
    void fromTransferToEntity() {
        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendedBudget(BigDecimal.ONE);
        recommendation.setOptimisation(new Optimisation());
        recommendation.setCampaign(new Campaign());

        RecommendationEntity result = recommendationTransformer.fromTransferToEntity(recommendation)
                .orElseThrow(IllegalStateException::new);

        assertEquals( 0, BigDecimal.ONE.compareTo(result.getRecommendedBudget()), "Equal value check");
        assertNotNull(result.getCampaign());
        assertNotNull(result.getOptimisation());

        assertFalse(recommendationTransformer.fromTransferToEntity(null).isPresent());

    }
}