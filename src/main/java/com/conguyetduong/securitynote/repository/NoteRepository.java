package com.conguyetduong.securitynote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conguyetduong.securitynote.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
