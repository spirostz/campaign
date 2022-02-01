package com.spiros.campaign.common.transformer;

import com.spiros.campaign.common.model.CampaignGroup;
import com.spiros.campaign.persistence.entity.CampaignGroupEntity;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CampaignGroupTransformer implements EntityTransformer<CampaignGroupEntity, CampaignGroup> {

    @Override
    public Optional<CampaignGroup> fromEntityToTransfer(@Nullable CampaignGroupEntity entity) {

        if (entity != null) {
            CampaignGroup campaignGroup = new CampaignGroup();
            campaignGroup.setId(entity.getId());
            campaignGroup.setName(entity.getName());
            return Optional.of(campaignGroup);
        }

        return Optional.empty();
    }

    @Override
    public Optional<CampaignGroupEntity> fromTransferToEntity(@Nullable CampaignGroup transfer) {

        if (transfer != null) {
            CampaignGroupEntity campaignGroupEntity = new CampaignGroupEntity();
            campaignGroupEntity.setId(transfer.getId());
            campaignGroupEntity.setName(transfer.getName());
            return Optional.of(campaignGroupEntity);
        }

        return Optional.empty();
    }

}
