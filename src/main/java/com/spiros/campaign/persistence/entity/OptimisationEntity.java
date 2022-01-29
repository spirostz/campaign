package com.spiros.campaign.persistence.entity;

import com.spiros.campaign.common.enums.OptimisationStatusType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "optimisation")
public class OptimisationEntity extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "campaign_group_id", referencedColumnName = "id")
    private CampaignGroupEntity campaignGroup;

    @Column(name = "optimisation_status")
    @Enumerated(EnumType.STRING)
    private OptimisationStatusType optimisationStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "optimisation")
    private Set<RecommendationEntity> recommendations;

    public CampaignGroupEntity getCampaignGroup() {
        return campaignGroup;
    }

    public void setCampaignGroup(CampaignGroupEntity campaignGroup) {
        this.campaignGroup = campaignGroup;
    }

    public OptimisationStatusType getOptimisationStatus() {
        return optimisationStatus;
    }

    public void setOptimisationStatus(OptimisationStatusType optimisationStatus) {
        this.optimisationStatus = optimisationStatus;
    }

    public Set<RecommendationEntity> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Set<RecommendationEntity> recommendations) {
        this.recommendations = recommendations;
    }
}
