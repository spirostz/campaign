package com.spiros.campaign.common.transformer;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.persistence.entity.CampaignEntity;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CampaignTransformer  implements EntityTransformer<CampaignEntity, Campaign>{

    @Autowired
    private CampaignGroupTransformer campaignGroupTransformer;

    @Override
    public Optional<Campaign> fromEntityToTransfer(@Nullable CampaignEntity entity) {
        if (entity != null) {
            Campaign campaign = new Campaign();
            campaign.setId(entity.getId());
            campaign.setCampaignGroup(campaignGroupTransformer
                    .fromEntityToTransfer(entity.getCampaignGroup())
                    .orElse(null));
            campaign.setName(entity.getName());
            campaign.setBudget(entity.getBudget());
            campaign.setImpressions(entity.getImpressions());
            campaign.setRevenue(entity.getRevenue());
            return Optional.of(campaign);
        }
        return Optional.empty();
    }

    @Override
    public Optional<CampaignEntity> fromTransferToEntity(@Nullable Campaign transfer) {
        if (transfer != null) {
            CampaignEntity campaignEntity = new CampaignEntity();
            campaignEntity.setId(transfer.getId());
            campaignEntity.setCampaignGroup(campaignGroupTransformer
                    .fromTransferToEntity(transfer.getCampaignGroup())
                    .orElse(null));
            campaignEntity.setName(transfer.getName());
            campaignEntity.setBudget(transfer.getBudget());
            campaignEntity.setImpressions(transfer.getImpressions());
            campaignEntity.setRevenue(transfer.getRevenue());
            return Optional.of(campaignEntity);
        }
        return Optional.empty();
    }
}
