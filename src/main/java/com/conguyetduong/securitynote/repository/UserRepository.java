package com.conguyetduong.securitynote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conguyetduong.securitynote.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
