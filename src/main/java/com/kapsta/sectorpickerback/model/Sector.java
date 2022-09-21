package com.kapsta.sectorpickerback.model;

import com.kapsta.sectorpickerback.util.StringTool;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "sectors")
public class Sector implements Serializable {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @Getter
    private Long id;

    @Setter
    private String name;
    @OneToOne
    @JoinColumn(name = "parent_id")
    private Sector parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Sector> children;

    @ManyToMany(mappedBy = "userSectors", cascade = CascadeType.DETACH)
    private List<Worker> sectorUsers;

    public String getName() {
        return StringTool.replaceKnownCharactersInString(name);
    }

    public List<Sector> getAllChildrenWithIndents(int childrenLevel) {
        List<Sector> allChildren = new ArrayList<>();
        List<Sector> sortedChildren = children.stream()
                .sorted(Comparator.comparing(Sector::getName))
                .toList();
        sortedChildren.forEach(child -> {
            child.setName(StringTool.addNbspToString(child.getName(), childrenLevel));
            allChildren.add(child);
            allChildren.addAll(child.getAllChildrenWithIndents(childrenLevel + 1));
        });
        return allChildren;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
