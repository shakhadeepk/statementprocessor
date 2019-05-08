package com.rabo.proj.statementprocessor.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecordReaderServiceTest {

    @Autowired
    private RecordReaderService recordReaderService;

    @Test
    void readXML() {
        recordReaderService.setUp("/home/hans/shankha/interview-project/assignment-shankadeep/records.xml");
        recordReaderService.readXML();
        recordReaderService.setInputFile("/home/hans/shankha/interview-project/assignment-shankadeep/records.csv");
        recordReaderService.readCSV();
    }

    @Test
    void readCSV() {
        recordReaderService.setInputFile("/home/hans/shankha/interview-project/assignment-shankadeep/records.csv");
        recordReaderService.readCSV();
    }
}