package com.rabo.proj.statementprocessor.services;

import com.rabo.proj.statementprocessor.models.Record;
import com.rabo.proj.statementprocessor.util.FileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Service
public class XmlReaderServices {

    private boolean initialized=false;

    @Autowired
    private FileUtility fileUtility;

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

    public void read(){
        JAXBElement jaxbElement = null;
        Record record=null;
        fileUtility.setClassName(elementClassName);
        fileUtility.setElementName(tagName);
        while (true){
            jaxbElement=fileUtility.readElementFromXML(this.unmarshaller);
            if(jaxbElement==null)break;
            record=(Record) jaxbElement.getValue();
            System.out.println(record);
        }
    }
}
