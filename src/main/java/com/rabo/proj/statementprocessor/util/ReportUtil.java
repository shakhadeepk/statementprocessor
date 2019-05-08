package com.rabo.proj.statementprocessor.util;

import com.rabo.proj.statementprocessor.models.Record;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
/*
* Utility created to generate csv report of the transaction files
*
*
*
* */
@Component
public class ReportUtil {

    private final Logger LOG = LoggerFactory.getLogger(ReportUtil.class);

    private String outputReport;

    public void setOutputReport(String outputReport) {
        this.outputReport = outputReport;
    }

    private FileWriter fileWriter;
    private CSVPrinter csvPrinter;
    private CSVFormat csvFormat;

    public void setUp(){
        try {
            fileWriter=new FileWriter(outputReport,false);
            csvFormat= CSVFormat.DEFAULT;
            csvPrinter=new CSVPrinter(fileWriter,csvFormat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(Record record){
        LOG.debug("Record to be written in Report"+record);
        try {
            csvPrinter.printRecord(record.getReference(),record.getDescription());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            fileWriter.close();
            csvPrinter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
