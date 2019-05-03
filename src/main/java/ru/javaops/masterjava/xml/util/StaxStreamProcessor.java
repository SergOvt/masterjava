package ru.javaops.masterjava.xml.util;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

public class StaxStreamProcessor implements AutoCloseable {
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLStreamReader reader;

    public StaxStreamProcessor(InputStream is) throws XMLStreamException {
        reader = FACTORY.createXMLStreamReader(is);
    }

    public XMLStreamReader getReader() {
        return reader;
    }

    public boolean doUntil(int stopEvent, String value, String... limit) throws XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();
            if (limit.length != 0 && event == XMLEvent.END_ELEMENT && getValue(event).equals(limit[0])) {
                return false;
            }
            if (event == stopEvent) {
                if (value.equals(getValue(event))) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getValue(int event) throws XMLStreamException {
        return (event == XMLEvent.CHARACTERS) ? reader.getText() : reader.getLocalName();
    }

    public String getElementValue(String element, String... limit) throws XMLStreamException {
        return doUntil(XMLEvent.START_ELEMENT, element, limit) ? reader.getEventType() == XMLEvent.CHARACTERS ?
                reader.getElementText() : reader.getLocalName() : null;
    }

    public String getAttributeValue(String attributeName) throws XMLStreamException {
        return reader.getAttributeValue("", attributeName);
    }

    public String getText() throws XMLStreamException {
        return reader.getElementText();
    }

    @Override
    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) {
                // empty
            }
        }
    }
}
