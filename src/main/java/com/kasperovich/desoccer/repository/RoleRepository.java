package com.kasperovich.desoccer.repository;

import com.kasperovich.desoccer.enums.Roles;
import com.kasperovich.desoccer.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findAllByName(Roles name);

}
