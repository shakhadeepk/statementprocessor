package com.rabo.proj.statementprocessor.resources;

import com.rabo.proj.statementprocessor.models.Request;
import com.rabo.proj.statementprocessor.services.RecordReaderService;
import com.rabo.proj.statementprocessor.services.RecordValidationAndReportGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statement")
public class StatementController {

    @Autowired
    private RecordReaderService recordReaderService;

    @Autowired
    private RecordValidationAndReportGeneration recordValidationAndReportGeneration;

    @GetMapping(value="/isAlive")
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>("Alive", HttpStatus.OK);
    }


    @PostMapping(value="/reports",produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> checkStatements(@RequestBody Request request){

        if(recordValidationAndReportGeneration.validateFileName(request.getCsvFileLocation())){
            return new ResponseEntity<>("Failed",HttpStatus.OK);
        }
        if(recordValidationAndReportGeneration.validateFileName(request.getXmlFileLocation())){
            return new ResponseEntity<>("Failed",HttpStatus.OK);
        }

        if(request.getCsvFileLocation()!=null){
            recordReaderService.setInputFile(request.getCsvFileLocation());
            recordReaderService.readCSV();
        }
        if(request.getXmlFileLocation()!=null){
            recordReaderService.setUp(request.getXmlFileLocation());
            recordReaderService.readXML();
        }
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }
}
