package com.infaspects.pet.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Managing pet counter
 * 
 * @author angular
 *
 */
@Document(collection="counters")
public class Counter {
	
	@Id
	private String collectionName;
	
	private int seq;

	public String getId() {
		return collectionName;
	}

	public void setId(String id) {
		this.collectionName = id;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	@Override
	public String toString() {
		return "id: "+collectionName+" ,seq:"+seq;
	}

}
