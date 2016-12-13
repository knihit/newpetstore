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

import com.infaspects.pet.repository.CategoryRepository;
import com.infaspects.pet.repository.PetRepository;
import com.infaspects.pet.repository.TagRepository;
import com.infaspects.pet.domain.Category;
import com.infaspects.pet.domain.Tag;
import com.infaspects.pet.domain.Pet;
import com.infaspects.pet.service.CounterService;

@SpringBootApplication
public class PetStoreApplication {

	private static final Logger logger = LoggerFactory.getLogger(PetStoreApplication.class);

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(PetStoreApplication.class, args);

		String[] beanNames = ctx.getBeanDefinitionNames();

		for (String name : beanNames) {
			logger.debug("Loaded Bean:" + name);
		}
	}

	@Bean
	CommandLineRunner runner(PetRepository petRepository, CategoryRepository categoryRepository,
			TagRepository tagRepository, CounterService counterService) {
		return args -> {

			petRepository.deleteAll();
			categoryRepository.deleteAll();
			tagRepository.deleteAll();

			List<Category> listCategory = new ArrayList<Category>();
			Category canineCategory = new Category();
			canineCategory.setId(counterService.getNextSequence("category"));
			canineCategory.setCategoryName("Canine");
			listCategory.add(canineCategory);

			Category repltileCategory = new Category();
			repltileCategory.setId(counterService.getNextSequence("category"));
			repltileCategory.setCategoryName("Reptiles");
			listCategory.add(repltileCategory);

			Category waterCategory = new Category();
			waterCategory.setId(counterService.getNextSequence("category"));
			waterCategory.setCategoryName("Fish");
			listCategory.add(waterCategory);

			categoryRepository.save(canineCategory);
			categoryRepository.save(repltileCategory);
			categoryRepository.save(waterCategory);

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
			dog.setCategories(listCategory);
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
			cat.setCategories(listCategory);
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
			lion.setCategories(listCategory);
			lion.setTags(listTag);

			petRepository.save(dog);
			petRepository.save(cat);
			petRepository.save(lion);
		};
	}
}
