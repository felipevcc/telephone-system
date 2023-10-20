package com.segurosbolivar.areaservice.repository;

import com.segurosbolivar.areaservice.model.GeographicArea;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<GeographicArea, Long> {

    List<GeographicArea> findAllByOrderByAreaId();

    @Query("SELECT COUNT(*) FROM GeographicArea area " +
            "JOIN AreaCenter ac ON ac.areaId = area.areaId " +
            "WHERE ac.centerId = ?1")
    Long countAreas(Long centerId);

    @Query("SELECT area FROM GeographicArea area " +
            "JOIN AreaCenter ac ON ac.areaId = area.areaId " +
            "WHERE ac.centerId = ?1 " +
            "ORDER BY area.areaId ASC")
    List<GeographicArea> findAreasByCenter(Long centerId, Pageable pageable);
}
