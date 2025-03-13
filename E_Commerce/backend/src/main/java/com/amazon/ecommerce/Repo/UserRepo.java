package com.amazon.ecommerce.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.ecommerce.Model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	int countByUserName(String username);

	User findByUserName(String username);
}