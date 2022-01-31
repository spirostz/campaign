package com.spiros.campaign.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "campaign_group")
@Getter
@Setter
public class CampaignGroupEntity extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "campaignGroup", cascade = CascadeType.ALL)
    private List<CampaignEntity> campaigns;

    @OneToOne(mappedBy = "campaignGroup", cascade = CascadeType.ALL)
    private OptimisationEntity optimisation;

}
