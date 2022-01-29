package com.spiros.campaign.common.transformer;

import org.jetbrains.annotations.NotNull;
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

    @NotNull
    default List<T> transformListFromEntityToTransfer(@Nullable List<E> entityList) {
        List<T> transfers = new ArrayList<>();
        if (entityList != null) {
            entityList.forEach(e -> transfers.add(fromEntityToTransfer(e).orElse(null)));
        }
        return transfers;
    }

    @NotNull
    default List<E> transformListFromTransferToEntity(@Nullable List<T> modelList) {
        List<E> entities = new ArrayList<>();
        if (modelList != null) {
            modelList.forEach(t -> entities.add(fromTransferToEntity(t).orElse(null)));
        }
        return entities;
    }

}
