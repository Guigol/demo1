package com.internaltools.demo1.repositories;

import com.internaltools.demo1.entities.CostTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public interface CostTrackingRepository extends JpaRepository<CostTracking, Integer> {
       Optional<CostTracking> findByTools_IdAndMonthYear(Integer toolsId, LocalDate monthYear);
    List<CostTracking> findByTools_Id(Integer toolsId);


}

