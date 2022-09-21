package com.kapsta.sectorpickerback.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "workers")
public class Worker implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "ID", updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Getter
    private UUID id;
    @ManyToMany
    @Setter
    @Getter
    @JoinTable(
            name = "sector_worker",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "sector_id"))
    private List<Sector> userSectors;
    @Getter
    @Setter
    @Column(nullable = false)
    private String name = null;

    @Column(nullable = false)
    @Getter
    @Setter
    private boolean tac = false;

    public Worker() {
        // Empty constructor
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name;
    }
}
