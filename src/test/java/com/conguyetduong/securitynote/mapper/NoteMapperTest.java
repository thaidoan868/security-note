package com.conguyetduong.securitynote.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.conguyetduong.securitynote.dto.NoteDto;
import com.conguyetduong.securitynote.dto.PublicUserDto;
import com.conguyetduong.securitynote.model.Note;
import com.conguyetduong.securitynote.model.User;

class NoteMapperTest {
	private final NoteMapper underTest = Mappers.getMapper(NoteMapper.class);
	private Long id = 1L;
	private String title = "Title";
	private String content = "content";
	private User owner = User.builder().email("email").username("username").build();
	private PublicUserDto ownerDto = new PublicUserDto(0L, "username", "email@email.com");

	@Test
	void testToDto_mapsAllFields() {
		Note note = new Note(title, content, owner);

		NoteDto noteDto = underTest.toDto(note);

		assertThat(noteDto.getId()).isNull();;
		assertThat(noteDto.getTitle()).isEqualTo(title);
		assertThat(noteDto.getContent()).isEqualTo(content);
		assertThat(noteDto.getOwner().getUsername()).isEqualTo(owner.getUsername());
		assertThat(noteDto.getOwner().getEmail()).isEqualTo(owner.getEmail());
	}

	@Test
	void testToEntity_mapsAllFieldsExceptIdAndOwner() {
		NoteDto noteDto = new NoteDto(id, title, content, ownerDto);

		Note note = underTest.toEntity(noteDto);

		assertThat(note.getId()).isNull();
		assertThat(note.getTitle()).isEqualTo(noteDto.getTitle());
		assertThat(note.getContent()).isEqualTo(noteDto.getContent());
		assertThat(note.getOwner()).isNull();

	}

	@Test
	void testUpdateFromDto_mapsNotNullFieldsExceptIdAndOwner() {
		NoteDto noteDto = new NoteDto(id, title, content, ownerDto);
		
		User oldOwner = User.builder().email("old email").username("old username").build();
		Note oldNote = new Note("old title", "old content", oldOwner);

		underTest.updateFromDto(noteDto, oldNote);

		assertThat(oldNote.getId()).isNull();
		assertThat(oldNote.getTitle()).isEqualTo(title);
		assertThat(oldNote.getContent()).isEqualTo(content);
		assertThat(oldNote.getOwner().getEmail()).isEqualTo(oldOwner.getEmail());
		assertThat(oldNote.getOwner().getUsername()).isEqualTo(oldOwner.getUsername());


	}
}
