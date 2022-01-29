package com.spiros.campaign.persistence.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "campaign_group")
public class CampaignGroupEntity extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campaignGroup")
    Set<CampaignEntity> campaignEntities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campaignGroup")
    Set<OptimisationEntity> optimisationEntities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CampaignEntity> getCampaignEntities() {
        return campaignEntities;
    }

    public void setCampaignEntities(Set<CampaignEntity> campaignEntities) {
        this.campaignEntities = campaignEntities;
    }

    public Set<OptimisationEntity> getOptimisationEntities() {
        return optimisationEntities;
    }

    public void setOptimisationEntities(Set<OptimisationEntity> optimisationEntities) {
        this.optimisationEntities = optimisationEntities;
    }
}
