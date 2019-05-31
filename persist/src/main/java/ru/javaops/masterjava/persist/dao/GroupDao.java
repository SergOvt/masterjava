package ru.javaops.masterjava.persist.dao;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import ru.javaops.masterjava.persist.model.Group;

import java.util.List;

public abstract class GroupDao implements AbstractDao {
    @SqlUpdate("TRUNCATE project_groups")
    @Override
    public abstract void clean();

    @SqlQuery("SELECT * FROM project_groups ORDER BY project_id, name")
    public abstract List<Group> getAll();

    @SqlUpdate("INSERT INTO project_groups (name, project_id) VALUES (:name, :projectId) ON CONFLICT DO NOTHING")
    @GetGeneratedKeys
    public abstract int insert(@BindBean Group group);

    @SqlBatch("INSERT INTO project_groups (name, project_id) VALUES (:name, :projectId) ON CONFLICT DO NOTHING")
    @GetGeneratedKeys
    public abstract void insertBatch(@BindBean List<Group> groups);
}
