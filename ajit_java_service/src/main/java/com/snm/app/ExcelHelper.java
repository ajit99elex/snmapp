package com.snm.app;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.StringUtil;
import org.springframework.util.StringUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelHelper {
    public static Map<String, List<Map<String, String>>> parseExcelStream(DataInputStream content) throws Exception {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(content);
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            int numberOfHeaders = headerRow.getPhysicalNumberOfCells();
            String[] headers = new String[numberOfHeaders];
            List<Map<String, String>> fileRowDataMap = new ArrayList<>();
//            Map<String, String> satsangNametoBranchSectorMap = getBranchSectorMapping();
            Map<String, List<Map<String, String>>> dataMap = new HashMap<>();

//            int indexOfSatsangNameColumn = 0;
            int indexOfPracharakNameColumn = 0;
            int indexOfDutyDateColumn = 0;
            int indexOfDateColumn = 0;

            for (int i = 0; i < numberOfHeaders; ++i) {
                Cell cell = headerRow.getCell(i);
                if (cell.getCellTypeEnum() != CellType.STRING) {
                    throw new Exception("Incorrect values found in header line");
                }
                if(!StringUtils.isEmpty(cell.getStringCellValue().trim()) && cell.getStringCellValue().trim().equals("Dutydate")){
                    indexOfDutyDateColumn = i;
                }
                if(!StringUtils.isEmpty(cell.getStringCellValue().trim()) && cell.getStringCellValue().trim().equals("DutyDate")){
                    indexOfDateColumn = i;
                }
                String headerValue = cell.getStringCellValue().trim().toLowerCase();
                /*if(!StringUtils.isEmpty(headerValue) && headerValue.equalsIgnoreCase("satsangname")){
                    indexOfSatsangNameColumn = i;
                }*/
                if(!StringUtils.isEmpty(headerValue) && headerValue.equalsIgnoreCase("pracharakname")){
                    indexOfPracharakNameColumn = i;
                }
                headers[i] = headerValue;
            }

            if(indexOfPracharakNameColumn == 0){
                return null;
            }

            for (int rowIndex = 1; rowIndex < sheet.getLastRowNum(); ++rowIndex) {
                Row dataRow = sheet.getRow(rowIndex);
                String pracharakName = dataRow.getCell(indexOfPracharakNameColumn).getStringCellValue().replaceAll("[\\$\\#\\[\\]\\/.]", "");
                dataMap.computeIfAbsent(pracharakName, k -> new ArrayList<Map<String, String>>());

                Map<String, String> rowData = new HashMap<>();
                for (int i = 0; i < numberOfHeaders; ++i) {
                    Cell currentCell = dataRow.getCell(i);
                    if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        currentCell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                    if(i == indexOfDutyDateColumn){
                        String cellValue = currentCell.getStringCellValue();
                        rowData.put(headers[i], cellValue.substring(cellValue.indexOf("-")+1));
                    }else if(i != indexOfDateColumn){
                        String cellValue = currentCell.getStringCellValue();
                        rowData.put(headers[i], cellValue);
                    }
                    /*if(i == indexOfSatsangNameColumn){
                        String branchSectorInfo = satsangNametoBranchSectorMap.get(cellValue);
                        if(!StringUtils.isEmpty(branchSectorInfo)){
                            rowData.put("branchname", branchSectorInfo.split("\\|")[0]);
                            rowData.put("sectornumber", branchSectorInfo.split("\\|")[1]);
                        }
                    }*/
                }

                dataMap.get(pracharakName).add(rowData);
            }

            return dataMap;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Map<String, String>> getPracharakNameKeyMapping(Map<String, List<Map<String, String>>> fileRowDataMap) {
        Map<String, Map<String, String>> finalData = new HashMap<>();
        for (Map.Entry<String, List<Map<String, String>>> entryObj : fileRowDataMap.entrySet()) {
            if(finalData.containsKey(entryObj.getKey())){
                continue;
            }
            Map<String, String> data = new HashMap<>();
            data.put("pracharakname", entryObj.getKey());
            finalData.put(entryObj.getKey(), data);
        }
        return finalData;
    }
}
