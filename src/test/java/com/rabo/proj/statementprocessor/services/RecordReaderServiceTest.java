package com.rabo.proj.statementprocessor.services;


import com.rabo.proj.statementprocessor.util.ReportUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecordReaderServiceTest {

    @Autowired
    private RecordReaderService recordReaderService;

    @Autowired
    private ReportUtil reportUtil;

    @Test
    public void readXMLAndCSV() {
        reportUtil.setOutputReport("/home/hans/shankha/interview-project/assignment-shankadeep/report.csv");
        reportUtil.setUp();
        recordReaderService.setReportUtil(this.reportUtil);
        recordReaderService.setUp("/home/hans/shankha/interview-project/assignment-shankadeep/records.xml");
        recordReaderService.readXML();
        recordReaderService.setInputFile("/home/hans/shankha/interview-project/assignment-shankadeep/records.csv");
        recordReaderService.readCSV();
        this.reportUtil.close();
    }
}