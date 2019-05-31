package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.Group;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class GroupDao implements AbstractDao<Group> {
    @SqlUpdate("TRUNCATE project_groups")
    @Override
    public abstract void clean();

    @SqlQuery("SELECT * FROM project_groups ORDER BY project_id, name")
    public abstract List<Group> getAll();

    @SqlUpdate("INSERT INTO project_groups (name, project_id) VALUES (:name, :projectId) ON CONFLICT DO NOTHING")
    @GetGeneratedKeys
    @Override
    public abstract int insertGeneratedId(@BindBean Group group);

    @SqlUpdate("INSERT INTO project_groups (id, name, project_id) VALUES (:id, :name, :projectId) ON CONFLICT DO NOTHING")
    @Override
    public abstract void insertWithId(@BindBean Group group);

    @SqlBatch("INSERT INTO project_groups (name, project_id) VALUES (:name, :projectId) ON CONFLICT DO NOTHING")
    @GetGeneratedKeys
    public abstract void insertBatch(@BindBean List<Group> groups);

}
