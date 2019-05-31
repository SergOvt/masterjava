package ru.javaops.masterjava.persist;

import com.google.common.collect.ImmutableList;
import ru.javaops.masterjava.persist.dao.CityDao;
import ru.javaops.masterjava.persist.model.City;

import java.util.List;

public class CityTestData {
    public static City MSK;
    public static City SPB;
    public static City KIV;

    public static List<City> CITIES;
    public static List<City> FULL_CITIES;

    public static void init() {
        MSK = new City(1, "Moscow");
        SPB = new City(2, "Saint-Petersburg");
        KIV = new City(3, "Kiev");
        CITIES = ImmutableList.of(MSK, SPB);
        FULL_CITIES = ImmutableList.of(MSK, SPB, KIV);
    }

    public static void setUp() {
        CityDao dao = DBIProvider.getDao(CityDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
               CITIES.forEach(dao::insert);
        });
    }
}
