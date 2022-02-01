package com.spiros.campaign.core.api;

import com.spiros.campaign.common.model.CampaignGroup;
import com.spiros.campaign.common.transformer.CampaignGroupTransformer;
import com.spiros.campaign.persistence.repository.CampaignGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignGroupApiService {

    @Autowired
    private CampaignGroupRepo campaignGroupRepo;

    @Autowired
    private CampaignGroupTransformer campaignGroupTransformer;

    @Transactional
    public List<CampaignGroup> retrieveAllCampaignGroups() {
        return campaignGroupRepo.findAll().stream().map(campaignGroupEntity ->
                campaignGroupTransformer.fromEntityToTransfer(campaignGroupEntity)
                        .orElseThrow(IllegalStateException::new)
        ).collect(Collectors.toList());
    }
}
