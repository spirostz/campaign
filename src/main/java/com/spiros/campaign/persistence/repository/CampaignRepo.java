package com.spiros.campaign.persistence.repository;

import com.spiros.campaign.persistence.entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepo extends JpaRepository<CampaignEntity, Long> {
}
