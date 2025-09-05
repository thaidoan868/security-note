package com.conguyetduong.securitynote.config;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.conguyetduong.securitynote.model.AppRole;
import com.conguyetduong.securitynote.model.Role;
import com.conguyetduong.securitynote.model.User;
import com.conguyetduong.securitynote.repository.RoleRepository;
import com.conguyetduong.securitynote.repository.UserRepository;

@Configuration
public class DataSeeder {

	@Bean
	@Transactional
	CommandLineRunner seedUsersAndRoles(RoleRepository roleRepository, UserRepository userRepository) {
		return args -> {
			// --- Ensure roles exist ---
			Role userRole = Optional.ofNullable(roleRepository.findByRoleName(AppRole.ROLE_USER))
					.orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));

			Role adminRole = Optional.ofNullable(roleRepository.findByRoleName(AppRole.ROLE_ADMIN))
					.orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

			// --- Seed default USER ---
			if (userRepository.findByUsername("user") == null) {
				User user = User.builder().username("user").email("user@example.com").password("{noop}password123")
						.role(userRole).build();
				userRepository.save(user);
			}

			// --- Seed default ADMIN ---
			if (userRepository.findByUsername("admin") == null) {
				User admin = User.builder().username("admin").email("admin@example.com").password("{noop}password123")
						.role(adminRole).build();
				userRepository.save(admin);
			}
		};
	}
}
