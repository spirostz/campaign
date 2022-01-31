package com.spiros.campaign.core.api;

import com.spiros.campaign.common.enums.OptimisationStatusType;
import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.common.model.Recommendation;
import com.spiros.campaign.common.transformer.RecommendationTransformer;
import com.spiros.campaign.core.logic.OptimisationApplyProcessService;
import com.spiros.campaign.persistence.entity.RecommendationEntity;
import com.spiros.campaign.persistence.repository.RecommendationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptimisationApiService {

    @Autowired
    private RecommendationRepo recommendationRepo;

    @Autowired
    private RecommendationTransformer recommendationTransformer;

    @Autowired
    private CampaignApiService campaignApiService;

    @Autowired
    private OptimisationApplyProcessService optimisationApplyProcessService;

    @Transactional
    public List<Recommendation> retrieveAllRecommendationsByCampaignGroupIdIfNotApplied(Long campaignGroupId) {

        List<RecommendationEntity> recommendationEntities = recommendationRepo
                .findAllByOptimisationCampaignGroupIdAndOptimisationOptimisationStatus(campaignGroupId,
                        OptimisationStatusType.NOT_APPLIED);

        return recommendationEntities.stream().map(recommendationEntity ->
                recommendationTransformer.fromEntityToTransfer(recommendationEntity)
                        .orElseThrow(IllegalStateException::new)
        ).collect(Collectors.toList());
    }

    @Transactional
    public List<Campaign> retrieveLatestOptimisationsByCampaignGroupId(Long campaignGroupId) {
        return campaignApiService.retrieveAllCampaignsByCampaignGroupId(campaignGroupId);
    }

    @Transactional
    public boolean applyOptimisationIfApplicable(Long campaignGroupId) {
       return optimisationApplyProcessService.applyOptimisation(campaignGroupId);
    }
}
