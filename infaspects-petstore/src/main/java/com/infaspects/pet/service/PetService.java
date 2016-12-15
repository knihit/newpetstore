package com.infaspects.pet.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infaspects.pet.domain.Category;
import com.infaspects.pet.domain.Pet;
import com.infaspects.pet.domain.Tag;
//import com.infaspects.pet.repository.CategoryRepository;
import com.infaspects.pet.repository.PetRepository;
import com.infaspects.pet.repository.TagRepository;

@Service
public class PetService {
	private static final Logger logger = LoggerFactory.getLogger(PetService.class);
	
	private PetRepository petRepository;
	//private CategoryRepository categoryRespository;
	private TagRepository tagRepository;
	private CounterService counterService;

	@Autowired
	public PetService (PetRepository petRepository,
			TagRepository tagRepository, CounterService counterService) {
		this.petRepository = petRepository;
		//this.categoryRespository = categoryRepository;
		this.tagRepository = tagRepository;
		this.counterService = counterService;
	}
	
	public Pet addPet(Pet pet) {
		//List<Category> userListCategory = pet.getCategories();
		List<Category> categoryList = new ArrayList<Category>();
		
		pet.setId(counterService.getNextSequence("pet"));
		
		//Add new category entry when creating a pet
/*		if (null != userListCategory) {
			for (Category category:userListCategory) {
				Category petCategory = new Category();
				
				petCategory.setId(counterService.getNextSequence("category"));
				petCategory.setCategoryName(pet.getCategory());
				categoryRespository.save(petCategory);
				categoryList.add(petCategory);
			}
		}*/
		
		//Add a new tags when creating a Pet
		List<Tag> userListTags = pet.getTags();
		List<Tag> tagList = new ArrayList<Tag>();
		
		if (null != tagList) {
			for (Tag tag: userListTags){
				Tag petTag = new Tag();
				
				petTag.setId(counterService.getNextSequence("tag"));
				petTag.setTagName(tag.getTagName());
				tagRepository.save(petTag);
				tagList.add(petTag);
			}
		}
		
		logger.debug("Creating pet "+pet);
		
		return petRepository.save(pet);
	}
	
	/**
	 * Find pet by ID
	 * 
	 * @param id - ID of pet to be searched
	 * @return
	 */
	public Pet findByID(Integer id) {
		return petRepository.findOne(id);
	}
	
	/**
	 * Delete pet based on ID
	 * 
	 * @param id - ID of the pet to be deleted
	 * @return - TRUE if delete was successful, FALSE if pet not found
	 */
	public boolean delete(Integer id) {
		if (null != petRepository.findOne(id)) {
			logger.debug("deleting pet id "+id);
			petRepository.delete(id);
			return true;
		}
		
		logger.debug("pet with id "+id+" not found");
		return false;
	}
	
	public Iterable<Pet> findAll() {
		return petRepository.findAll();
	}
}
