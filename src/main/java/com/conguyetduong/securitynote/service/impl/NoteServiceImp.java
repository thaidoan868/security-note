package com.conguyetduong.securitynote.service.impl;

import java.util.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.conguyetduong.securitynote.dto.NoteDto;
import com.conguyetduong.securitynote.mapper.NoteMapper;
import com.conguyetduong.securitynote.model.Note;
import com.conguyetduong.securitynote.model.User;
import com.conguyetduong.securitynote.repository.NoteRepository;
import com.conguyetduong.securitynote.repository.UserRepository;
import com.conguyetduong.securitynote.service.NoteService;
import com.conguyetduong.securitynote.util.SecurityUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // all methods are read-only by default
@PreAuthorize("isAuthenticated()")
//@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class NoteServiceImp implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final UserRepository userRepository;

    public User getCurrentUser() {
        UserDetails authUser = SecurityUtils.getCurrentUser();
        return userRepository.findByUsername(authUser.getUsername());
    }
    
    private String getCurrentUsername() {
		UserDetails authUser = SecurityUtils.getCurrentUser();
		return authUser.getUsername();
	}
    
    private Note getNoteIfOwner(Long id) {
		return noteRepository.findByIdAndOwner_Username(id, getCurrentUsername())
				.orElseThrow(() -> new EntityNotFoundException("Note not found: " + id));
	}
    
    @Override
    @Transactional // write tx
    public NoteDto create(NoteDto NoteDto) {
        Note note = noteMapper.toEntity(NoteDto);
        note.setOwner(getCurrentUser());
        return noteMapper.toDto(noteRepository.save(note));
    }

    @Override
    public List<NoteDto> getAll() {
        return noteRepository.findAllByOwner_Username(getCurrentUsername()).stream().map(noteMapper::toDto).toList();
    }

    @Override
    public NoteDto getById(Long id) {
        Note note = getNoteIfOwner(id);
        return noteMapper.toDto(note);
    }

    @Override
    @Transactional // write tx (find + mutate + save as one unit)
    public NoteDto update(Long id, NoteDto NoteDto) {
        Note note = getNoteIfOwner(id);
        noteMapper.updateFromDto(NoteDto, note);
        
        return noteMapper.toDto(noteRepository.save(note));
    }

    @Override
    @Transactional // write tx
    public void delete(Long id) {
        Note note = getNoteIfOwner(id);
        noteRepository.delete(note);
    }
}