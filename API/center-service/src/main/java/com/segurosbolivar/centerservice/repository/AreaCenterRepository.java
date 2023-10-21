package com.segurosbolivar.centerservice.repository;

import com.segurosbolivar.centerservice.model.AreaCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaCenterRepository extends JpaRepository<AreaCenter, Long> {
    @Query(value = "SELECT AREA_ID FROM GEOGRAPHIC_AREA WHERE AREA_ID = ?1", nativeQuery = true)
    Long findAreaById(Long areaId);
}
