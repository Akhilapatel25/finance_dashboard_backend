package com.akhila.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.akhila.finance.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	
}