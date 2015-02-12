/*
 * Generated at : 09-May-2006 20:45:32
 *
 * Copyright (c) 2005 - 2006, Edwin Dankert 
 * All rights reserved. 
 */

package Main;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ValidateExternalSchema {
    
    public class SimpleErrorHandler implements ErrorHandler {
        public void warning(SAXParseException e) throws SAXException {
            System.out.println("   Warning: "+e.getMessage());
        }

        public void error(SAXParseException e) throws SAXException {
            System.out.println("   Error: "+e.getMessage());
        }

        public void fatalError(SAXParseException e) throws SAXException {
            System.out.println("   Fatal Error: "+e.getMessage());
        }
    }
    public ValidateExternalSchema(FileInputStream aValidar) {
        try {
            System.out.println("Validacion de Mondrian Schema:");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setValidating(false);
            factory.setNamespaceAware(true);

            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

            factory.setSchema(schemaFactory.newSchema(new Source[] {new StreamSource("./mondrian.xsd")}));

            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new SimpleErrorHandler());
            Document doc = builder.parse(new InputSource(aValidar));
            System.out.println("Validacion Finalizada.");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
