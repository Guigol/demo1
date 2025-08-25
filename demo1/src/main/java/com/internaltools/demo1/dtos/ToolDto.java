package com.internaltools.demo1.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class ToolDto {
    private Integer id;
    private String name;
    private String vendor;
    private String categoryName;
    private String description;
    private String websiteUrl;
    private BigDecimal monthlyCost;
    private String ownerDepartment;
    private String status;
    private Integer activeUsersCount;
    private BigDecimal totalMonthlyCost;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Map<String, Map<String, Object>> usageMetrics;

    // Constructeur complet
    public ToolDto(Integer id,
                   String name,
                   String vendor,
                   String categoryName,
                   String description,
                   String websiteUrl,
                   BigDecimal monthlyCost,
                   String ownerDepartment,
                   String status,
                   Integer activeUsersCount,
                   BigDecimal totalMonthlyCost,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   Map<String, Map<String, Object>> usageMetrics) {
        this.id = id;
        this.name = name;
        this.vendor = vendor;
        this.categoryName = categoryName;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.monthlyCost = monthlyCost;
        this.ownerDepartment = ownerDepartment;
        this.status = status;
        this.activeUsersCount = activeUsersCount;
        this.totalMonthlyCost = totalMonthlyCost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.usageMetrics = usageMetrics;
    }

    // Getters et Setters
    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getVendor() { return vendor; }
    public String getCategoryName() { return categoryName; }
    public String getDescription() { return description; }
    public String getWebsiteUrl() { return websiteUrl; }
    public BigDecimal getMonthlyCost() { return monthlyCost; }
    public String getOwnerDepartment() { return ownerDepartment; }
    public String getStatus() { return status; }
    public Integer getActiveUsersCount() { return activeUsersCount; }
    public BigDecimal getTotalMonthlyCost() { return totalMonthlyCost; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Map<String, Map<String, Object>> getUsageMetrics() { return usageMetrics; }
}
