package com.spiros.campaign.common.transformer;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.persistence.entity.CampaignEntity;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Transforms an Object of type {@link E} to an Object of type {@link T} and vice versa
 *
 * @param <E> Entity Object Type
 * @param <T> Transfer Object Type
 */
public interface EntityTransformer<E, T> {

    Optional<T> fromEntityToTransfer(@Nullable E entity);

    Optional<E> fromTransferToEntity(@Nullable T transfer);

    default Set<T> transformSetFromEntityToTransfer(@Nullable Set<E> campaigns) {
        if (campaigns != null) {
            Set<T> transfers = new HashSet<>();
            campaigns.forEach(e -> transfers.add(fromEntityToTransfer(e).orElse(null)));
            return transfers;
        }
        return null;
    }

}
