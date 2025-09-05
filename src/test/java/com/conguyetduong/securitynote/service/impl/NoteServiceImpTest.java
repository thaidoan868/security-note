package com.conguyetduong.securitynote.service.impl;

import com.conguyetduong.securitynote.dto.NoteDto;
import com.conguyetduong.securitynote.mapper.NoteMapper;
import com.conguyetduong.securitynote.mapper.NoteMapperImpl;
import com.conguyetduong.securitynote.model.Note;
import com.conguyetduong.securitynote.model.User;
import com.conguyetduong.securitynote.repository.NoteRepository;
import com.conguyetduong.securitynote.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class NoteServiceImpTest {

	@Autowired NoteServiceImp underTest; // real service + repo + mapper
	@Autowired NoteRepository noteRepo; // has been tested
	@Autowired UserRepository userRepo;
	@Autowired NoteMapper noteMapper;
	

	private String title = "Monday";
	private String content = "Get things done";
	private User owner;
	
	
	@BeforeEach
	void setUp() {
		owner = userRepo.findByUsername("user");
	}

	// ---------- tests ----------

	@Test
	@WithMockUser(username="user")
	void create_persistsNote_whenValid() {
		User currentUser = userRepo.findByUsername("user");
		NoteDto note = new NoteDto(null, title, content, null);
		
		NoteDto createdNote = underTest.create(note);

		assertThat(createdNote.getId()).as("id should be generated").isNotNull();
		assertThat(createdNote.getTitle()).isEqualTo(note.getTitle());
		assertThat(createdNote.getContent()).isEqualTo(note.getContent());
		assertThat(createdNote.getOwner().getUsername()).isEqualTo(currentUser.getUsername());
	}

	@Test
	void getAll_initially_empty_then_contains_created_note() {
		if(noteRepo.count() == 0) {
			assertThat(underTest.getAll()).isEmpty();
		}

		Note note = noteRepo.save(new Note(title, content, owner));
		NoteDto noteDto = noteMapper.toDto(note);

		List<NoteDto> noteList = underTest.getAll();
		

		assertThat(noteList).contains(noteDto);
	}

	@Test
	void getById_returns_note_when_present() {
		Note note = noteRepo.save(new Note(title, content, owner));
		
		NoteDto retrievedNote = underTest.getById(note.getId());

		assertThat(retrievedNote.getId()).isEqualTo(note.getId());
		assertThat(retrievedNote.getContent()).isEqualTo(note.getContent());
		assertThat(retrievedNote.getTitle()).isEqualTo(note.getTitle());
		assertThat(retrievedNote.getOwner().getUsername()).isEqualTo(note.getOwner().getUsername());
	}

	@Test
	void getById_throws_when_missing() {
		assertThatThrownBy(() -> underTest.getById(9_999_999L)).isInstanceOf(EntityNotFoundException.class)
				.hasMessageContaining("9");
	}

	@Test
	void update_overwrites_mutable_fields_and_persists() {
		Note oldNote = noteRepo.save(new Note(title, content, owner));
		String newTitle = "New Title: Monday";
		NoteDto patchNote = NoteDto.builder().title(newTitle).build();
		
		NoteDto updatedNote = underTest.update(oldNote.getId(), patchNote);

		assertThat(updatedNote.getId()).isEqualTo(oldNote.getId());
		assertThat(updatedNote.getTitle()).isEqualTo(newTitle);
		assertThat(updatedNote.getContent()).isEqualTo(oldNote.getContent());
		assertThat(updatedNote.getOwner().getUsername()).isEqualTo(oldNote.getOwner().getUsername());
	}

	@Test
	void update_throws_when_missing() {
		assertThatThrownBy(() -> underTest.update(123456L, new NoteDto()))
				.isInstanceOf(EntityNotFoundException.class);
	}

	@Test
	void delete_removes_row() {
		Note note = noteRepo.save(new Note(title, content, owner));

		underTest.delete(note.getId());

		Optional<Note> deletedNote = noteRepo.findById(note.getId());
		assertThat(deletedNote.isEmpty()).isTrue();
	}

	@Test
	void delete_throws_when_missing() {
		assertThatThrownBy(() -> underTest.delete(777L)).isInstanceOf(EntityNotFoundException.class);
	}
}
