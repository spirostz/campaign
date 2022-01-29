package com.spiros.campaign.persistence.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CampaignGroupRepoTest {

    @Autowired
    private CampaignGroupRepo campaignGroupRepo;


    @Test
    void name() {
        assertTrue(true);
    }
}