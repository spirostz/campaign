package com.spiros.campaign.persistence.repository;

import com.spiros.campaign.persistence.entity.OptimisationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptimisationRepo extends JpaRepository<OptimisationEntity, Long> {
}
