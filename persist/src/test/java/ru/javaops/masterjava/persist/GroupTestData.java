package ru.javaops.masterjava.persist;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.dao.GroupDao;
import ru.javaops.masterjava.persist.model.Group;

import java.util.List;

public class GroupTestData {
    public static Group TJ6;
    public static Group TJ7;
    public static Group TJ8;
    public static Group MJ1;

    public static List<Group> GROUPS;
    public static List<Group> FULL_GROUPS;

    public static void init() {
        TJ6 = new Group("TopJava 6", 2);
        TJ7 = new Group("TopJava 7", 2);
        TJ8 = new Group("TopJava 8", 2);
        MJ1 = new Group("MasterJava 1", 3);
        GROUPS = ImmutableList.of(TJ6, TJ7, TJ8);
        FULL_GROUPS = ImmutableList.of(TJ6, TJ7, TJ8, MJ1);
    }

    public static void setUp() {
        GroupDao dao = DBIProvider.getDao(GroupDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
               GROUPS.forEach(dao::insert);
        });
    }
}
