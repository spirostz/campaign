package com.spiros.campaign.view.api.v1;

import com.spiros.campaign.common.model.Recommendation;
import com.spiros.campaign.core.api.OptimisationApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/optimisation")
public class OptimisationController {

    @Autowired
    private OptimisationApiService optimisationApiService;

    @GetMapping(value = "/recommendations/all/{campaignGroupId}")
    public List<Recommendation> retrieveAllRecommendationsByCampaignGroupId(@PathVariable Long campaignGroupId) {
        return optimisationApiService.retrieveAllRecommendationsByCampaignGroupIdIfNotApplied(campaignGroupId);
    }
}
