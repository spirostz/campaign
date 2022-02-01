package com.spiros.campaign.view.api.v1;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.core.api.CampaignApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/campaign")
public class CampaignController {

    @Autowired
    private CampaignApiService campaignApiService;

    @GetMapping(value = "/all/{campaignGroupId}")
    public List<Campaign> retrieveAllCampaignsByCampaignGroupId(@PathVariable Long campaignGroupId) {
        return campaignApiService.retrieveAllCampaignsByCampaignGroupId(campaignGroupId);
    }
}
