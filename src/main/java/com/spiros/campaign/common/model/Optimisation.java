package com.spiros.campaign.common.model;

import com.spiros.campaign.common.enums.OptimisationStatusType;

import java.util.Set;

public class Optimisation extends AbstractTransferObject {

    private CampaignGroup campaignGroup;

    private OptimisationStatusType optimisationStatus;

    private Set<Recommendation> recommendations;

    public CampaignGroup getCampaignGroup() {
        return campaignGroup;
    }

    public void setCampaignGroup(CampaignGroup campaignGroup) {
        this.campaignGroup = campaignGroup;
    }

    public OptimisationStatusType getOptimisationStatus() {
        return optimisationStatus;
    }

    public void setOptimisationStatus(OptimisationStatusType optimisationStatus) {
        this.optimisationStatus = optimisationStatus;
    }

    public Set<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Set<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }
}
