package com.conguyetduong.securitynote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.conguyetduong.securitynote.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
    @EntityGraph(attributePaths = "owner")
    List<Note> findAllByOwner_Username(String username);
    
    @EntityGraph(attributePaths = "owner")
    Optional<Note> findByIdAndOwner_Username(Long id, String username);
}
