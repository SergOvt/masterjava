package ru.javaops.masterjava.persist.model;


import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Project extends BaseEntity {
    @Column("project_group")
    private @NonNull String name;

    public Project(Integer id, String name) {
        this(name);
        this.id = id;
    }
}
