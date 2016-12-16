package com.infaspects.pet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.infaspects.pet.domain.Pet;
import com.infaspects.pet.service.PetService;

@RestController
@RequestMapping("/pet")
public class PetController {
	
	private static final Logger logger = LoggerFactory.getLogger(PetController.class);
	
	private PetService petService;
	
	@Autowired
	public PetController(PetService petService) {
		this.petService = petService;
	}

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Iterable<Pet>> findAll() {
		logger.debug("Retrieving all pets in pet store");
		return new ResponseEntity<Iterable<Pet>>(petService.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> findByID(@PathVariable(value="id") Integer id) {
		logger.debug("retrieving pet with id "+id);
		Pet pet = petService.findByID(id);
		if (null != pet) {
			return new ResponseEntity<Pet>(pet, HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Pet> create(@RequestBody Pet pet) {
		logger.debug("Adding a new pet");
		return new ResponseEntity<Pet>(petService.addPet(pet), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable(value="id") Integer id) {
		logger.debug("deleting a pet with id ", id);
		if (petService.delete(id)) {
			return new ResponseEntity(HttpStatus.OK);
		}
		
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
}
