package com.spiros.campaign.core.api;

import com.spiros.campaign.common.model.Campaign;
import com.spiros.campaign.core.logic.ImportCampaignProcessService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvHandlingService {


    @Autowired
    private ImportCampaignProcessService importCampaignProcessService;

    public void handleCsvFile(InputStream inStream, String campaignGroupName) throws IOException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inStream, StandardCharsets.UTF_8));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);

        //TODO: validate that title exist and its correct

        List<Campaign> campaigns = csvParser.stream()
                .skip(1) //Skip title
                .map(this::mapCsvRecordToCampaign )
                .collect(Collectors.toList());

        importCampaignProcessService.processDataFromCsv(campaigns, campaignGroupName);
    }

    private Campaign mapCsvRecordToCampaign(CSVRecord csvRecord) {
        //TODO:
        return new Campaign();
    }

}
