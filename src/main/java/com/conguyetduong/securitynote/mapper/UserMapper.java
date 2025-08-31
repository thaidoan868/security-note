package com.conguyetduong.securitynote.mapper;

import org.mapstruct.Mapper;

import com.conguyetduong.securitynote.dto.PublicUserDto;
import com.conguyetduong.securitynote.model.User;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
	//	Entity -> Dto
	PublicUserDto toDto(User user);
}
