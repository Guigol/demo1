package com.internaltools.demo1.repositories;

import com.internaltools.demo1.entities.UserToolAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserToolAccessRepository extends JpaRepository<UserToolAccess, Integer> {

    List<UserToolAccess> findByDusers_Id(Long userId);

    List<UserToolAccess> findByTools_Id(Long toolId);

    List<UserToolAccess> findByStatus(UserToolAccess.Status status);
}

