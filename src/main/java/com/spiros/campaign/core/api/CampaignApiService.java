package com.spiros.campaign.core.api;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.common.model.CampaignGroup;
import com.spiros.campaign.common.transformer.CampaignGroupTransformer;
import com.spiros.campaign.common.transformer.CampaignTransformer;
import com.spiros.campaign.persistence.repository.CampaignGroupRepo;
import com.spiros.campaign.persistence.repository.CampaignRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignApiService {

    @Autowired
    private CampaignRepo campaignRepo;

    @Autowired
    private CampaignTransformer campaignTransformer;

    //TODO: test
    public List<Campaign> retrieveAllCampaignsByCampaignGroupId(Long campaignGroupId) {
        return campaignRepo.findAllByCampaignGroupId(campaignGroupId).stream().map(campaignEntity ->
                campaignTransformer.fromEntityToTransfer(campaignEntity)
                        .orElseThrow(IllegalStateException::new)
        ).collect(Collectors.toList());
    }
}
