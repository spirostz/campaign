package com.spiros.campaign.common.model;

import com.spiros.campaign.common.enums.OptimisationStatusType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Optimisation extends AbstractTransferObject {

    private CampaignGroup campaignGroup;
    private OptimisationStatusType optimisationStatus;
}
