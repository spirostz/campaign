package com.spiros.campaign.persistence.repository;

import com.spiros.campaign.common.enums.OptimisationStatusType;
import com.spiros.campaign.persistence.entity.CampaignEntity;
import com.spiros.campaign.persistence.entity.CampaignGroupEntity;
import com.spiros.campaign.persistence.entity.OptimisationEntity;
import com.spiros.campaign.persistence.entity.RecommendationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CampaignGroupRepoTest {

    @Autowired
    private CampaignGroupRepo campaignGroupRepo;

    @Test
    @Transactional
    void testSampleCampaignGroup() {

        CampaignGroupEntity campaignGroup = new CampaignGroupEntity();
        campaignGroup.setName("campaignGroupTest");

        CampaignEntity campaign1 = new CampaignEntity();
        campaign1.setCampaignGroup(campaignGroup);

        CampaignEntity campaign2 = new CampaignEntity();
        campaign2.setCampaignGroup(campaignGroup);

        OptimisationEntity optimisation = new OptimisationEntity();
        optimisation.setCampaignGroup(campaignGroup);
        optimisation.setOptimisationStatus(OptimisationStatusType.NOT_APPLIED);

        RecommendationEntity recommendation1 = new RecommendationEntity();
        recommendation1.setCampaign(campaign1);
        recommendation1.setOptimisation(optimisation);
        campaign1.setRecommendation(recommendation1);

        RecommendationEntity recommendation2 = new RecommendationEntity();
        recommendation2.setCampaign(campaign2);
        recommendation2.setOptimisation(optimisation);
        campaign2.setRecommendation(recommendation2);

        campaignGroup.setCampaigns(new HashSet<>(Arrays.asList(campaign1, campaign2)));
        campaignGroup.setOptimisation(optimisation);
        optimisation.setRecommendations(new HashSet<>(Arrays.asList(recommendation1, recommendation2)));

        CampaignGroupEntity campaignGroupSaved = campaignGroupRepo.save(campaignGroup);

        assertTrue(true);
    }
}