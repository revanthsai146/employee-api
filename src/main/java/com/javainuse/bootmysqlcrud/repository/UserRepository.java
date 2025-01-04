package com.javainuse.bootmysqlcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javainuse.bootmysqlcrud.entity.DAOUser;

public interface UserRepository extends JpaRepository<DAOUser, Integer> {
	DAOUser findByUsername(String name);
}
