package com.conguyetduong.securitynote.service.impl;

import java.util.*;

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
public class NoteServiceImp implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final UserRepository userRepository;

    public User getCurrentUser() {
        UserDetails authUser = SecurityUtils.getCurrentUser();
        if (authUser == null) throw new IllegalStateException("No authenticated user");
        return Optional.ofNullable(userRepository.findByUsername(authUser.getUsername()))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
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
        return noteRepository.findAll().stream().map(noteMapper::toDto).toList();
    }

    @Override
    public NoteDto getById(Long id) {
        Note note = noteRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Note not found: " + id));
        return noteMapper.toDto(note);
    }

    @Override
    @Transactional // write tx (find + mutate + save as one unit)
    public NoteDto update(Long id, NoteDto NoteDto) {
        Note note = noteRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Note not found"));
        noteMapper.updateFromDto(NoteDto, note);
        
        return noteMapper.toDto(noteRepository.save(note));
    }

    @Override
    @Transactional // write tx
    public void delete(Long id) {
        Note note = noteRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Note not found: " + id));
        noteRepository.delete(note);
    }
}