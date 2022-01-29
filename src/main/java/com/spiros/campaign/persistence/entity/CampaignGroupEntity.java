package com.spiros.campaign.persistence.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "campaign_group")
public class CampaignGroupEntity extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campaignGroup")
    private List<CampaignEntity> campaigns;

    @OneToOne(mappedBy = "campaignGroup")
    private OptimisationEntity optimisation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CampaignEntity> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<CampaignEntity> campaigns) {
        this.campaigns = campaigns;
    }

    public OptimisationEntity getOptimisation() {
        return optimisation;
    }

    public void setOptimisation(OptimisationEntity optimisation) {
        this.optimisation = optimisation;
    }
}
