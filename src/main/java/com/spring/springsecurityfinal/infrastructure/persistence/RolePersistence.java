package com.spring.springsecurityfinal.infrastructure.persistence;

import com.spring.springsecurityfinal.domain.entity.Role;
import com.spring.springsecurityfinal.domain.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolePersistence extends JpaRepository<Role,Long> {

    Optional<Role> findRoleByRoleName(RoleName roleName);
}
