package com.climatesystem.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.climatesystem.model.UserModel;

@Repository
public interface UserDao extends CrudRepository<UserModel, Long>{
	UserModel findByUsername(String username);
}
