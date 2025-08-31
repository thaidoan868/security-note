package com.conguyetduong.securitynote.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@Table(name = "roles")
public class Role{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    @Setter(AccessLevel.NONE)
    private Integer id;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name")
    @NotNull(message="RoleName is required")
    private AppRole roleName;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private Set<User> users = new HashSet<>();

    @Builder
    public Role(AppRole roleName) {
        this.roleName = roleName;
    }
}
