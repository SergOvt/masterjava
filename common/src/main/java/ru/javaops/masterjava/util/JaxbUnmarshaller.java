package ru.javaops.masterjava.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

public class JaxbUnmarshaller {
    private static final ThreadLocal<Unmarshaller> UNMARSHALLER = new ThreadLocal<>();

    public JaxbUnmarshaller(JAXBContext ctx) throws JAXBException {
        UNMARSHALLER.set(ctx.createUnmarshaller());
    }

    public void setSchema(Schema schema) {
        UNMARSHALLER.get().setSchema(schema);
    }

    public Object unmarshal(InputStream is) throws JAXBException {
        return UNMARSHALLER.get().unmarshal(is);
    }

    public Object unmarshal(Reader reader) throws JAXBException {
        return UNMARSHALLER.get().unmarshal(reader);
    }

    public Object unmarshal(String str) throws JAXBException {
        return unmarshal(new StringReader(str));
    }

    public <T> T unmarshal(XMLStreamReader reader, Class<T> elementClass) throws JAXBException {
        return UNMARSHALLER.get().unmarshal(reader, elementClass).getValue();
    }
}