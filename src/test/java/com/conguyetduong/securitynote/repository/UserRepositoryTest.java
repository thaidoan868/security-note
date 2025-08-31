package com.conguyetduong.securitynote.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.conguyetduong.securitynote.model.AppRole;
import com.conguyetduong.securitynote.model.Role;
import com.conguyetduong.securitynote.model.User;

import jakarta.validation.ConstraintViolationException;

@DataJpaTest
class UserRepositoryTest {
	
	@Autowired
	UserRepository underTest;
	
	@Autowired
	RoleRepository roleRepo;
	
	private String username = "thaidoan878";
	private String email = "thaidoan@conguyetduong.vn";
	private String password = "lethanhtong1442";
	
	private Role role = new Role(AppRole.ROLE_USER);
	

	@Test
	void save_persistsUser_whenValid() {
	    // given
	    Role savedRole = roleRepo.save(role);
	    User user = new User(username, email, password, savedRole);

	    // when
	    User savedUser = underTest.save(user);

	    // then
	    assertThat(savedUser).isNotNull();
	    assertThat(savedUser.getId()).isNotNull();  // JPA should generate an ID
	    assertThat(savedUser.getUsername()).isEqualTo(username);
	    assertThat(savedUser.getEmail()).isEqualTo(email);
	    assertThat(savedUser.getPassword()).isEqualTo(password);
	    assertThat(savedUser.getRole()).isEqualTo(savedRole);
	}
	
	@Test
    void save_throws_whenRequiredFieldsMissing() {
        // given: valid role, but invalid user (e.g., null username)
        Role savedRole = roleRepo.save(role);
        User invalidUser = new User(null, email, password, savedRole); // username is required

        // when + then
        assertThatThrownBy(() -> underTest.saveAndFlush(invalidUser))
            .isInstanceOfAny(ConstraintViolationException.class, DataIntegrityViolationException.class);
        // - If you use Bean Validation (@NotBlank/@NotNull): ConstraintViolationException
        // - If DB enforces NOT NULL: DataIntegrityViolationException
    }
	
	@Test
	void findByUsername_returnsUser_whenExists() {
	    // given
	    Role savedRole = roleRepo.save(role);
	    User user = new User(username, email, password, savedRole);
	    underTest.saveAndFlush(user);

	    // when
	    User found = underTest.findByUsername(username);

	    // then
	    assertThat(found).isNotNull();
	    assertThat(found.getUsername()).isEqualTo(username);
	    assertThat(found.getEmail()).isEqualTo(email);
	    assertThat(found.getRole()).isEqualTo(savedRole);
	}

	@Test
	void findByUsername_returnsEmpty_whenNotExists() {
	    // when
	    var result = underTest.findByUsername("does_not_exist");

	    // then
	    assertThat(result).isNull();;
	}

}
