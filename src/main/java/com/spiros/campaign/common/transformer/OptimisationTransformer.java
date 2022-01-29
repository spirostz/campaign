package com.spiros.campaign.common.transformer;

import com.spiros.campaign.common.model.Optimisation;
import com.spiros.campaign.persistence.entity.OptimisationEntity;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OptimisationTransformer implements EntityTransformer<OptimisationEntity, Optimisation> {

    @Autowired
    private CampaignGroupTransformer campaignGroupTransformer;

    @Autowired
    private RecommendationTransformer recommendationTransformer;

    @Override
    public Optional<Optimisation> fromEntityToTransfer(@Nullable OptimisationEntity entity) {

        if (entity != null) {
            Optimisation optimisation = new Optimisation();
            optimisation.setOptimisationStatus(entity.getOptimisationStatus());
            optimisation.setCampaignGroup(campaignGroupTransformer
                    .fromEntityToTransfer(entity.getCampaignGroup())
                    .orElse(null));
            optimisation.setRecommendations(recommendationTransformer
                    .transformListFromEntityToTransfer(entity.getRecommendations()));
            return Optional.of(optimisation);

        }
        return Optional.empty();
    }

    @Override
    public Optional<OptimisationEntity> fromTransferToEntity(@Nullable Optimisation transfer) {
        if (transfer != null) {
            OptimisationEntity optimisationEntity = new OptimisationEntity();
            optimisationEntity.setOptimisationStatus(transfer.getOptimisationStatus());
            optimisationEntity.setCampaignGroup(campaignGroupTransformer
                    .fromTransferToEntity(transfer.getCampaignGroup())
                    .orElse(null));
            optimisationEntity.setRecommendations(recommendationTransformer
                    .transformListFromTransferToEntity(transfer.getRecommendations()));
            return Optional.of(optimisationEntity);

        }
        return Optional.empty();
    }
}
