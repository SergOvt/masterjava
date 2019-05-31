package ru.javaops.masterjava.persist.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javaops.masterjava.persist.CityTestData;
import ru.javaops.masterjava.persist.model.City;

import java.util.List;

import static ru.javaops.masterjava.persist.CityTestData.CITIES;
import static ru.javaops.masterjava.persist.CityTestData.FULL_CITIES;
import static ru.javaops.masterjava.persist.CityTestData.KIV;

public class CityDaoTest extends AbstractDaoTest<CityDao> {

    protected CityDaoTest(Class<CityDao> cityDaoClass) {
        super(cityDaoClass);
    }

    @BeforeClass
    public static void init() throws Exception {
        CityTestData.init();
    }

    @Before
    public void setUp() throws Exception {
        CityTestData.setUp();
    }

    @Test
    public void getWithLimit() {
        List<City> cities = dao.getAll();
        Assert.assertEquals(CITIES, cities);
    }

    @Test
    public void insert() {
        dao.insert(KIV);
        Assert.assertEquals(FULL_CITIES, dao.getAll());
    }
}
