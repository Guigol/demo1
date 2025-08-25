package com.internaltools.demo1.dtos;

import com.internaltools.demo1.entities.Tools;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ToolCreateDto {
    private String name;
    private String description;
    private String vendor;
    private String websiteUrl;
    private BigDecimal monthlyCost;
    private Integer activeUsersCount;
    private Tools.OwnerDepartment ownerDepartment;
    private Tools.Status status;
    private Integer categoryId;
}
