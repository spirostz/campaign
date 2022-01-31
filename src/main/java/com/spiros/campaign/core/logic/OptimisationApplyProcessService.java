package com.spiros.campaign.core.logic;

import com.spiros.campaign.common.enums.OptimisationStatusType;
import com.spiros.campaign.persistence.entity.CampaignGroupEntity;
import com.spiros.campaign.persistence.repository.CampaignGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class OptimisationApplyProcessService {


    @Autowired
    private CampaignGroupRepo campaignGroupRepo;

    //TODO: test
    @Transactional
    public boolean applyOptimisation(Long campaignGroupId) {

        Optional<CampaignGroupEntity> campaignGroupEntityOptional = campaignGroupRepo.findById(campaignGroupId);
        if (campaignGroupEntityOptional.isPresent()) {
            CampaignGroupEntity campaignGroupEntity = campaignGroupEntityOptional.get();

            boolean notAppliedYet = OptimisationStatusType.NOT_APPLIED
                    .equals(campaignGroupEntity.getOptimisation().getOptimisationStatus());

            if (notAppliedYet) {
                return applyRecommendationsToCampaigns(campaignGroupEntity);
            }
        }
        return false;
    }

    private boolean applyRecommendationsToCampaigns(CampaignGroupEntity campaignGroupEntity) {
        campaignGroupEntity.getCampaigns()
                .forEach(campaignEntity -> campaignEntity
                        .setBudget(campaignEntity.getRecommendation().getRecommendedBudget()));
        campaignGroupEntity.getOptimisation().setOptimisationStatus(OptimisationStatusType.APPLIED);
        return true;
    }
}
