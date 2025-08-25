package com.internaltools.demo1.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tools")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tools {

    public enum OwnerDepartment {
        ENGINEERING,
        SALES,
        MARKETING,
        HR,
        FINANCE,
        OPERATIONS,
        DESIGN
    }

    public enum Status {
        ACTIVE,
        DEPRECATED,
        TRIAL
    }

    @Id
    @Column(name = "TOOL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String vendor;

    @Column(length = 255)
    private String websiteUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monthlyCost;

    @Column(nullable = false)
    @Builder.Default
    private Integer activeUsersCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OwnerDepartment ownerDepartment;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIVE;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
