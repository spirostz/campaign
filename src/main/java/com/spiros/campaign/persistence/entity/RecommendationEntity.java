package com.spiros.campaign.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "recommendation")
public class RecommendationEntity extends AbstractEntity {

    private OptimisationEntity optimisation;
    private CampaignEntity campaign;

    @Column(name = "recommended_budget")
    private BigDecimal recommendedBudget;
}
