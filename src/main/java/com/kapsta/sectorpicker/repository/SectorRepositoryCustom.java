package com.kapsta.sectorpicker.repository;


import com.kapsta.sectorpicker.model.Sector;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SectorRepositoryCustom {

    @Query(value = "SELECT * FROM sectors WHERE parent_id is null", nativeQuery = true)
    List<Sector> getHierarchyFromTopParents();
}
