package com.segurosbolivar.centerservice.repository;

import com.segurosbolivar.centerservice.model.Center;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
