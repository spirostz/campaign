package com.spiros.campaign.core.api;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.Objects;

@SpringBootTest
public abstract class LoadSampleCampaignsForApiServiceTest {

    @Autowired
    private CsvHandlingService csvHandlingService;

    public Long loadSampleCampaigns(String campaignGroupName) throws IOException {
        InputStream inStream = readCsvFileAsInputStream();
        return csvHandlingService.handleCsvFile(inStream, campaignGroupName);
    }

    @NotNull
    private InputStream readCsvFileAsInputStream() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("campaigns_simple.csv")).getFile());
        return new FileInputStream(file);
    }

}
