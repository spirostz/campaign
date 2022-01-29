package com.spiros.campaign.view.api.v1;

import com.spiros.campaign.common.model.CampaignGroup;
import com.spiros.campaign.core.api.CampaignGroupApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/campaignGroup")
public class CampaignGroupController {

    @Autowired
    private CampaignGroupApiService campaignGroupApiService;

    @GetMapping(value = "/all")
    public List<CampaignGroup> retrieveAllCampaignGroups() {
        return campaignGroupApiService.retrieveAllCampaignGroups();
    }
}
