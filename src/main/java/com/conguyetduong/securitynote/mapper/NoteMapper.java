package com.conguyetduong.securitynote.mapper;

import org.mapstruct.*;

import com.conguyetduong.securitynote.dto.NoteDto;
import com.conguyetduong.securitynote.model.Note;

@Mapper(
		componentModel = "spring",
	    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface NoteMapper {
    // -------- Entity -> DTO --------
    // This will call userMapper.toDto(note.getOwner())
	NoteDto toDto(Note note);
	
    // -------- DTO -> Entity --------
	// @Mapping(target = "id", ignore = true)
	@Mapping(target = "owner", ignore = true)
	Note toEntity(NoteDto noteDto);
	
	@BeanMapping(nullValuePropertyMappingStrategy  = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "owner", ignore = true)
	void updateFromDto(NoteDto noteDto, @MappingTarget Note note);
}
