package com.spiros.campaign.core.api;

import com.spiros.campaign.common.model.CampaignGroup;
import com.spiros.campaign.common.transformer.CampaignGroupTransformer;
import com.spiros.campaign.persistence.entity.CampaignGroupEntity;
import com.spiros.campaign.persistence.repository.CampaignGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class CampaignGroupApiService {

    @Autowired
    private CampaignGroupRepo campaignGroupRepo;

    @Autowired
    private CampaignGroupTransformer campaignGroupTransformer;

    public List<CampaignGroup> retrieveAllCampaignGroups(){

        CampaignGroup campaignGroup = new CampaignGroup();
        campaignGroup.setName("test" + new Random().nextInt());

        CampaignGroupEntity campaignGroupEntity = campaignGroupRepo.save(campaignGroupTransformer.fromTransferToEntity(campaignGroup).get());

        CampaignGroup savedCampaignGroup = campaignGroupTransformer.fromEntityToTransfer(campaignGroupEntity).get();

        //TODO:
        return Arrays.asList(savedCampaignGroup);
    }
}
