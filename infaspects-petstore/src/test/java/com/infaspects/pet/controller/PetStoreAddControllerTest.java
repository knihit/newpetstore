package com.infaspects.pet.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
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
public class PetStoreAddControllerTest {
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
    
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
	
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        //initialize the repository
        this.petRepository.deleteAll();
        this.tagRepository.deleteAll();
        
		counterService.deleteSequenceForCollection("tag");
		counterService.deleteSequenceForCollection("pet");
		
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

    }

	
    /**
     * Test case to add a pet
     * 
     * @throws Exception
     */
    @Test 
    public void addPet() throws Exception {
		List<String> lionPhotos = new ArrayList<String>();
		lionPhotos.add("Lion");
		lionPhotos.add("Lion_1");
		lionPhotos.add("Lion_2");
		lionPhotos.add("Lion_3");

		Pet lionPet = new Pet();
		lionPet.setId(counterService.getNextSequence("pet"));
		lionPet.setPetName("King!");
		lionPet.setPhotoUrl(lionPhotos);
		lionPet.setStatus("Pending");
		lionPet.setCategory("Feline");
		lionPet.setTags(listTag);
		
    	mockMvc.perform(post("/pet")
				.contentType(contentType)
				.content(json(lionPet)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.petName", is(lionPet.getPetName())))
				.andExpect(jsonPath("$.category", is(lionPet.getCategory())))
				.andExpect(jsonPath("$.status", is(lionPet.getStatus())))
				.andExpect(jsonPath("$.tags[0].tagName", is(lionPet.getTags().get(0).getTagName())))
				.andExpect(jsonPath("$.tags[1].tagName", is(lionPet.getTags().get(1).getTagName())))
				.andExpect(jsonPath("$.photoUrl", is(lionPet.getPhotoUrl())));
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
