package com.kasperovich.desoccer.service.role;

import com.kasperovich.desoccer.enums.Roles;
import com.kasperovich.desoccer.models.Role;

import java.util.List;

public interface RoleService {

    public Role findRoleByName(Roles name);

    public List<Role> findAll();

    public Long deleteById(Long id);

    public Role createRole(Role role);

}
