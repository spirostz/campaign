package com.spiros.campaign.common.transformer;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Transforms an Object of type {@link E} to an Object of type {@link T} and vice versa
 *
 * @param <E> Entity Object Type
 * @param <T> Transfer Object Type
 */
public interface EntityTransformer<E, T> {

    Optional<T> fromEntityToTransfer(@Nullable E entity);

    Optional<E> fromTransferToEntity(@Nullable T transfer);

    default List<T> transformListFromEntityToTransfer(@Nullable List<E> campaigns) {
        if (campaigns != null) {
            List<T> transfers = new ArrayList<>();
            campaigns.forEach(e -> transfers.add(fromEntityToTransfer(e).orElse(null)));
            return transfers;
        }
        return null;
    }

}
