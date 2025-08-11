package com.conguyetduong.securitynote.mapper;

import org.mapstruct.*;

import com.conguyetduong.securitynote.dto.NoteDto;
import com.conguyetduong.securitynote.model.Note;

@Mapper(componentModel = "spring")
public interface NoteMapper {
	NoteDto toDto(Note note);
	
	Note toEntity(NoteDto noteDto);
	
	@BeanMapping(nullValuePropertyMappingStrategy  = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromDto(NoteDto noteDto, @MappingTarget Note note);
}
