package de.oio.service;

import de.oio.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByAuthority(String roleName);
}
