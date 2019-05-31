package ru.javaops.masterjava.persist.dao;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import ru.javaops.masterjava.persist.model.City;

import java.util.List;

public abstract class CityDao implements AbstractDao {
    @SqlUpdate("TRUNCATE cities")
    @Override
    public abstract void clean();

    @SqlQuery("SELECT * FROM cities ORDER BY name")
    public abstract List<City> getAll();

    @SqlUpdate("INSERT INTO cities (name) VALUES (:name) ON CONFLICT DO NOTHING")
    @GetGeneratedKeys
    public abstract int insert(@BindBean City city);

    @SqlBatch("INSERT INTO cities (name) VALUES (:name) ON CONFLICT DO NOTHING")
    @GetGeneratedKeys
    public abstract void insertBatch(@BindBean List<City> cities);
}
