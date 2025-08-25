package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	
	// where 쿼리문을 사용해 username이 존재하는지 존재하지 않는지를 확인한다.
	boolean existsByUsername(String username);

	UserEntity findByUsername(String username);
}
