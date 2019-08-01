package ru.javaops.masterjava.upload;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.GroupDao;
import ru.javaops.masterjava.persist.dao.ProjectDao;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.Project;
import ru.javaops.masterjava.persist.model.type.GroupType;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.stream.XMLStreamException;
import java.util.Map;

@Slf4j
public class ProjectGroupProcessor {
    private final ProjectDao projectDao = DBIProvider.getDao(ProjectDao.class);
    private final GroupDao groupDao = DBIProvider.getDao(GroupDao.class);

    public Map<String, Group> process(StaxStreamProcessor processor) throws XMLStreamException {
        Map<String, Project> projectMap = projectDao.getAsMap();
        Map<String, Group> groupMap = groupDao.getAsMap();

        while (processor.startElement("Project", "Projects")) {
            val projectName = processor.getAttribute("name");
            if (!projectMap.containsKey(projectName)) {
                log.info("Insert project " + projectName);
                projectDao.insert(new Project(projectName, processor.getElementValue("description")));
                projectMap = projectDao.getAsMap();
            }
            while (processor.startElement("Group", "Project")) {
                val groupName = processor.getAttribute("name");
                if (!groupMap.containsKey(groupName)) {
                    log.info("Insert group " + groupName + " for project " + projectName);
                    groupDao.insert(
                            new Group(groupName,
                            GroupType.valueOf(processor.getAttribute("type")),
                            projectMap.get(projectName).getId())
                    );
                }
            }
        }
        return groupDao.getAsMap();
    }
}
