package ru.javaops.masterjava.upload;

import ru.javaops.masterjava.persist.model.User;
import ru.javaops.masterjava.persist.model.UserFlag;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.JaxbUnmarshaller;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UserProcessor {
    private static final JaxbParser jaxbParser = new JaxbParser(ObjectFactory.class);
    private final StaxStreamProcessor processor;

    public UserProcessor(InputStream is) throws XMLStreamException {
        processor = new StaxStreamProcessor(is);
    }

    public List<User> process() throws XMLStreamException, JAXBException {
        return process(-1);
    }

    public List<User> process(int chankSize) throws XMLStreamException, JAXBException {
        JaxbUnmarshaller unmarshaller = jaxbParser.createUnmarshaller();
        List<User> users = new ArrayList<>();
        while (users.size() != chankSize && processor.doUntil(XMLEvent.START_ELEMENT, "User")) {
            ru.javaops.masterjava.xml.schema.User xmlUser = unmarshaller.unmarshal(processor.getReader(), ru.javaops.masterjava.xml.schema.User.class);
            final User user = new User(xmlUser.getValue(), xmlUser.getEmail(), UserFlag.valueOf(xmlUser.getFlag().value()));
            users.add(user);
        }
        return users;
    }
}
