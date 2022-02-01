package com.spiros.campaign.core.api;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.common.model.Recommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OptimisationApiServiceTest extends LoadSampleCampaignsForApiServiceTest {

    @Autowired
    private OptimisationApiService optimisationApiService;

    private Long campaignGroupId;

    @BeforeEach
    public void beforeEach() throws IOException {
        campaignGroupId = loadSampleCampaigns("groupName1");
    }

    @Test
    void retrieveAllRecommendationsByCampaignGroupIdIfNotApplied() {

        List<Recommendation> recommendationList = optimisationApiService
                .retrieveAllRecommendationsByCampaignGroupIdIfNotApplied(campaignGroupId);

        Recommendation recommendation1 = recommendationList.get(0);
        Recommendation recommendation2 = recommendationList.get(1);

        assertEquals("campaign1", recommendation1.getCampaign().getName());
        assertThat(recommendation1.getRecommendedBudget()).isNotEqualByComparingTo(recommendation1.getCampaign().getBudget());

        assertEquals("campaign2", recommendation2.getCampaign().getName());
        assertThat(recommendation2.getRecommendedBudget()).isNotEqualByComparingTo(recommendation2.getCampaign().getBudget());

        //Apply Recommendations
        optimisationApiService.applyOptimisationIfApplicable(campaignGroupId);
        recommendationList = optimisationApiService
                .retrieveAllRecommendationsByCampaignGroupIdIfNotApplied(campaignGroupId);

        assertTrue(recommendationList.isEmpty(), "Nothing should be there after recommendations been applied");

    }

    @Test
    void retrieveLatestOptimisationsByCampaignGroupId() {

        List<Campaign> campaignList = optimisationApiService.retrieveLatestOptimisationsByCampaignGroupId(campaignGroupId);

        Campaign campaign1 = campaignList.get(0);
        Campaign campaign2 = campaignList.get(1);

        assertEquals("campaign1", campaign1.getName());
        assertThat(campaign1.getBudget()).isEqualByComparingTo(BigDecimal.valueOf(20));
        assertEquals(100L, campaign1.getImpressions());

        assertEquals("campaign2", campaign2.getName());
        assertThat(campaign2.getBudget()).isEqualByComparingTo(BigDecimal.valueOf(30));
        assertEquals(200L, campaign2.getImpressions());

        //Apply Recommendations
        optimisationApiService.applyOptimisationIfApplicable(campaignGroupId);
        campaignList = optimisationApiService.retrieveLatestOptimisationsByCampaignGroupId(campaignGroupId);

        campaign1 = campaignList.get(0);
        campaign2 = campaignList.get(1);

        assertEquals("campaign1", campaign1.getName());
        assertThat(campaign1.getBudget()).isNotEqualByComparingTo(BigDecimal.valueOf(20));
        assertEquals(100L, campaign1.getImpressions());

        assertEquals("campaign2", campaign2.getName());
        assertThat(campaign2.getBudget()).isNotEqualByComparingTo(BigDecimal.valueOf(30));
        assertEquals(200L, campaign2.getImpressions());

    }

    @Test
    void applyOptimisationIfApplicable() {

        assertTrue(optimisationApiService.applyOptimisationIfApplicable(campaignGroupId));

        assertFalse(optimisationApiService.applyOptimisationIfApplicable(campaignGroupId)
                , "Apply Recommendations for second time");

        assertFalse(optimisationApiService.applyOptimisationIfApplicable(123L)
                , "Apply Recommendations for second time");
    }
}