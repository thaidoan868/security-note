package com.conguyetduong.securitynote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conguyetduong.securitynote.dto.NoteDto;
import com.conguyetduong.securitynote.service.NoteService;
import com.conguyetduong.securitynote.service.impl.NoteAdminService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {
	@Autowired NoteAdminService noteService;
	
	@GetMapping("/notes")
	public ResponseEntity<List<NoteDto>> getAll() {
		return ResponseEntity.ok(noteService.getAll());
	}
}
