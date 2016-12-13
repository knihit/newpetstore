package com.infaspects.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infaspects.pet.domain.Tag;
import com.infaspects.pet.repository.TagRepository;

@Service
public class TagService {
	
	@Autowired
	private TagRepository tagRepository;
	
	public Tag read(Tag tag) {
		return tag;
	}
	
	public Iterable<Tag> readAll(){
		return tagRepository.findAll();
	}
}
