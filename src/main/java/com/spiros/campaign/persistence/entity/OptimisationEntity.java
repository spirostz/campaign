package com.spiros.campaign.persistence.entity;

import com.spiros.campaign.common.enums.OptimisationStatusType;

import javax.persistence.*;

@Entity
@Table(name = "optimisation")
public class OptimisationEntity extends AbstractEntity {

    private CampaignGroupEntity campaignGroup;

    @Column(name = "optimisation_status")
    @Enumerated(EnumType.STRING)
    private OptimisationStatusType optimisationStatus;

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
}
