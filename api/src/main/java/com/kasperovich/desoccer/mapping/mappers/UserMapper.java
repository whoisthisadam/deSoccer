package com.kasperovich.desoccer.mapping.mappers;

import com.kasperovich.desoccer.dto.users.UserCreateDto;
import com.kasperovich.desoccer.dto.users.UserGetDto;
import com.kasperovich.desoccer.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserGetDto toDto(User user);

    User toEntity(UserCreateDto userCreateDto);

}

