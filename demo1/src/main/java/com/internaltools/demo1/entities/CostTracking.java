package com.internaltools.demo1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity

@Table(name = "cost_tracking",
        uniqueConstraints = @UniqueConstraint(columnNames = {"tools_id", "month_year"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_id", nullable = false)
    private Tools tools;

    @Column(name = "month_year", nullable = false)
    private LocalDate monthYear;

    @Column(name = "total_monthly_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalMonthlyCost;

    @Column(name = "active_users_count", nullable = false)
    private Integer activeUsersCount = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
