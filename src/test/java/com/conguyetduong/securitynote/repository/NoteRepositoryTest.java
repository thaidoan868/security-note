package com.conguyetduong.securitynote.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.management.ManagementFactory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.conguyetduong.securitynote.model.AppRole;
import com.conguyetduong.securitynote.model.Note;
import com.conguyetduong.securitynote.model.Role;
import com.conguyetduong.securitynote.model.User;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;

@DataJpaTest
class NoteRepositoryTest {

	@Autowired
	NoteRepository underTest;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired	
	RoleRepository roleRepo;

	private String content = "Remove the banana trees. They are blocking my view of the sky";
	private String title = "Staturday";
	private User owner;
	private Role role;

//	@AfterEach
//	void tearDown() {
//		underTest.deleteAll(); // If you use @DataJpaTest, tests are automatically rolled back
//	}
	@BeforeEach
	void setUp() {
		role = roleRepo.save(new Role(AppRole.ROLE_USER));
		owner = userRepo.save(new User("username", "email@gmail.com", "passowrd", role));
	}

	@Test
	@DisplayName("save(): Should persist a new note")
	void save_persistsNote() {
		Note note = new Note(title, content, owner);

		Note savedNote = underTest.save(note);

		assertThat(savedNote.getTitle()).isEqualTo(note.getTitle());
		assertThat(savedNote.getContent()).isEqualTo(note.getContent());
		assertThat(savedNote.getOwner()).isEqualTo(note.getOwner());

	}
	
	@Test
	@DisplayName("save(): Should throw ConstraintViolation Exception when saving an invalid note")
	void save_shouldThrowException_WhenNoteIsValid() {
		Note invalidNote = new Note(title, content, null);
		
		assertThatThrownBy(() -> underTest.save(invalidNote)).isInstanceOf(ConstraintViolationException.class);
	}
	
	@Autowired EntityManager em;

	@Test
	void save_ShouldNotUpdateNestedOwner() {
	    // arrange
	    String originalEmail = owner.getEmail();

	    Note savedNote = underTest.save(new Note(title, content, owner));
	    Long ownerId = savedNote.getOwner().getId();

	    // Detach the owner entity to prevent automatic update
	    em.detach(owner);
	    savedNote.getOwner().setEmail("new-email@gmail.com");

	    // act
	    underTest.save(savedNote);

	    // assert against the DB state, not the managed object
	    User reloadedOwner = userRepo.findById(ownerId).orElseThrow();
	    assertThat(reloadedOwner.getEmail()).isEqualTo(originalEmail);
	}


	@Test
	void save_ShouldThrowException_WhenOwnerIsNotPersisted() {
	    User notSavedOwner = new User("username", "email", "password", role);
	    Note note = new Note(title, content, notSavedOwner);

	    assertThatThrownBy(() -> underTest.save(note))
	        .isInstanceOf(Exception.class);
	}

//	@Test
//	@DisplayName("runMockitoAsJavaAgent: Mockito-core must be run as a java agent to work with future JDK realeases")
//	@Disabled
//	void runMockitoAsJavaAgent() {
//		System.out.println("INPUT ARGS = " + ManagementFactory.getRuntimeMXBean().getInputArguments());
//		assert ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("mockito-core");
//	}

}