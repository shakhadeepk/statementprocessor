package com.rabo.proj.statementprocessor.models;

/*
* Request is the request JSON sent by the consumer
*
* */
public class Request {

    private String csvFileLocation;
    private String xmlFileLocation;
    private String outputReportLocation;

    public String getCsvFileLocation() {
        return csvFileLocation;
    }

    public void setCsvFileLocation(String csvFileLocation) {
        this.csvFileLocation = csvFileLocation;
    }

    public String getXmlFileLocation() {
        return xmlFileLocation;
    }

    public void setXmlFileLocation(String xmlFileLocation) {
        this.xmlFileLocation = xmlFileLocation;
    }

    public String getOutputReportLocation() {
        return outputReportLocation;
    }

    public void setOutputReportLocation(String outputReportLocation) {
        this.outputReportLocation = outputReportLocation;
    }
}
