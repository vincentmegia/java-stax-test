package com.vincentmegia;

import com.vincentmegia.processors.EmployeeXmlGenerator;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class App {
    public static void main(String args[]) throws FileNotFoundException, XMLStreamException {
        var employees = new HashMap<String, Map<String, String>>();
        for (long index = 0; index <= Long.MAX_VALUE; index++) {
            String uuid = UUID.randomUUID().toString();
            employees.put(UUID.randomUUID().toString(),
                    Map.of("FirstName", "Vince-" + uuid,
                            "LastName", "Megia-" + uuid));
        }
        var employeeXmlGenerator = new EmployeeXmlGenerator();
        employeeXmlGenerator.generate(employees);
    }
}
