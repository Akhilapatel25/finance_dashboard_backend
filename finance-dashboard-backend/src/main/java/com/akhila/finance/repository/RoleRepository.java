package com.akhila.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.akhila.finance.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}