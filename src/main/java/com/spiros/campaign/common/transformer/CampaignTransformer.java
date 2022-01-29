package com.spiros.campaign.common.transformer;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.persistence.entity.CampaignEntity;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CampaignTransformer  implements EntityTransformer<CampaignEntity, Campaign>{

    @Override
    public Optional<Campaign> fromEntityToTransfer(@Nullable CampaignEntity entity) {
        return Optional.empty();
    }

    @Override
    public Optional<CampaignEntity> fromTransferToEntity(@Nullable Campaign transfer) {
        return Optional.empty();
    }
}
