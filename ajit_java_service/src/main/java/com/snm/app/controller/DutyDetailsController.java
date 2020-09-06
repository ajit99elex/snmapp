package com.snm.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snm.app.ExcelHelper;
import com.snm.app.FireBaseConfig;
import com.snm.app.dao.DataStore;
import com.snm.app.dao.FireBaseDatasource;
import com.snm.app.dto.DutyDetailsDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.*;

@RestController
@RequestMapping("DataService")
public class DutyDetailsController {

    private Logger logger = LoggerFactory.getLogger(DutyDetailsController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FireBaseDatasource fireBaseDatasource;

    @Autowired
    private FireBaseConfig fireBaseConfig;

    @Autowired
    private DataStore dataStore;


    private JSONParser jsonParser = new JSONParser();

    @Autowired
    private ObjectMapper objectMapper;


    @GetMapping("greeting")
    public String greeting() {
        return "Welcome to SNM App";
    }

    @GetMapping("fetchDutyListForPracharak")
    public String fetchDutyListForUser(@RequestParam(required = false) String pracharakName,
                                       @RequestParam(required = false) String sectorNumber,
                                       @RequestParam(required = false) String branchName) throws JsonProcessingException {
        JSONArray finalResultArray = new JSONArray();
        List<DutyDetailsDTO> fetchedDutyList = new ArrayList<>();
        try {
            /*String response = restTemplate.getForObject(fireBaseConfig.getUrl() + "/DutyList.json?print=pretty" +
                    "&orderBy=\"name\"" +
                    "&startAt=\"" + pracharakName + "\"" +
                    "&access_token=" + fireBaseDatasource.getAuthToken(), String.class);

            JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
            Collection<JSONObject> jsonCollection = jsonObject.values();
            jsonCollection.forEach(jsonObj -> {
                System.out.println(jsonObj);
                Collection dutyDetails = jsonObj.values();
                dutyDetails.forEach(dutyDetailsObj -> {
                    System.out.println(dutyDetailsObj);
                    finalResultArray.add(dutyDetailsObj);
                });
            });*/
            if(!StringUtils.isEmpty(pracharakName)){
                fetchedDutyList.addAll(dataStore.pracharakDutyDetailsCache.get(pracharakName));
            }else if(!StringUtils.isEmpty(sectorNumber)){
                fetchedDutyList.addAll(dataStore.sectorWiseDutyDetailsCache.get(sectorNumber));
            }else if(!StringUtils.isEmpty(branchName)){
                fetchedDutyList.addAll(dataStore.branchWiseDutyDetailsCache.get(branchName));
            }
//            finalResultArray.addAll(fetchedDutyList);

        } catch (Exception e) {
            logger.error("Exception while fetching duty details", e);
        }

//        return finalResultArray.toString();
        return objectMapper.writeValueAsString(fetchedDutyList);
    }

    @GetMapping("fetchThoughtOfTheDay")
    public String fetchThoughtOfTheDay(){
        String response = restTemplate.getForObject(fireBaseConfig.getUrl()+"/ThoughtOfTheDay.json", String.class);
        return response;
    }

    @PostMapping("uploadDutyDetailsFile")
    public String uploadDutyDetailsFile(@RequestBody byte[] content){
        DataInputStream stream = new DataInputStream(new ByteArrayInputStream(content));
        try {

            Map<String, List<Map<String, String>>> fileRowDataMap = ExcelHelper.parseExcelStream(stream);
            /*Map<String, Map<String, String>> pracharakNameKey = new HashMap<>();*/
            /*ExcelHelper.populateDataCollection(fileRowDataMap, pracharakNameKey, branchWiseData, sectorWiseData);*/

            HttpEntity<Map<String, List<Map<String, String>>>> requestEntity = new HttpEntity<>(fileRowDataMap);
            /*HttpEntity<Map<String, Map<String, String>>> requestEntityForPatch = new HttpEntity<>(fileRowDataMap);*/
            restTemplate.exchange(fireBaseConfig.getUrl()+"/DutyList.json"+
                    "?access_token=" + fireBaseDatasource.getAuthToken(),
                    HttpMethod.PUT, requestEntity, Object.class);
            /*restTemplate.exchange(fireBaseConfig.getUrl()+"/DutyList.json"+
                            "?access_token=" + fireBaseDatasource.getAuthToken(),
                    HttpMethod.PATCH, requestEntity, Object.class);*/
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while parsing file, please contact technology team.";
        }

        return "Success";


    }


    @GetMapping("pracharakName")
    public String fetchPracharakNames() throws JsonProcessingException {
        return objectMapper.writeValueAsString(dataStore.pracharakDutyDetailsCache.keySet());
    }

    @GetMapping("branchName")
    public String fetchBranchNames() throws JsonProcessingException {
        return objectMapper.writeValueAsString(dataStore.branchWiseDutyDetailsCache.keySet());
    }

    @GetMapping("sectorNumber")
    public String fetchSectorNumbers() throws JsonProcessingException {
        return objectMapper.writeValueAsString(dataStore.sectorWiseDutyDetailsCache.keySet());
    }

    /*@GetMapping("readData")
    public String getAllJsonData(){
        DatabaseReference reference = fireBaseDatasource.getDb().getReference("dishes");
        *//*reference.child("place").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });*//*


        return "Test";
    }*/


}
