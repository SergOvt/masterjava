package ru.javaops.masterjava.persist.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javaops.masterjava.persist.ProjectTestData;
import ru.javaops.masterjava.persist.model.Project;

import java.util.List;

import static ru.javaops.masterjava.persist.ProjectTestData.FULL_PROJECTS;
import static ru.javaops.masterjava.persist.ProjectTestData.MJ;
import static ru.javaops.masterjava.persist.ProjectTestData.PROJECTS;

public class ProjectDaoTest extends AbstractDaoTest<ProjectDao> {

    public ProjectDaoTest() {
        super(ProjectDao.class);
    }

    @BeforeClass
    public static void init() throws Exception {
        ProjectTestData.init();
    }

    @Before
    public void setUp() throws Exception {
        ProjectTestData.setUp(false);
    }

    @Test
    public void getAll() {
        List<Project> projects = dao.getAll();
        Assert.assertEquals(PROJECTS, projects);
    }

    @Test
    public void insert() {
        dao.insert(MJ);
        Assert.assertEquals(FULL_PROJECTS, dao.getAll());
    }
}
