package com.spiros.campaign.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Recommendation extends AbstractTransferObject {

    @JsonIgnore
    private Optimisation optimisation;
    private Campaign campaign;
    private BigDecimal recommendedBudget;

}
