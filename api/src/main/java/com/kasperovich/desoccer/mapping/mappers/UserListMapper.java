package com.kasperovich.desoccer.mapping.mappers;


import com.kasperovich.desoccer.dto.users.UserCreateDto;
import com.kasperovich.desoccer.dto.users.UserGetDto;
import com.kasperovich.desoccer.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserListMapper {

    List<UserGetDto> toDtoList(List<User>userList);

    List<User>toEntityList(List<UserCreateDto>dtoList);

}
