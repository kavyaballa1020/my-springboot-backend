package com.main.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.main.model.Pond;

public interface PondRepository extends MongoRepository<Pond, String> {
	
}