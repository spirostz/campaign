package com.spiros.campaign.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "recommendation")
@Getter
@Setter
public class RecommendationEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "optimisation_id", referencedColumnName = "id", nullable = false)
    private OptimisationEntity optimisation;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "campaign_id", referencedColumnName = "id", nullable = false)
    private CampaignEntity campaign;

    @Column(name = "recommended_budget", precision = 19, scale = 8)
    private BigDecimal recommendedBudget;
}
