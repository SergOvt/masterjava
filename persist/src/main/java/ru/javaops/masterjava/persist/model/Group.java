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
public class Group extends BaseEntity {
    private @NonNull String name;
    @Column("project_id")
    private @NonNull Integer projectId;

    public Group(Integer id, String name, Integer projectId) {
        this(name, projectId);
        this.id = id;
    }
}
