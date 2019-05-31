package ru.javaops.masterjava.persist;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.dao.ProjectDao;
import ru.javaops.masterjava.persist.model.Project;

import java.util.List;

public class ProjectTestData {
    public static Project BJ;
    public static Project TJ;
    public static Project MJ;

    public static List<Project> PROJECTS;
    public static List<Project> FULL_PROJECTS;

    public static void init() {
        BJ = new Project(1, "Base Java");
        TJ = new Project(2, "Top Java");
        MJ = new Project(2, "Master Java");
        PROJECTS = ImmutableList.of(BJ, TJ);
        FULL_PROJECTS = ImmutableList.of(BJ, TJ, MJ);
    }

    public static void setUp() {
        ProjectDao dao = DBIProvider.getDao(ProjectDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
               PROJECTS.forEach(dao::insert);
        });
    }
}
