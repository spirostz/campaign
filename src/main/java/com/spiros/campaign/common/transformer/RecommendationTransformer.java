package com.spiros.campaign.common.transformer;

import com.spiros.campaign.common.model.Recommendation;
import com.spiros.campaign.persistence.entity.RecommendationEntity;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RecommendationTransformer implements EntityTransformer<RecommendationEntity, Recommendation> {
    @Override
    public Optional<Recommendation> fromEntityToTransfer(@Nullable RecommendationEntity entity) {
        return Optional.empty();
    }

    @Override
    public Optional<RecommendationEntity> fromTransferToEntity(@Nullable Recommendation transfer) {
        return Optional.empty();
    }
}
