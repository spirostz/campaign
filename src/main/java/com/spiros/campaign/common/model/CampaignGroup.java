package com.spiros.campaign.common.model;

import java.util.List;

public class CampaignGroup extends AbstractTransferObject {

    private String name;

    private List<Campaign> campaigns;

    private Optimisation optimisation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    public Optimisation getOptimisation() {
        return optimisation;
    }

    public void setOptimisation(Optimisation optimisation) {
        this.optimisation = optimisation;
    }
}
