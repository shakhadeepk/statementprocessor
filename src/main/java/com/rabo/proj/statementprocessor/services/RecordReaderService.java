package com.rabo.proj.statementprocessor.services;

import com.rabo.proj.statementprocessor.models.Record;
import com.rabo.proj.statementprocessor.util.FileUtilsXML;
import com.rabo.proj.statementprocessor.util.ReportUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordReaderService {

    private final Logger LOG = LoggerFactory.getLogger(RecordReaderService.class);

    private boolean initialized=false;

    @Autowired
    private FileUtilsXML fileUtility;

    private ReportUtil reportUtil;

    @Autowired
    RecordReaderService(ReportUtil reportUtil){
        this.reportUtil=reportUtil;
        this.reportUtil.setUp();
    }

    @PreDestroy
    public void close(){
        this.reportUtil.close();
    }

    private String inputFile;

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    @Autowired
    private RecordValidationAndReportGeneration recordValidationAndReportGeneration;

    private JAXBContext jaxbContext;
    private Unmarshaller unmarshaller;
    private static final String elementClassName="com.rabo.proj.statementprocessor.models.Record";
    private static final String tagName="record";


    public void setUp(String fileSource){
        try {
            jaxbContext=JAXBContext.newInstance(Record.class);
            unmarshaller=jaxbContext.createUnmarshaller();
            fileUtility.setXmlFileResource(fileSource);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public synchronized void readXML(){
        JAXBElement jaxbElement = null;
        Record record=null;
        fileUtility.setClassName(elementClassName);
        fileUtility.setElementName(tagName);
        while (true){
            jaxbElement=fileUtility.readElementFromXML(this.unmarshaller);
            if(jaxbElement==null)break;
            record=(Record) jaxbElement.getValue();
            LOG.info("XML Record to be validated "+record);
            if(!recordValidationAndReportGeneration.validateRecord(record)){
                this.reportUtil.writeToFile(record);
            }
        }
    }
    public synchronized void readCSV(){
        Record record=null;
        List<Record> records = new ArrayList<>();
        try {
            Reader in=new FileReader(inputFile);
            Iterable<CSVRecord> csvRecords= CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            for (CSVRecord csvRecord:csvRecords){
                record=new Record();
                record.setReference(csvRecord.get(0));
                record.setAccountNumber(csvRecord.get(1));
                record.setDescription(csvRecord.get(2));
                record.setStartBalance(new BigDecimal(csvRecord.get(3)));
                record.setMutation(new BigDecimal(csvRecord.get(4)));
                record.setEndBalance(new BigDecimal(csvRecord.get(5)));
                LOG.info("CSV Record to be validated "+record);
                if(!recordValidationAndReportGeneration.validateRecord(record)){
                    this.reportUtil.writeToFile(record);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
