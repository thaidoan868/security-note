package com.conguyetduong.securitynote.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.management.ManagementFactory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.conguyetduong.securitynote.model.Note;

import jakarta.validation.ConstraintViolationException;

@DataJpaTest
class NoteRepositoryTest {

	@Autowired
	private NoteRepository underTest;

	private String content = "Remove the banana trees. They are blocking my view of the sky";
	private String title = "Staturday";
	private String owner = "thaidoan868";

//	@AfterEach
//	void tearDown() {
//		underTest.deleteAll(); // If you use @DataJpaTest, tests are automatically rolled back
//	}

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
	@DisplayName("save(): Should throw ConstraintViolation Eception when saving an invalid note")
	void save_shouldThrowExceptionWhenNoteIsValid() {
		Note invalidNote = new Note(title, content, null);
		
		assertThatThrownBy(() -> underTest.save(invalidNote)).isInstanceOf(ConstraintViolationException.class);
	}
	

	@Test
	@DisplayName("runMockitoAsJavaAgent: Mockito-core must be run as a java agent to work with future JDK realeases")
	void runMockitoAsJavaAgent() {
		System.out.println("INPUT ARGS = " + ManagementFactory.getRuntimeMXBean().getInputArguments());
		assert ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("mockito-core");
	}

}































