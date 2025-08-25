package com.internaltools.demo1.repositories;

import com.internaltools.demo1.entities.DUsers;
import com.internaltools.demo1.entities.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DUsersRepository extends JpaRepository<DUsers, Long> {

    Optional<DUsers> findByEmail(String email);

    List<DUsers> findByStatus(Status status);

    List<DUsers> findByDepartment(com.internaltools.demo1.entities.enums.Department department);

    List<DUsers> findByRole(com.internaltools.demo1.entities.enums.Role role);
}
