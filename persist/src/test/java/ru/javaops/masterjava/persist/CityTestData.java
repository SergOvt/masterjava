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
        MSK = new City("Moscow", "mov");
        SPB = new City("Saint-Petersburg", "spb");
        KIV = new City("Kiev", "kiv");
        CITIES = ImmutableList.of(MSK, SPB);
        FULL_CITIES = ImmutableList.of(KIV, MSK, SPB);
    }

    public static void setUp(boolean isFull) {
        CityDao dao = DBIProvider.getDao(CityDao.class);
        dao.clean();
        DBIProvider.getDBI().useTransaction((conn, status) -> {
               if (isFull) {
                   FULL_CITIES.forEach(dao::insert);
               } else {
                   CITIES.forEach(dao::insert);
               }
        });
    }
}
