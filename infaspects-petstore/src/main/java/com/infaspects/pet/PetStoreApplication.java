package com.infaspects.pet;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.infaspects.pet.repository.PetRepository;
import com.infaspects.pet.repository.TagRepository;
import com.infaspects.pet.domain.Tag;
import com.infaspects.pet.domain.Pet;
import com.infaspects.pet.service.CounterService;

@SpringBootApplication
public class PetStoreApplication {

	private static final Logger logger = LoggerFactory.getLogger(PetStoreApplication.class);

	public static void main(String[] args) {
		logger.debug("Starting spring boot");
		ApplicationContext ctx = SpringApplication.run(PetStoreApplication.class, args);

		String[] beanNames = ctx.getBeanDefinitionNames();

		for (String name : beanNames) {
			logger.debug("Loaded Bean:" + name);
		}
	}

	@Bean
	CommandLineRunner runner(PetRepository petRepository,
			TagRepository tagRepository, CounterService counterService) {
		return args -> {
			counterService.deleteSequenceForCollection("tag");
			counterService.deleteSequenceForCollection("pet");
			petRepository.deleteAll();
			tagRepository.deleteAll();

			List<Tag> listTag = new ArrayList<Tag>();
			Tag cityTag = new Tag();
			cityTag.setId(counterService.getNextSequence("tag"));
			cityTag.setTagName("Toronto");
			listTag.add(cityTag);

			Tag stateTag = new Tag();
			stateTag.setId(counterService.getNextSequence("tag"));
			stateTag.setTagName("Ontario");
			listTag.add(stateTag);

			tagRepository.save(cityTag);
			tagRepository.save(stateTag);

			List<String> dogPhotos = new ArrayList<String>();
			dogPhotos.add("dog");
			dogPhotos.add("dog_1");
			dogPhotos.add("dog_2");

			Pet dog = new Pet();
			dog.setId(counterService.getNextSequence("pet"));
			dog.setPetName("Buster");
			dog.setPhotoUrl(dogPhotos);
			dog.setStatus("Sold");
			dog.setCategory("Canine");
			dog.setTags(listTag);

			List<String> catPhotos = new ArrayList<String>();
			catPhotos.add("cat");
			catPhotos.add("cat_1");
			catPhotos.add("cat_2");
			catPhotos.add("cat_3");

			Pet cat = new Pet();
			cat.setId(counterService.getNextSequence("pet"));
			cat.setPetName("Purrfect");
			cat.setPhotoUrl(catPhotos);
			cat.setStatus("Available");
			cat.setCategory("Feline");
			cat.setTags(listTag);

			List<String> lionPhotos = new ArrayList<String>();
			lionPhotos.add("lion");
			lionPhotos.add("lion_1");
			lionPhotos.add("lion_2");
			lionPhotos.add("lion_3");

			Pet lion = new Pet();
			lion.setId(counterService.getNextSequence("pet"));
			lion.setPetName("King!");
			lion.setPhotoUrl(lionPhotos);
			lion.setStatus("Pending");
			lion.setCategory("Feline");
			lion.setTags(listTag);

			petRepository.save(dog);
			petRepository.save(cat);
			petRepository.save(lion);
		};
	}
}
