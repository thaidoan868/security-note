package com.conguyetduong.securitynote.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicUserDto {
	private Long id;
	private String username;
	private String email;
}
