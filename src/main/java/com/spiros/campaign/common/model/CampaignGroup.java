package com.spiros.campaign.common.model;

import java.util.Set;

public class CampaignGroup extends AbstractTransferObject {

    private String name;

    private Set<Campaign> campaigns;

    private Optimisation optimisation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(Set<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    public Optimisation getOptimisation() {
        return optimisation;
    }

    public void setOptimisation(Optimisation optimisation) {
        this.optimisation = optimisation;
    }
}
