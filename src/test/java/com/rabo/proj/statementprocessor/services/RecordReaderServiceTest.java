package com.rabo.proj.statementprocessor.services;


import com.rabo.proj.statementprocessor.util.ReportUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecordReaderServiceTest {

    @Autowired
    private RecordReaderService recordReaderService;

    @Autowired
    private ReportUtil reportUtil;

    @Test
    public void readXMLAndCSV() {
        try {
        reportUtil.setOutputReport("report.csv");
        reportUtil.setUp();
        recordReaderService.setReportUtil(this.reportUtil);
        recordReaderService.setUp("records.xml");
        recordReaderService.readXML();
        recordReaderService.setInputFile("records.csv");
        recordReaderService.readCSV();
        this.reportUtil.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}