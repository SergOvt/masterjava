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
public class City extends BaseEntity {
    private @NonNull String name;
    @Column("short_name")
    private @NonNull String shortName;

    public City(Integer id, String name, String shortName) {
        this(name, shortName);
        this.id = id;
    }
}
