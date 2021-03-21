package com.vincentmegia.processors;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Fix issue with constructor, XMLOutputFactory.newInstance() is not exactly loosely couple and IOC friendly
 */
public class EmployeeXmlGenerator {
    private XMLEventWriter xmlEventWriter;
    private final String newLine = "\n";
    private final String tab = "\t";

    public EmployeeXmlGenerator() throws FileNotFoundException, XMLStreamException {
        var filename = "/Users/tylia/repository/java-doodles/java-stax-test/employee.xml"; //make relative path
        this.xmlEventWriter  = XMLOutputFactory
                .newInstance()
                .createXMLEventWriter(new FileOutputStream(filename), "UTF-8");
    }

    /**
     *
     * @param employees
     */
    public void generate(Map<String, Map<String, String>> employees) {
        try {
            var xmlEventFactory = XMLEventFactory.newInstance();
            xmlEventWriter.add(xmlEventFactory.createStartDocument());
            createNewLine(xmlEventFactory);
            createStartElement(xmlEventFactory,"Employees");
            addEmployees(employees);
            createNewLine(xmlEventFactory);
            createEndElement(xmlEventFactory,"Employees");
            xmlEventWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param employees
     * @throws XMLStreamException
     */
    private void addEmployees(Map<String, Map<String, String>> employees) throws XMLStreamException {
        employees.forEach((key, value) -> {
            try {
                var xmlEventFactory = XMLEventFactory.newInstance();
                createNewLine(xmlEventFactory);
                createTab(xmlEventFactory);
                createStartElement(xmlEventFactory,"Employee");
                value.forEach((innerKey, innerValue) -> {
                    try {
                        createNewLine(xmlEventFactory);
                        createTab(xmlEventFactory);
                        createTab(xmlEventFactory);
                        addNode(xmlEventFactory, innerKey, innerValue);
                    } catch (XMLStreamException e) {
                        e.printStackTrace();
                    }
                });
                createNewLine(xmlEventFactory);
                createTab(xmlEventFactory);
                createEndElement(xmlEventFactory,"Employee");
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     *
     * @param xmlEventFactory
     * @param name
     * @param value
     * @throws XMLStreamException
     */
    private void addNode(XMLEventFactory xmlEventFactory, String name, String value) throws XMLStreamException {
        createStartElement(xmlEventFactory, name);
        xmlEventWriter.add(xmlEventFactory.createCharacters(value));
        createEndElement(xmlEventFactory, name);
    }

    /**
     *
     * @param xmlEventFactory
     * @param name
     * @throws XMLStreamException
     */
    private void createStartElement(XMLEventFactory xmlEventFactory, String name) throws XMLStreamException {
        xmlEventWriter.add(xmlEventFactory.createStartElement("", "", name));
    }

    /**
     *
     * @param xmlEventFactory
     * @param name
     * @throws XMLStreamException
     */
    private void createEndElement(XMLEventFactory xmlEventFactory, String name) throws XMLStreamException {
        xmlEventWriter.add(xmlEventFactory.createEndElement("", "", name));
    }

    /**
     *
     * @param xmlEventFactory
     * @throws XMLStreamException
     */
    private void createNewLine(XMLEventFactory xmlEventFactory) throws XMLStreamException {
        xmlEventWriter.add(xmlEventFactory.createDTD("\n"));
    }

    /**
     *
     * @param xmlEventFactory
     * @throws XMLStreamException
     */
    private void createTab(XMLEventFactory xmlEventFactory) throws XMLStreamException {
        xmlEventWriter.add(xmlEventFactory.createDTD("\t"));
    }
}