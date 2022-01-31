package com.spiros.campaign.persistence.entity;

import com.spiros.campaign.common.enums.OptimisationStatusType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "optimisation")
@Getter
@Setter
public class OptimisationEntity extends AbstractEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "campaign_group_id", referencedColumnName = "id", nullable = false)
    private CampaignGroupEntity campaignGroup;

    @Column(name = "optimisation_status")
    @Enumerated(EnumType.STRING)
    private OptimisationStatusType optimisationStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "optimisation", cascade = CascadeType.ALL)
    private List<RecommendationEntity> recommendations;

}
