package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.City;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class CityDao implements AbstractDao<City> {
    @SqlUpdate("TRUNCATE cities CASCADE")
    @Override
    public abstract void clean();

    @SqlUpdate("INSERT INTO cities (name, short_name) VALUES (:name, :shortName) ON CONFLICT DO NOTHING")
    @GetGeneratedKeys
    @Override
    public abstract int insertGeneratedId(@BindBean City city);

    @SqlUpdate("INSERT INTO cities (id, name, short_name) VALUES (:id, :name, :shortName) ON CONFLICT DO NOTHING")
    @Override
    public abstract void insertWithId(@BindBean City entity);

    @SqlQuery("SELECT * FROM cities ORDER BY name")
    public abstract List<City> getAll();

    @SqlBatch("INSERT INTO cities (name, short_name) VALUES (:name, :shortName) ON CONFLICT DO NOTHING")
    @GetGeneratedKeys
    public abstract void insertBatch(@BindBean List<City> cities);

}
