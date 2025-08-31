package com.conguyetduong.securitynote.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.conguyetduong.securitynote.model.AppRole;
import com.conguyetduong.securitynote.model.Role;

@DataJpaTest
class RoleRepositoryTest {

	@Autowired
	RoleRepository underTest;
	
	@Test
	void save_persistsRole_whenValid() {
		Role role = Role.builder().roleName(AppRole.ROLE_USER).build();
		Role savedRole = underTest.save(role);
		
		assertThat(savedRole.getRoleName()).isEqualTo(role.getRoleName());
	}

}
