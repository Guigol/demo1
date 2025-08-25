package com.internaltools.demo1.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.internaltools.demo1.entities.enums.Role;
import com.internaltools.demo1.entities.enums.Department;
import com.internaltools.demo1.entities.enums.Status;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "users")
@Data // génère getters/setters/toString/equals/hashCode
@AllArgsConstructor // constructeur avec tous les champs
@Builder // optionnel : permet de créer un User avec User.builder()...
public class DUsers {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private String hireDate; // ⚠️ si c’est une date, mieux vaut LocalDate !

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
