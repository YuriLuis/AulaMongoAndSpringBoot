package com.yuri.luis.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuri.luis.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
