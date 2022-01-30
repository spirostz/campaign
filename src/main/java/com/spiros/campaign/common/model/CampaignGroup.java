package com.spiros.campaign.common.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CampaignGroup extends AbstractTransferObject {

    private String name;
}
