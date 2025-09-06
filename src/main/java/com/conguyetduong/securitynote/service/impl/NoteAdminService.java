package com.conguyetduong.securitynote.service.impl;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conguyetduong.securitynote.dto.NoteDto;
import com.conguyetduong.securitynote.mapper.NoteMapper;
import com.conguyetduong.securitynote.repository.NoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@PreAuthorize("hasRole('ADMIN')")
public class NoteAdminService {
	private final NoteRepository noteRepository;
	private final NoteMapper noteMapper;
	
	public List<NoteDto> getAll() {
		return noteRepository.findAll().stream().map(noteMapper::toDto).toList();
	}
}
