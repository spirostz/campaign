package com.spiros.campaign.common.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
public class Recommendation extends AbstractTransferObject {

    private Optimisation optimisation;
    private Campaign campaign;
    private BigDecimal recommendedBudget;

}
