package com.rabo.proj.statementprocessor.models;

public class Request {

    private String csvFileLocation;
    private String xmlFileLocation;

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
}
