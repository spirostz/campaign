package com.spiros.campaign.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "campaign")
@Getter
@Setter
public class CampaignEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "campaign_group_id", referencedColumnName = "id", nullable = false)
    private CampaignGroupEntity campaignGroup;

    private String name;
    private BigDecimal budget;
    private Long impressions;
    private BigDecimal revenue;

    @OneToOne(mappedBy = "campaign", cascade=CascadeType.ALL)
    private RecommendationEntity recommendation;

}
