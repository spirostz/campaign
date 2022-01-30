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
    @JoinColumn(name = "optimisation_id", referencedColumnName = "id")
    private OptimisationEntity optimisation;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    private CampaignEntity campaign;

    @Column(name = "recommended_budget")
    private BigDecimal recommendedBudget;
}
