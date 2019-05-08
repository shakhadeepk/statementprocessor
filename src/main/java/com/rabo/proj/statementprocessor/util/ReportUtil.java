package com.rabo.proj.statementprocessor.util;

import com.rabo.proj.statementprocessor.models.Record;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class ReportUtil {

    @Value("${output.report}")
    private String outputReport;

    private static String[] header={"Reference","Description"};
    private FileWriter fileWriter;
    private CSVPrinter csvPrinter;
    private CSVFormat csvFormat;

    public void setUp(){
        try {
            fileWriter=new FileWriter(outputReport);
            csvFormat= CSVFormat.DEFAULT;
            csvPrinter=new CSVPrinter(fileWriter,csvFormat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(Record record){

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
