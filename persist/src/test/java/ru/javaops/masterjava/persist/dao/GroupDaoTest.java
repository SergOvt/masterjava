package ru.javaops.masterjava.persist.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javaops.masterjava.persist.GroupTestData;
import ru.javaops.masterjava.persist.ProjectTestData;
import ru.javaops.masterjava.persist.model.Group;

import java.util.List;

import static ru.javaops.masterjava.persist.GroupTestData.FULL_GROUPS;
import static ru.javaops.masterjava.persist.GroupTestData.GROUPS;
import static ru.javaops.masterjava.persist.GroupTestData.MJ1;

public class GroupDaoTest extends AbstractDaoTest<GroupDao> {

    public GroupDaoTest() {
        super(GroupDao.class);
    }

    @BeforeClass
    public static void init() throws Exception {
        ProjectTestData.init();
        GroupTestData.init();
        ProjectTestData.setUp(true);
    }

    @Before
    public void setUp() throws Exception {
        GroupTestData.setUp();
    }

    @Test
    public void getAll() {
        List<Group> groups = dao.getAll();
        Assert.assertEquals(GROUPS, groups);
    }

    @Test
    public void insert() {
        dao.insert(MJ1);
        Assert.assertEquals(FULL_GROUPS, dao.getAll());
    }
}
