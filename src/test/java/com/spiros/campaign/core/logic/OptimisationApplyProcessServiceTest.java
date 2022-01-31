package com.spiros.campaign.core.logic;

import com.spiros.campaign.common.enums.OptimisationStatusType;
import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.persistence.entity.CampaignEntity;
import com.spiros.campaign.persistence.entity.CampaignGroupEntity;
import com.spiros.campaign.persistence.entity.OptimisationEntity;
import com.spiros.campaign.persistence.entity.RecommendationEntity;
import com.spiros.campaign.persistence.repository.CampaignGroupRepo;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
class OptimisationApplyProcessServiceTest {

    CampaignEntity campaign1 = sampleCampaignEntity(new BigDecimal("20"), new BigDecimal("10"));
    CampaignEntity campaign2 = sampleCampaignEntity(new BigDecimal("20"), new BigDecimal("30"));
    OptimisationEntity optimisationEntity = new OptimisationEntity();
    CampaignGroupEntity campaignGroupEntity = new CampaignGroupEntity();

    @BeforeEach
    public void initEach() {
        optimisationEntity.setOptimisationStatus(OptimisationStatusType.NOT_APPLIED);
        campaignGroupEntity.setOptimisation(optimisationEntity);
        campaignGroupEntity.setCampaigns(Arrays.asList(campaign1, campaign2));
    }

    @InjectMocks
    private OptimisationApplyProcessService optimisationApplyProcessService;

    @Mock
    private CampaignGroupRepo campaignGroupRepo;

    @Test
    void applyOptimisation() {
        doReturn(Optional.of(campaignGroupEntity)).when(campaignGroupRepo).findById(1L);

        assertTrue(optimisationApplyProcessService.applyOptimisation(1L));
        assertEquals(OptimisationStatusType.APPLIED, optimisationEntity.getOptimisationStatus());
        assertThat(campaign1.getBudget())
                .isEqualByComparingTo(new BigDecimal("10"));
        assertThat(campaign2.getBudget())
                .isEqualByComparingTo(new BigDecimal("30"));

        //Already applied
        assertFalse(optimisationApplyProcessService.applyOptimisation(1L));

    }

    @Test
    void applyOptimisation_whenAlreadyAppliedOrGroupNotExist_shouldReturnFalse() {
        doReturn(Optional.of(campaignGroupEntity)).when(campaignGroupRepo).findById(1L);
        doReturn(Optional.empty()).when(campaignGroupRepo).findById(2L);

        assertTrue(optimisationApplyProcessService.applyOptimisation(1L));
        assertFalse(optimisationApplyProcessService.applyOptimisation(1L),"Already applied");

        assertFalse(optimisationApplyProcessService.applyOptimisation(2L),"Group not exists");

    }

    @NotNull
    private CampaignEntity sampleCampaignEntity(BigDecimal budget, BigDecimal recommendation) {
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setBudget(budget);

        RecommendationEntity recommendationEntity = new RecommendationEntity();
        recommendationEntity.setRecommendedBudget(recommendation);
        campaignEntity.setRecommendation(recommendationEntity);

        return campaignEntity;
    }
}