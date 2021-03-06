package com.rabo.proj.statementprocessor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
/*
*
* Utility file to read XML files each record at a time. XMLStreamReader is used for efficiency
*
* */
@Component
public class FileUtilsXML {

    private final Logger LOG = LoggerFactory.getLogger(FileUtilsXML.class);

    private XMLStreamReader xmlStreamReader;
    private String xmlFileResource;
    private boolean initialized=false;
    private String elementName;
    private String className;

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getXmlFileResource() {
        return xmlFileResource;
    }

    public void setXmlFileResource(String xmlFileResource) {
        this.xmlFileResource = xmlFileResource;
    }

    public XMLStreamReader getXmlStreamReader() {
        return xmlStreamReader;
    }

    public void setXmlStreamReader(XMLStreamReader xmlStreamReader) {
        this.xmlStreamReader = xmlStreamReader;
    }

    public void initialize() throws FileNotFoundException, XMLStreamException {
        XMLInputFactory xmlInputFactory=XMLInputFactory.newInstance();
        xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        this.xmlStreamReader =xmlInputFactory.createXMLStreamReader(new FileInputStream(this.xmlFileResource));
        initialized=true;
    }

    public <Record> JAXBElement<Record> readElementFromXML(Unmarshaller unmarshaller) throws FileNotFoundException, XMLStreamException, ClassNotFoundException, JAXBException {
        JAXBElement jaxbElement=null;

        if(!initialized){
            initialize();
        }
        while(this.xmlStreamReader.hasNext()){
                if(xmlStreamReader.isStartElement() && elementName.equalsIgnoreCase(xmlStreamReader.getLocalName())){
                    break;
                }
                this.xmlStreamReader.next();
            }
            if(this.xmlStreamReader !=null
                    &&!(this.xmlStreamReader.getEventType() == XMLStreamReader.END_DOCUMENT)
                    && !(this.xmlStreamReader.getEventType() == XMLStreamReader.END_ELEMENT)){
                jaxbElement=unmarshaller.unmarshal(this.xmlStreamReader,Class.forName(this.className));
            }
        return jaxbElement;

    }
}
