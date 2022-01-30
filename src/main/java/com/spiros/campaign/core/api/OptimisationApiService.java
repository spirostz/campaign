package com.spiros.campaign.core.api;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.common.model.Recommendation;
import com.spiros.campaign.common.transformer.CampaignTransformer;
import com.spiros.campaign.common.transformer.RecommendationTransformer;
import com.spiros.campaign.persistence.repository.CampaignRepo;
import com.spiros.campaign.persistence.repository.RecommendationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptimisationApiService {

    @Autowired
    private RecommendationRepo recommendationRepo;

    @Autowired
    private RecommendationTransformer recommendationTransformer;



    public List<Recommendation> retrieveAllRecommendationsByCampaignGroupId(Long campaignGroupId) {

        return recommendationRepo.findAllByOptimisationCampaignGroupId(campaignGroupId).stream().map(recommendationEntity ->
                recommendationTransformer.fromEntityToTransfer(recommendationEntity)
                        .orElseThrow(IllegalStateException::new)
        ).collect(Collectors.toList());
    }
}
