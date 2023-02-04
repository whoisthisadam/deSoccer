package com.kasperovich.desoccer.service.role;

import com.kasperovich.desoccer.enums.Roles;
import com.kasperovich.desoccer.models.Role;
import com.kasperovich.desoccer.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findRoleByName(Roles name) {
        return roleRepository.findAllByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Long deleteById(Long id) {
        roleRepository.deleteById(id);
        return id;
    }

    @Override
    public Role createRole(@Valid Role role) {
        return roleRepository.save(role);
    }


}
