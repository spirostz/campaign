package com.spiros.campaign.common.model;

import java.math.BigDecimal;

public class Recommendation extends AbstractTransferObject {

    private Optimisation optimisation;

    private Campaign campaign;

    private BigDecimal recommendedBudget;

    public Optimisation getOptimisation() {
        return optimisation;
    }

    public void setOptimisation(Optimisation optimisation) {
        this.optimisation = optimisation;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public BigDecimal getRecommendedBudget() {
        return recommendedBudget;
    }

    public void setRecommendedBudget(BigDecimal recommendedBudget) {
        this.recommendedBudget = recommendedBudget;
    }
}
