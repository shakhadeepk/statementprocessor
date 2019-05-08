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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/*
* Reads records from CSV and XML file. Each record is read, validated and corresponding report is created
*
*
* */
@Service
public class RecordReaderService {

    private final Logger LOG = LoggerFactory.getLogger(RecordReaderService.class);

    private boolean initialized=false;

    @Autowired
    private FileUtilsXML fileUtility;

    private ReportUtil reportUtil;

    public void setReportUtil(ReportUtil reportUtil) {
        this.reportUtil = reportUtil;
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


    public void setUp(String fileSource) throws JAXBException {
        jaxbContext=JAXBContext.newInstance(Record.class);
        unmarshaller=jaxbContext.createUnmarshaller();
        fileUtility.setXmlFileResource(fileSource);
    }

    public void readXML() throws ClassNotFoundException, FileNotFoundException, JAXBException, XMLStreamException {
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
        fileUtility.setInitialized(false);
    }
    public void readCSV() throws IOException {
        Record record=null;
        List<Record> records = new ArrayList<>();
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

    }
}
