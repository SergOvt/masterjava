package ru.javaops.masterjava.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.validation.Schema;
import java.io.StringWriter;
import java.io.Writer;

public class JaxbMarshaller {
    private static final ThreadLocal<Marshaller> MARSHALLER = new ThreadLocal<>();

    public JaxbMarshaller(JAXBContext ctx) throws JAXBException {
        MARSHALLER.set(ctx.createMarshaller());
        MARSHALLER.get().setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        MARSHALLER.get().setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        MARSHALLER.get().setProperty(Marshaller.JAXB_FRAGMENT, true);
    }

    public void setProperty(String prop, Object value) throws PropertyException {
        MARSHALLER.get().setProperty(prop, value);
    }

    public void setSchema(Schema schema) {
        MARSHALLER.get().setSchema(schema);
    }

    public String marshal(Object instance) throws JAXBException {
        StringWriter sw = new StringWriter();
        marshal(instance, sw);
        return sw.toString();
    }

    public void marshal(Object instance, Writer writer) throws JAXBException {
        MARSHALLER.get().marshal(instance, writer);
    }

}
