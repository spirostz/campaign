package com.spiros.campaign.core.api;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.Objects;

@SpringBootTest
public abstract class LoadSampleCampaignsForApiServiceTest {

    @Autowired
    private CsvHandlingService csvHandlingService;

    public Long loadSampleCampaigns() throws IOException {
        InputStream inStream = readCsvFileAsInputStream("campaigns_simple.csv");
        return csvHandlingService.handleCsvFile(inStream, "groupName1");
    }

    @NotNull
    private InputStream readCsvFileAsInputStream(String fileName) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        return new FileInputStream(file);
    }

}
