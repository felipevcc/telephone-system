package com.segurosbolivar.centerservice.repository;

import com.segurosbolivar.centerservice.model.AreaCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaCenterRepository extends JpaRepository<AreaCenter, Long> {
    @Query("SELECT ac FROM AreaCenter ac WHERE ac.areaId = ?1 AND ac.centerId = ?2")
    AreaCenter findAreaCenterById(Long areaId, Long centerId);
}
