package com.conguyetduong.securitynote.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conguyetduong.securitynote.dto.NoteDto;
import com.conguyetduong.securitynote.service.NoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notes")
public class NoteController {
	
	private final NoteService noteService;
	/*
	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	} */

	@PostMapping
	public ResponseEntity<NoteDto> create(@RequestBody NoteDto NoteDto) {
		return ResponseEntity.ok(noteService.create(NoteDto));
	}
	
	@GetMapping
	public ResponseEntity<List<NoteDto>> getAll() {
		return ResponseEntity.ok(noteService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NoteDto> getById(@PathVariable Long id) {
		return ResponseEntity.ok(noteService.getById(id));
	}
	
	@PatchMapping("/{id}")
    public ResponseEntity<NoteDto> update(@PathVariable Long id, @RequestBody NoteDto NoteDto) {
        return ResponseEntity.ok(noteService.update(id, NoteDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.ok("Note with ID " + id + " deleted successfully.");
    }
}