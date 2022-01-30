package com.spiros.campaign.persistence.repository;

import com.spiros.campaign.persistence.entity.RecommendationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepo extends JpaRepository<RecommendationEntity, Long> {

    List<RecommendationEntity> findAllByOptimisationCampaignGroupId(Long campaignGroupId);

}
