package com.conguyetduong.securitynote.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_username", columnNames = "username"),
        @UniqueConstraint(name = "uk_users_email", columnNames = "email")
    },
    indexes = {
        @Index(name = "idx_users_username", columnList = "username"),
        @Index(name = "idx_users_email", columnList = "email")
    }
)
@Data
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class User {

    // ---------- Identity ----------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    @ToString.Include
    @Setter(AccessLevel.NONE)
    private Long id;

    // ---------- Core info ----------
    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 20, message = "Username must be between {min} and {max} characters.")
    @Pattern(
        regexp = "^[A-Za-z0-9._-]+$",
        message = "Username can only contain letters, numbers, dots, underscores, and hyphens."
    )
    @Column(name = "username", nullable = false, length = 20)
    @ToString.Include
    private String username;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email must be a valid address.")
    @Size(max = 255, message = "Email must not exceed {max} characters.")
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Size(min = 8, max = 120, message = "Password must be between {min} and {max} characters.")
    @NotBlank
    @Column(name = "password", length = 120)
    @JsonIgnore // never serialize password
    private String password;

    // ---------- Security flags ----------
    @Column(nullable = false)
    private boolean accountNonLocked = true;

    @Column(nullable = false)
    private boolean accountNonExpired = true;

    @Column(nullable = false)
    private boolean credentialsNonExpired = true;

    @Column(nullable = false)
    private boolean enabled = true;

    // Optional expiries (null = not set)
    private LocalDate credentialsExpiryDate;
    private LocalDate accountExpiryDate;

    // 2FA
    @Size(max = 255, message = "Two-factor secret must not exceed {max} characters.")
    @JsonIgnore // sensitive
    private String twoFactorSecret;

    @Column(nullable = false)
    private boolean twoFactorEnabled = false;

    @Size(max = 50, message = "Sign-up method must not exceed {max} characters.")
    @Column(length = 50)
    private String signUpMethod;

    // ---------- Relations ----------
    @NotNull(message = "Role is required.")
    @ManyToOne(fetch = FetchType.LAZY) // avoid N+1 and large payloads
    @JoinColumn(name = "role_id", nullable = false)
    @JsonBackReference
    private Role role;

    // ---------- Audit ----------
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedDate;
    
    // ---------- Constructor --------------
    @Builder
    public User(String userName, String email, String password, Role role) {
        this.username = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // ---------- Equality (by id) ----------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

