package com.kasperovich.desoccer.mapping.mappers;

import com.kasperovich.desoccer.dto.roles.RoleCreateDto;
import com.kasperovich.desoccer.dto.roles.RoleGetDto;
import com.kasperovich.desoccer.models.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface RoleListMapper {

    public List<Role>toEntityList(List<RoleCreateDto> roleRequestList);

    public List<RoleGetDto>toResponsesList(List<Role>roleList);

}
