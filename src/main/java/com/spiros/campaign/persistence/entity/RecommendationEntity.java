package com.spiros.campaign.persistence.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "recommendation")
public class RecommendationEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "optimisation_id", referencedColumnName = "id")
    private OptimisationEntity optimisation;

    @OneToOne
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    private CampaignEntity campaign;

    @Column(name = "recommended_budget")
    private BigDecimal recommendedBudget;

    public OptimisationEntity getOptimisation() {
        return optimisation;
    }

    public void setOptimisation(OptimisationEntity optimisation) {
        this.optimisation = optimisation;
    }

    public CampaignEntity getCampaign() {
        return campaign;
    }

    public void setCampaign(CampaignEntity campaign) {
        this.campaign = campaign;
    }

    public BigDecimal getRecommendedBudget() {
        return recommendedBudget;
    }

    public void setRecommendedBudget(BigDecimal recommendedBudget) {
        this.recommendedBudget = recommendedBudget;
    }
}
