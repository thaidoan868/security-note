package com.conguyetduong.securitynote.service.impl;

import java.util.*;

import org.springframework.stereotype.Service;

import com.conguyetduong.securitynote.dto.NoteDto;
import com.conguyetduong.securitynote.mapper.NoteMapper;
import com.conguyetduong.securitynote.model.Note;
import com.conguyetduong.securitynote.repository.NoteRepository;
import com.conguyetduong.securitynote.service.NoteService;

import jakarta.persistence.EntityNotFoundException;
import lombok.*;

@Service
@RequiredArgsConstructor
public class NoteServiceImp implements NoteService {
	private final NoteRepository noteRepository;
	private final NoteMapper noteMapper;
	
/*
	public NoteServiceImp(NoteRepository noteRepository, NoteMapper noteMapper) {
		super();
		this.noteRepository = noteRepository;
		this.noteMapper = noteMapper;
	} */

	@Override
	public NoteDto create(NoteDto noteDto) {
		Note entity = noteMapper.toEntity(noteDto);
		return noteMapper.toDto(noteRepository.save(entity));
	}

	@Override
	public List<NoteDto> getAll() {
		return noteRepository.findAll().stream().map(noteMapper::toDto).toList();
	}

	@Override
	public NoteDto getById(Long id) {
		Note note = noteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Note not found: "+ id));
		return noteMapper.toDto(note);
	}

	@Override
	public NoteDto update(Long id, NoteDto noteDto) {
		Note note = noteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Note not found: "+ id));
		noteMapper.updateFromDto(noteDto, note);
		return noteMapper.toDto(noteRepository.save(note));
	}
	
	@Override
	public void delete(Long id) {
		Note note = noteRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Note not found: "+ id));
		noteRepository.delete(note);
	}
}


















