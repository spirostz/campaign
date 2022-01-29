package com.spiros.campaign.persistence.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "campaign_group")
public class CampaignGroupEntity extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    Set<CampaignEntity> campaignEntities;

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
}
