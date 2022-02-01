package com.spiros.campaign.common.transformer;

import com.spiros.campaign.common.model.Recommendation;
import com.spiros.campaign.persistence.entity.RecommendationEntity;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RecommendationTransformer implements EntityTransformer<RecommendationEntity, Recommendation> {

    @Autowired
    private OptimisationTransformer optimisationTransformer;

    @Autowired
    private CampaignTransformer campaignTransformer;

    @Override
    public Optional<Recommendation> fromEntityToTransfer(@Nullable RecommendationEntity entity) {

        if (entity != null) {
            Recommendation recommendation = new Recommendation();
            recommendation.setId(entity.getId());
            recommendation.setRecommendedBudget(entity.getRecommendedBudget());
            recommendation.setCampaign(campaignTransformer.fromEntityToTransfer(entity.getCampaign())
                    .orElse(null));
            recommendation.setOptimisation(optimisationTransformer.fromEntityToTransfer(entity.getOptimisation())
                    .orElse(null));

            return Optional.of(recommendation);
        }

        return Optional.empty();
    }

    @Override
    public Optional<RecommendationEntity> fromTransferToEntity(@Nullable Recommendation transfer) {
        
        if (transfer != null) {
            RecommendationEntity recommendationEntity = new RecommendationEntity();
            recommendationEntity.setId(transfer.getId());
            recommendationEntity.setRecommendedBudget(transfer.getRecommendedBudget());
            recommendationEntity.setCampaign(campaignTransformer.fromTransferToEntity(transfer.getCampaign())
                    .orElse(null));
            recommendationEntity.setOptimisation(optimisationTransformer.fromTransferToEntity(transfer.getOptimisation())
                    .orElse(null));

            return Optional.of(recommendationEntity);
        }

        return Optional.empty();
    }
}
