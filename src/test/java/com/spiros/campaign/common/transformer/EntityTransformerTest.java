package com.spiros.campaign.common.transformer;

import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EntityTransformerTest {

    SampleTransformer sampleTransformer = new SampleTransformer();

    @Test
    void transformListFromEntityToTransfer() {
        List<SampleEntity> sampleEntityList = Arrays.asList(
                new SampleEntity("A"),
                null,
                new SampleEntity("B")
        );

        List<SampleModel> sampleModelList = sampleTransformer.transformListFromEntityToTransfer(sampleEntityList);
        assertEquals("A", sampleModelList.get(0).getSample());
        assertNull(sampleModelList.get(1));
        assertEquals("B", sampleModelList.get(2).getSample());

        assertNull(sampleTransformer.transformListFromEntityToTransfer(null));
    }

    static class SampleTransformer implements EntityTransformer<SampleEntity, SampleModel> {

        @Override
        public Optional<SampleModel> fromEntityToTransfer(@Nullable SampleEntity entity) {
            if (entity != null) {
                return Optional.of(new SampleModel(entity.getSample()));
            }
            return Optional.empty();
        }

        @Override
        public Optional<SampleEntity> fromTransferToEntity(@Nullable SampleModel transfer) {
            if (transfer != null) {
                return Optional.of(new SampleEntity(transfer.getSample()));
            }
            return Optional.empty();
        }
    }

    static class SampleModel {

        public SampleModel(String sample) {
            this.sample = sample;
        }

        private String sample;

        public String getSample() {
            return sample;
        }

        public void setSample(String sample) {
            this.sample = sample;
        }
    }

    static class SampleEntity {

        public SampleEntity(String sample) {
            this.sample = sample;
        }

        private String sample;

        public String getSample() {
            return sample;
        }

        public void setSample(String sample) {
            this.sample = sample;
        }
    }
}