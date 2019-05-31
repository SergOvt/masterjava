package ru.javaops.masterjava.persist;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.dao.UserDao;
import ru.javaops.masterjava.persist.model.User;
import ru.javaops.masterjava.persist.model.UserFlag;

import java.util.List;

public class UserTestData {
    public static User ADMIN;
    public static User DELETED;
    public static User FULL_NAME;
    public static User USER1;
    public static User USER2;
    public static User USER3;
    public static List<User> FIST5_USERS;

    public static void init() {
        ADMIN = new User("Admin", "admin@javaops.ru", UserFlag.superuser, "spb");
        DELETED = new User("Deleted", "deleted@yandex.ru", UserFlag.deleted, "spb");
        FULL_NAME = new User("Full Name", "gmail@gmail.com", UserFlag.active, "mov");
        USER1 = new User("User1", "user1@gmail.com", UserFlag.active, "kiv");
        USER2 = new User("User2", "user2@yandex.ru", UserFlag.active, "mov");
        USER3 = new User("User3", "user3@yandex.ru", UserFlag.active, "spb");
        FIST5_USERS = ImmutableList.of(ADMIN, DELETED, FULL_NAME, USER1, USER2);
    }

    public static void setUp() {
        UserDao dao = DBIProvider.getDao(UserDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
            FIST5_USERS.forEach(dao::insert);
            dao.insert(USER3);
        });
    }
}
