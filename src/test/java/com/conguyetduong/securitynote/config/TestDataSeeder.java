package com.conguyetduong.securitynote.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import com.conguyetduong.securitynote.model.AppRole;
import com.conguyetduong.securitynote.model.Note;
import com.conguyetduong.securitynote.model.Role;
import com.conguyetduong.securitynote.model.User;
import com.conguyetduong.securitynote.repository.NoteRepository;
import com.conguyetduong.securitynote.repository.RoleRepository;
import com.conguyetduong.securitynote.repository.UserRepository;

@TestConfiguration
public class TestDataSeeder {
	@Autowired RoleRepository roleRepository;
	@Autowired UserRepository userRepository;
	@Autowired NoteRepository noteRepository;
	

	@Bean
    @Transactional
    CommandLineRunner seedTestData() {
        return args -> {
        	User user = null;
        	User admin = null;
        	
        	// --- Ensure roles exist ---
			Role userRole = Optional.ofNullable(roleRepository.findByRoleName(AppRole.ROLE_USER))
					.orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));

			Role adminRole = Optional.ofNullable(roleRepository.findByRoleName(AppRole.ROLE_ADMIN))
					.orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

			// --- Seed default USER ---
			if (userRepository.findByUsername("user") == null) {
				user = User.builder().username("user").email("user@example.com").password("{noop}password123")
						.role(userRole).build();
				userRepository.save(user);
			}

			// --- Seed default ADMIN ---
			if (userRepository.findByUsername("admin") == null) {
				admin = User.builder().username("admin").email("admin@example.com").password("{noop}password123")
						.role(adminRole).build();
				userRepository.save(admin);
			}
				
			// Seed notes
			if (noteRepository.count() == 0) {
				Note note = new Note("Title", "Content", user);
				noteRepository.save(note);
			}
        };
    }
}
	
	
	
	
	
	
	
	
	
	
	
	
	
