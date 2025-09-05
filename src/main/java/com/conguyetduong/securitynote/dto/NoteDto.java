package com.conguyetduong.securitynote.dto;

import com.conguyetduong.securitynote.model.User;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDto {
	private Long id;
	private String title;
	private String content;
	private PublicUserDto owner;
}
