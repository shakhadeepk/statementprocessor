package com.rabo.proj.statementprocessor.resources;

import com.rabo.proj.statementprocessor.models.Request;
import com.rabo.proj.statementprocessor.services.RecordReaderService;
import com.rabo.proj.statementprocessor.services.RecordValidationAndReportGeneration;
import com.rabo.proj.statementprocessor.util.ReportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
* Rest Controller exposing RESTFul API to generate report of failed transactions
*
*  The RESTFul API
*  POST : http://<url>:<Port>/statement/reports
*
*  Request:
* {
*   "csvFileLocation":<Location of csv file in server>,
	"xmlFileLocation":<Location of xml file in server>
* }
*
* */

@RestController
@RequestMapping("/statement")
public class StatementController {

    @Autowired
    private RecordReaderService recordReaderService;

    private ReportUtil reportUtil;

    @Autowired
    public void setReportUtil(ReportUtil reportUtil) {
        this.reportUtil = reportUtil;
    }

    @Autowired
    private RecordValidationAndReportGeneration recordValidationAndReportGeneration;

    @GetMapping(value="/isAlive")
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>("Alive", HttpStatus.OK);
    }


    @PostMapping(value="/reports",produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> checkStatements(@RequestBody Request request){

        if(request.getCsvFileLocation()!=null && !recordValidationAndReportGeneration.validateFileName(request.getCsvFileLocation())){
            return new ResponseEntity<>("Failed! Please check the CSV file location",HttpStatus.OK);
        }
        if(request.getXmlFileLocation()!=null && !recordValidationAndReportGeneration.validateFileName(request.getXmlFileLocation())){
            return new ResponseEntity<>("Failed! Please check the XML file location",HttpStatus.OK);
        }

        this.reportUtil.setOutputReport(request.getOutputReportLocation());
        this.reportUtil.setUp();
        recordReaderService.setReportUtil(this.reportUtil);

        if(request.getCsvFileLocation()!=null){
            recordReaderService.setInputFile(request.getCsvFileLocation());
            recordReaderService.readCSV();
        }
        if(request.getXmlFileLocation()!=null){
            recordReaderService.setUp(request.getXmlFileLocation());
            recordReaderService.readXML();
        }
        this.reportUtil.close();
        this.recordValidationAndReportGeneration.clearReferences();
        return new ResponseEntity<>("Success! "+request.getOutputReportLocation()+" is generated",HttpStatus.OK);
    }
}
