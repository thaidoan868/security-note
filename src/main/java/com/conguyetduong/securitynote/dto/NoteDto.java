package com.conguyetduong.securitynote.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDto {
	private Long id;
	private String title;
	private String content;
	private String owner;
}
