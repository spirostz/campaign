package com.spiros.campaign.common.transformer;

import com.spiros.campaign.common.model.Optimisation;
import com.spiros.campaign.persistence.entity.OptimisationEntity;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OptimisationTransformer implements EntityTransformer<OptimisationEntity, Optimisation> {

    @Override
    public Optional<Optimisation> fromEntityToTransfer(@Nullable OptimisationEntity entity) {
        //TODO:
        return Optional.of(new Optimisation());
    }

    @Override
    public Optional<OptimisationEntity> fromTransferToEntity(@Nullable Optimisation transfer) {
        return Optional.of(new OptimisationEntity());
    }
}
