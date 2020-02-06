package org.sam.melchor.repository;

import org.sam.melchor.domain.Role;
import org.sam.melchor.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);

}
