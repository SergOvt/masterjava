package ru.javaops.masterjava;

import com.google.common.io.Resources;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;
import ru.javaops.masterjava.xml.schema.Project;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;
import ru.javaops.masterjava.xml.util.XsltProcessor;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class Main {
    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    public static void main(String[] args) throws Exception {
        getProjectUsersJAXB("masterjava").forEach(u -> System.out.println(u.getFullName() + " " + u.getEmail()));
        System.out.println();
        getProjectUsersStax("masterjava").forEach(System.out::println);
        System.out.println();
        System.out.println(transformXmlToHtml());
    }

    public static Set<User> getProjectUsersJAXB(final String projectName) throws IOException, JAXBException {
        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource("payload.xml").openStream());
        return payload.getProjects().getProject()
                .stream()
                .filter(p -> projectName.equals(p.getName()))
                .findAny()
                .orElse(new Project())
                .getGroup()
                .parallelStream()
                .flatMap(g -> g.getUser().stream())
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User::getFullName).thenComparing(User::getEmail))));
    }

    public static Set<String> getProjectUsersStax(final String projectName) throws IOException, XMLStreamException {
        Set<String> users = new TreeSet<>();
        try (StaxStreamProcessor processor =
                     new StaxStreamProcessor(Resources.getResource("payload.xml").openStream())) {
            while (processor.getElementValue("Project") != null) {
                if (projectName.equals(processor.getAttributeValue("name"))) {
                    while (processor.getElementValue("User", "Project") != null) {
                        users.add(processor.getAttributeValue("fullName") + " " + processor.getAttributeValue("email"));
                    }
                    break;
                }
            }
        }
        return users;
    }

    public static String transformXmlToHtml() throws IOException, TransformerException {
        try (InputStream xslInputStream = Resources.getResource("groups.xsl").openStream();
             InputStream xmlInputStream = Resources.getResource("payload.xml").openStream()) {

            XsltProcessor processor = new XsltProcessor(xslInputStream);
            processor.setTransformerParameter("projectName", "masterjava");
            return processor.transform(xmlInputStream);
        }
    }
}
