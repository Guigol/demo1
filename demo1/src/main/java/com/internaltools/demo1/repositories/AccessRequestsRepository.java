package com.internaltools.demo1.repositories;

import com.internaltools.demo1.entities.AccessRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessRequestsRepository extends JpaRepository<AccessRequests, Long> {

    // Récupérer toutes les demandes d’un utilisateur
    List<AccessRequests> findByDusers_Id(Long userId);

    // Récupérer toutes les demandes d’un outil
    List<AccessRequests> findByTools_Id(Long toolId);
}
