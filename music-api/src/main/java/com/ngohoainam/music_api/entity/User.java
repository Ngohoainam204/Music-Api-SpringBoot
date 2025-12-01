package com.ngohoainam.music_api.entity;

import com.ngohoainam.music_api.enums.Roles;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@FieldDefaults (level = AccessLevel.PRIVATE)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email",nullable = false, unique = true)
    String email;

    @Column(name= "email_verified",nullable = false)
    boolean emailVerified;

    @Column(name = "password_hash", nullable = false)
    String passwordHash;

    @Column(name = "display_name", nullable = false)
    String displayName;

    @CreationTimestamp
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "status", nullable = false)
    String status;

    @Column(name = "roles")
    Roles roles;

}
