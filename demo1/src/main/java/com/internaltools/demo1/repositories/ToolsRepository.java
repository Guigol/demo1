package com.internaltools.demo1.repositories;

import com.internaltools.demo1.entities.Tools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ToolsRepository extends JpaRepository<Tools, Integer>, JpaSpecificationExecutor<Tools> {
    List<Tools> findByCategory_Id(Integer categoryId);
    List<Tools> findByOwnerDepartment(Tools.OwnerDepartment ownerDepartment);
    List<Tools> findByStatus(Tools.Status status);
}

