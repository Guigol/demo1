package com.internaltools.demo1.repositories;

import com.internaltools.demo1.entities.UsageLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UsageLogsRepository extends JpaRepository<UsageLogs, Integer> {
    List<UsageLogs> findBydUsers_Id(Long userId);
    List<UsageLogs> findByTools_Id(Integer toolId);
    List<UsageLogs> findBySessionDate(LocalDate sessionDate);
}
