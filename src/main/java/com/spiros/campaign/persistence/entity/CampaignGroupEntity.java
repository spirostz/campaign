package com.spiros.campaign.persistence.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "campaign_group")
public class CampaignGroupEntity extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campaignGroup")
    Set<CampaignEntity> campaigns;

    @OneToOne(mappedBy = "campaignGroup")
    OptimisationEntity optimisation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CampaignEntity> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(Set<CampaignEntity> campaigns) {
        this.campaigns = campaigns;
    }

    public OptimisationEntity getOptimisation() {
        return optimisation;
    }

    public void setOptimisation(OptimisationEntity optimisation) {
        this.optimisation = optimisation;
    }
}
