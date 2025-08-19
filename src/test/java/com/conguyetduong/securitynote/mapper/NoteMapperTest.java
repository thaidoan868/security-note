package com.conguyetduong.securitynote.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.conguyetduong.securitynote.dto.NoteDto;
import com.conguyetduong.securitynote.model.Note;



class NoteMapperTest {
	private final NoteMapper underTest = Mappers.getMapper(NoteMapper.class);
	private Long id = 1L;
	private String title = "Work List";
	private String content = "Today is a busy day. I'm gonna do a lot stuff";
	private String owner = "thaidoan878";
	
	@Test
	void testToDto_mapsAllFields() {
		Note note = Note.builder().title(title).content(content).owner(owner).build();
		
		NoteDto noteDto = underTest.toDto(note);

		assertAll(
			    () -> assertThat(noteDto.getId()).isEqualTo(note.getId()),
			    () -> assertThat(noteDto.getTitle()).isEqualTo(note.getTitle()),
			    () -> assertThat(noteDto.getContent()).isEqualTo(note.getContent()),
			    () -> assertThat(noteDto.getOwner()).isEqualTo(note.getOwner())
			);
	}

	@Test
	void testToEntity_mapsAllFieldsExceptId() {
		NoteDto noteDto = NoteDto.builder().id(id).title(title).content(content).owner(owner).build();
		
		Note note = underTest.toEntity(noteDto);
		
		assertAll(
			    () -> assertThat(note.getId()).isNull(),
			    () -> assertThat(note.getTitle()).isEqualTo(noteDto.getTitle()),
			    () -> assertThat(note.getContent()).isEqualTo(noteDto.getContent()),
			    () -> assertThat(note.getOwner()).isEqualTo(noteDto.getOwner())
			);
	} 

	@Test
	void testUpdateFromDto_mapsNotNullFieldsExceptId() {
		NoteDto noteDto = NoteDto.builder().id(id).title(title).content(null).owner(null).build();
		Note note = Note.builder().title("An existing title").content(content).owner(owner).build();

		underTest.updateFromDto(noteDto, note);
		
		assertAll(
			    () -> assertThat(note.getId()).isNull(),
			    () -> assertThat(note.getTitle()).isEqualTo(noteDto.getTitle()),
			    () -> assertThat(note.getContent()).isEqualTo(content),
			    () -> assertThat(note.getOwner()).isEqualTo(owner)
			);
		
	}
}





