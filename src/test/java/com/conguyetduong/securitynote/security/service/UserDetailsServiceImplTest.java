package com.conguyetduong.securitynote.security.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.conguyetduong.securitynote.model.AppRole;
import com.conguyetduong.securitynote.model.Role;
import com.conguyetduong.securitynote.model.User;
import com.conguyetduong.securitynote.repository.RoleRepository;
import com.conguyetduong.securitynote.repository.UserRepository;

@SpringBootTest
@Transactional
class UserDetailsServiceImplTest {
	
	@Autowired
	UserDetailsServiceImpl underTest;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	private Role role;
	private User user;
	
	
	@BeforeEach
	void setUp() {
		role = roleRepo.save(new Role(AppRole.ROLE_USER));
		user = userRepo.save(new User("username", "email", "password", role));
	}

	@Test
	void findByUsername_returnsValidUserDetailsImpl() {
		UserDetailsImpl userDetails = underTest.loadUserByUsername(user.getUsername());
		
		assertThat(userDetails.getUsername()).isEqualTo(user.getUsername());
		assertThat(userDetails.getEmail()).isEqualTo(user.getEmail());
	}

}























