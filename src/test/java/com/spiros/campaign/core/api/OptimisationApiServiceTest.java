package com.spiros.campaign.core.api;

import com.spiros.campaign.common.model.Recommendation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OptimisationApiServiceTest  extends LoadSampleCampaignsForApiServiceTest {

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
    }

    @Test
    void applyOptimisationIfApplicable() {
    }
}