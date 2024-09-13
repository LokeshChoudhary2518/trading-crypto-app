package com.trading.crypto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.crypto.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	User findByEmail(String email);
}
