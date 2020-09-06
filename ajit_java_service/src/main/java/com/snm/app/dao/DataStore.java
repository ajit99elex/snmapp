package com.snm.app.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snm.app.FireBaseConfig;
import com.snm.app.dto.DutyDetailsDTO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DataStore {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FireBaseDatasource fireBaseDatasource;

    @Autowired
    private FireBaseConfig fireBaseConfig;

    Logger logger = LoggerFactory.getLogger(DataStore.class);

    public static final Map<String, List<DutyDetailsDTO>> pracharakDutyDetailsCache = new ConcurrentHashMap<>();
    public static final Map<String, List<DutyDetailsDTO>> branchWiseDutyDetailsCache = new ConcurrentHashMap<>();
    public static final Map<String, List<DutyDetailsDTO>> sectorWiseDutyDetailsCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void populateCache() throws ParseException, JsonProcessingException {
        String response = restTemplate.getForObject(fireBaseConfig.getUrl() + "DutyList.json?" +
                "access_token=" + fireBaseDatasource.getAuthToken(), String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
        Collection jsonCollection = jsonObject.values();
        jsonCollection.forEach(jsonObj -> {
            try {
                List<DutyDetailsDTO> dutyDetailsDTOS = objectMapper.readValue(jsonObj.toString(), new TypeReference<List<DutyDetailsDTO>>() {});

                for (DutyDetailsDTO dutyDetailsDTO : dutyDetailsDTOS) {
                    String pracharakName = dutyDetailsDTO.getPracharakname().replaceAll("[\\$\\#\\[\\]\\/.]", "").toUpperCase();
                    pracharakDutyDetailsCache.computeIfAbsent(pracharakName, e -> new ArrayList<>());
                    pracharakDutyDetailsCache.get(pracharakName).add(dutyDetailsDTO);

                    String branchName = dutyDetailsDTO.getBranchname().toUpperCase();
                    branchWiseDutyDetailsCache.computeIfAbsent(branchName, e -> new ArrayList<>());
                    branchWiseDutyDetailsCache.get(branchName).add(dutyDetailsDTO);

                    String sectorid = dutyDetailsDTO.getSectorid().toUpperCase();
                    sectorWiseDutyDetailsCache.computeIfAbsent(sectorid, e -> new ArrayList<>());
                    sectorWiseDutyDetailsCache.get(sectorid).add(dutyDetailsDTO);
                }
            } catch (Exception e) {
                logger.info("Exception occurred while populating cache", e);
            }
        });
        logger.info("Completed population of Cache data");
    }


}
