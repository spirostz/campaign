package com.spiros.campaign.common.model;

import com.spiros.campaign.common.enums.OptimisationStatusType;

import java.util.List;

public class Optimisation extends AbstractTransferObject {

    private CampaignGroup campaignGroup;

    private OptimisationStatusType optimisationStatus;

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

}
