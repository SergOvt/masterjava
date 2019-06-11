package ru.javaops.masterjava.service.mail.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.service.mail.model.Report;

import java.util.List;


@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class ReportDao {

    @SqlUpdate("TRUNCATE report CASCADE ")
    public abstract void clean();

    @SqlQuery("SELECT * FROM report")
    public abstract List<Report> getAll();

    @SqlUpdate("INSERT INTO report (email, success, couse) VALUES (:email, :success, :couse)")
    @GetGeneratedKeys
    public abstract void insert(@BindBean Report report);
}
