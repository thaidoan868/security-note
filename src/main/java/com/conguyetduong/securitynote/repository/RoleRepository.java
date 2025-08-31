package com.conguyetduong.securitynote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conguyetduong.securitynote.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
