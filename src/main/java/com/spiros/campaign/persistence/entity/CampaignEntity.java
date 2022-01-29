package com.spiros.campaign.persistence.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "campaign")
public class CampaignEntity extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_group_id", referencedColumnName = "id")
    private CampaignGroupEntity campaignGroup;

    private String name;
    private BigDecimal budget;
    private Long impressions;
    private BigDecimal revenue;

    public CampaignGroupEntity getCampaignGroup() {
        return campaignGroup;
    }

    public void setCampaignGroup(CampaignGroupEntity campaignGroup) {
        this.campaignGroup = campaignGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Long getImpressions() {
        return impressions;
    }

    public void setImpressions(Long impressions) {
        this.impressions = impressions;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }
}
