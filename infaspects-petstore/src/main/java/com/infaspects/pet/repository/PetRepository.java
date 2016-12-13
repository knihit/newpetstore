package com.infaspects.pet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.infaspects.pet.domain.Pet;

@Repository
public interface PetRepository extends MongoRepository<Pet, Integer>{

}
