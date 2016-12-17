package com.infaspects.pet.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.infaspects.pet.PetStoreApplication;
import com.infaspects.pet.domain.Pet;
import com.infaspects.pet.domain.Tag;
import com.infaspects.pet.repository.PetRepository;
import com.infaspects.pet.repository.TagRepository;
import com.infaspects.pet.service.CounterService;

@RunWith(SpringRunner.class)
@SpringApplicationConfiguration(classes = PetStoreApplication.class)
@WebAppConfiguration
public class PetStoreGetDeleteControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    
    private Pet createdPetDog = null;
    
    private Pet createdPetCat = null;
    
    private List<Tag> listTag = null;

    @Autowired
    private PetRepository petRepository;
    
    @Autowired
    private TagRepository tagRepository;
    
    @Autowired 
    private CounterService counterService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        //initialize the repository
        this.petRepository.deleteAll();
        this.tagRepository.deleteAll();

		counterService.deleteSequenceForCollection("tag");
		counterService.deleteSequenceForCollection("pet");
		petRepository.deleteAll();
		tagRepository.deleteAll();

		//create data
		listTag = new ArrayList<Tag>();
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

		//create first pet dog
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
		
		//create second pet cat
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

		//save the pets in the repository
		this.createdPetDog = petRepository.save(dog);
		this.createdPetCat = petRepository.save(cat);

    }
    
	/**
	 * Test case to retrieve all pet information
	 * 
	 * @throws Exception
	 */
    @Test
    public void getPet() throws Exception {
    	mockMvc.perform(get("/pet")
    			.contentType(contentType))
				.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(this.createdPetDog.getId())))
				.andExpect(jsonPath("$[0].petName", is(this.createdPetDog.getPetName())))
				.andExpect(jsonPath("$[0].category", is(this.createdPetDog.getCategory())))
				.andExpect(jsonPath("$[0].status", is(this.createdPetDog.getStatus())))
				.andExpect(jsonPath("$[0].tags[0].tagName", is(this.createdPetDog.getTags().get(0).getTagName())))
				.andExpect(jsonPath("$[0].tags[1].tagName", is(this.createdPetDog.getTags().get(1).getTagName())))
				.andExpect(jsonPath("$[0].photoUrl", is(this.createdPetDog.getPhotoUrl())))
				.andExpect(jsonPath("$[1].id", is(this.createdPetCat.getId())))
				.andExpect(jsonPath("$[1].petName", is(this.createdPetCat.getPetName())))
				.andExpect(jsonPath("$[1].category", is(this.createdPetCat.getCategory())))
				.andExpect(jsonPath("$[1].status", is(this.createdPetCat.getStatus())))
				.andExpect(jsonPath("$[0].tags[0].tagName", is(this.createdPetCat.getTags().get(0).getTagName())))
				.andExpect(jsonPath("$[0].tags[1].tagName", is(this.createdPetCat.getTags().get(1).getTagName())))
				.andExpect(jsonPath("$[1].photoUrl", is(this.createdPetCat.getPhotoUrl())));
    }
    
    /**
     * Test case to retrieve pet details based on path variable {id}
     * @throws Exception
     */
    @Test
    public void getPetDetails() throws Exception {
    	mockMvc.perform(get("/pet/"+this.createdPetDog.getId())
    			.contentType(contentType))
    			.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(this.createdPetDog.getId())))
				.andExpect(jsonPath("$.petName", is(this.createdPetDog.getPetName())))
				.andExpect(jsonPath("$.category", is(this.createdPetDog.getCategory())))
				.andExpect(jsonPath("$.status", is(this.createdPetDog.getStatus())))
				.andExpect(jsonPath("$.tags[0].tagName", is(this.createdPetDog.getTags().get(0).getTagName())))
				.andExpect(jsonPath("$.tags[1].tagName", is(this.createdPetDog.getTags().get(1).getTagName())))				
				.andExpect(jsonPath("$.photoUrl", is(this.createdPetDog.getPhotoUrl())));
    	
    	mockMvc.perform(get("/pet/"+this.createdPetCat.getId())
    			.contentType(contentType))
    			.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(this.createdPetCat.getId())))
				.andExpect(jsonPath("$.petName", is(this.createdPetCat.getPetName())))
				.andExpect(jsonPath("$.category", is(this.createdPetCat.getCategory())))
				.andExpect(jsonPath("$.status", is(this.createdPetCat.getStatus())))
				.andExpect(jsonPath("$.tags[0].tagName", is(this.createdPetCat.getTags().get(0).getTagName())))
				.andExpect(jsonPath("$.tags[1].tagName", is(this.createdPetCat.getTags().get(1).getTagName())))
				.andExpect(jsonPath("$.photoUrl", is(this.createdPetCat.getPhotoUrl())));
    }
    
    /**
     * Test case to delete pet based on pet {id}
     * 
     * @throws Exception
     */
    @Test
    public void deletePet() throws Exception {
    	mockMvc.perform(delete("/pet/"+this.createdPetCat.getId())
    			.contentType(contentType))
    			.andExpect(status().isOk());
    	
    	mockMvc.perform(get("/pet/"+this.createdPetCat.getId())
    			.contentType(contentType))
    			.andExpect(status().isNotFound());
    }
    
    @After
    public void tearDown() throws Exception {
        //delete all items in the repository
		counterService.deleteSequenceForCollection("tag");
		counterService.deleteSequenceForCollection("pet");
		petRepository.deleteAll();
		tagRepository.deleteAll();
    }
}
