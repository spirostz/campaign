package com.spiros.campaign.persistence.repository;

import com.spiros.campaign.persistence.entity.CampaignGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignGroupRepo  extends JpaRepository<CampaignGroupEntity, Long> {
    Optional<CampaignGroupEntity> findFirstByName(String name);
}
