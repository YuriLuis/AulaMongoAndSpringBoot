package com.yuri.luis.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuri.luis.model.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

	
}
