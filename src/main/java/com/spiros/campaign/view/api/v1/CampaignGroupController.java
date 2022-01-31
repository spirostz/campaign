package com.spiros.campaign.view.api.v1;

import com.spiros.campaign.common.model.CampaignGroup;
import com.spiros.campaign.core.api.CampaignGroupApiService;
import com.spiros.campaign.core.api.CsvHandlingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//TODO: @RestControllerAdvice

@RestController
@RequestMapping("/api/v1/campaignGroup")
public class CampaignGroupController {

    Logger logger = LoggerFactory.getLogger(CampaignGroupController.class);

    @Autowired
    private CampaignGroupApiService campaignGroupApiService;

    @Autowired
    private CsvHandlingService csvHandlingService;

    @GetMapping(value = "/all")
    public List<CampaignGroup> retrieveAllCampaignGroups() {
        return campaignGroupApiService.retrieveAllCampaignGroups();
    }

    @PostMapping("/uploadCsv/{campaignGroupName}")
    public ResponseEntity<Map<String, String>> handleCsvFileUpload(
            @RequestParam("file") MultipartFile file,
            @PathVariable String campaignGroupName
    ) {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("campaignGroupName", campaignGroupName);

        try (InputStream inStream = file.getInputStream()) {
            csvHandlingService.handleCsvFile(inStream, campaignGroupName);
            result.put("filename", file.getOriginalFilename());
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Problem uploading file", e);
            result.put("msg", "Problem uploading file " + e.getMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
