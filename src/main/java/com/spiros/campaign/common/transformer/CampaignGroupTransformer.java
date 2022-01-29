package com.spiros.campaign.common.transformer;

import com.spiros.campaign.common.model.CampaignGroup;
import com.spiros.campaign.persistence.entity.CampaignGroupEntity;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CampaignGroupTransformer implements EntityTransformer<CampaignGroupEntity, CampaignGroup> {

    @Autowired
    private CampaignTransformer campaignTransformer;

    @Autowired
    private OptimisationTransformer optimisationTransformer;

    @Override
    public Optional<CampaignGroup> fromEntityToTransfer(@Nullable CampaignGroupEntity entity) {

        if (entity != null) {
            CampaignGroup campaignGroup = new CampaignGroup();
            campaignGroup.setName(entity.getName());
            campaignGroup.setCampaigns(campaignTransformer
                    .transformListFromEntityToTransfer(entity.getCampaigns()));
            campaignGroup.setOptimisation(optimisationTransformer
                    .fromEntityToTransfer(entity.getOptimisation())
                    .orElse(null));
            return Optional.of(campaignGroup);
        }

        return Optional.empty();
    }

    @Override
    public Optional<CampaignGroupEntity> fromTransferToEntity(@Nullable CampaignGroup transfer) {

        if (transfer != null) {
            CampaignGroupEntity campaignGroupEntity = new CampaignGroupEntity();
            campaignGroupEntity.setName(transfer.getName());
            campaignGroupEntity.setCampaigns(campaignTransformer
                    .transformListFromTransferToEntity(transfer.getCampaigns()));
            campaignGroupEntity.setOptimisation(optimisationTransformer
                    .fromTransferToEntity(transfer.getOptimisation())
                    .orElse(null));
            return Optional.of(campaignGroupEntity);
        }

        return Optional.empty();
    }



}
