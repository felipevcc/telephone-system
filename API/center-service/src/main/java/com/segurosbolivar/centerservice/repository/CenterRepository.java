package com.segurosbolivar.centerservice.repository;

import com.segurosbolivar.centerservice.dto.CenterCreationDTO;
import com.segurosbolivar.centerservice.model.Center;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {

    @Query("SELECT COUNT(*) FROM Center center " +
            "JOIN AreaCenter ac ON ac.centerId = center.centerId " +
            "WHERE ac.areaId = ?1")
    Long countCenters(Long areaId);

    @Query("SELECT center FROM Center center " +
            "JOIN AreaCenter ac ON ac.centerId = center.centerId " +
            "WHERE ac.areaId = ?1 " +
            "ORDER BY center.createdAt DESC")
    List<Center> findCentersByArea(Long areaId, Pageable pageable);

    @Procedure(procedureName = "PCK_CENTER.Proc_CreateCenter")
    Long createCenter(@Param("Ip_center_data") CenterCreationDTO newCenterData);

    @Query(value = "CALL PCK_CENTER.Proc_CreateCenter(:Ip_center_data);", nativeQuery = true)
    Long createCenterr(@Param("Ip_center_data") CenterCreationDTO newCenterData);
}
