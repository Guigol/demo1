package com.internaltools.demo1.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.internaltools.demo1.entities.DUsers;

@Entity
@Table(
        name = "user_tool_access",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_users_tools_active",
                        columnNames = {"users_id", "tools_id", "status"}
                )
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserToolAccess {

    public enum Status {
        ACTIVE,
        REVOKED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private DUsers dusers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_id", nullable = false)
    private Tools tools;

    @Column(updatable = false)
    private LocalDateTime grantedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "granted_by", nullable = false)
    private DUsers grantedBy;

    private LocalDateTime revokedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revoked_by")
    private DUsers revokedBy;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIVE;

    @PrePersist
    protected void onCreate() {
        this.grantedAt = LocalDateTime.now();
    }
}
