package com.conguyetduong.securitynote.service;

import java.util.*;

import com.conguyetduong.securitynote.dto.NoteDto;


public interface NoteService {
	NoteDto create(NoteDto note);
	List<NoteDto> getAll();
	NoteDto getById(Long id);
	NoteDto update(Long id, NoteDto note);
	void delete(Long id);
}