package com.infaspects.pet.service;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.infaspects.pet.domain.Counter;

/**
 * Service to generate the Sequence id for domain objects
 * 
 * @author angular
 *
 */

@Service
public class CounterService {

	@Autowired
	private MongoOperations operations;
	
	public void deleteSequenceForCollection(String collectionName) {
		operations.dropCollection(collectionName);
	}

	public int getNextSequence(String collectionName) {
		
		Counter counter = operations.findAndModify(
				query(where("_id").is(collectionName)),
				new Update().inc("seq", 1),
				options().upsert(true), 
				Counter.class);

		System.out.println(counter.toString());
		
		return counter.getSeq();
	}
}
