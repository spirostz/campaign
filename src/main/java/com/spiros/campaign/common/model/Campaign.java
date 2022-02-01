package com.spiros.campaign.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Campaign extends AbstractTransferObject {

    @JsonIgnore
    private CampaignGroup campaignGroup;
    private String name;
    private BigDecimal budget;
    private Long impressions;
    private BigDecimal revenue;
}
