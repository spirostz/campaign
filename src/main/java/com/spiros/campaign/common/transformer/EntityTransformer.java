package com.spiros.campaign.common.transformer;

import org.jetbrains.annotations.Nullable;

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

}
