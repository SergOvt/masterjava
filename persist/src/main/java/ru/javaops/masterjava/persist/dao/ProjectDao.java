package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.Project;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class ProjectDao implements AbstractDao<Project> {
    @SqlUpdate("TRUNCATE projects CASCADE")
    @Override
    public abstract void clean();

    @SqlQuery("SELECT * FROM projects ORDER BY name")
    public abstract List<Project> getAll();

    @SqlUpdate("INSERT INTO projects (name) VALUES (:name) ON CONFLICT DO NOTHING")
    @GetGeneratedKeys
    @Override
    public abstract int insertGeneratedId(@BindBean Project project);

    @SqlUpdate("INSERT INTO projects (id, name) VALUES (:id, :name) ON CONFLICT DO NOTHING")
    @Override
    public abstract void insertWithId(@BindBean Project project);

    @SqlBatch("INSERT INTO projects (name) VALUES (:name) ON CONFLICT DO NOTHING")
    @GetGeneratedKeys
    public abstract void insertBatch(@BindBean List<Project> groups);
}
