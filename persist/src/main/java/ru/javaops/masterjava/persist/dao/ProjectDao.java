package ru.javaops.masterjava.persist.dao;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import ru.javaops.masterjava.persist.model.Project;

import java.util.List;

public abstract class ProjectDao implements AbstractDao {
    @SqlUpdate("TRUNCATE projects")
    @Override
    public abstract void clean();

    @SqlQuery("SELECT * FROM projects ORDER BY name")
    public abstract List<Project> getAll();

    @SqlUpdate("INSERT INTO projects (name) VALUES (:name) ON CONFLICT DO NOTHING")
    @GetGeneratedKeys
    public abstract int insert(@BindBean Project project);

    @SqlBatch("INSERT INTO projects (name) VALUES (:name) ON CONFLICT DO NOTHING")
    @GetGeneratedKeys
    public abstract void insertBatch(@BindBean List<Project> groups);
}
