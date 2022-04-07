package com.bugtracker.activities;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(value = OrderAnnotation.class)
@ComponentScan(basePackages = {"com.bugtracker"})
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = {ActivityControllerMockMvcTest.class})
public class ActivityControllerMockMvcTest {
	
	@MockBean
	ActivityService activityService;
	
	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks
	ActivityController activityController;
	
	@BeforeEach
	public void setup(WebApplicationContext context) {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}
	
	@Test
	@Order(1)
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldReturnListOfActivities() throws Exception {
		List<Activity> activities = Stream.of(
				new Activity(1, "bla bla ", null, null, null),
				new Activity(2, "bla bla ", null, null, null),
				new Activity(3, "bla bla ", null, null, null)
				).collect(Collectors.toList());
		
		when(activityService.findAllActivities()).thenReturn(activities);
		
		 mockMvc.perform(get("/activities")).andExpect(status().isOk());			
		
	}
	
	@Test
	@Order(2)
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldAddActivity() throws Exception {
		Activity act = new Activity(1, "bla bla ", null, null, null);
		
		when(activityService.addActivity(act, null)).thenReturn(act);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(act);
		
		mockMvc.perform(post("/activities").content(jsonBody)
											.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	@Order(3)
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldUpdateActivityById() throws Exception {
		Activity act = new Activity(1, "bla bla ", null, null, null);
		long id = 1;
		when(activityService.findActivityById(id)).thenReturn(act);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(act);
		
		mockMvc.perform(put("/activities/{id}", id).content(jsonBody)
											.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	@Order(4)
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldDeleteActivityById() throws Exception {
		Activity act = new Activity(1, "bla bla ", null, null, null);
		long id = 1;
		when(activityService.findActivityById(id)).thenReturn(act);
		
		mockMvc.perform(delete("/activities/{id}", id)).andExpect(status().isOk());
		
		verify(activityService, times(1)).deleteActivity(id);
	}
	
	@Test
	@Order(5)
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldGetActivityById() throws Exception {
		Activity act = new Activity(1, "bla bla ", null, null, null);
		long id = 1;
		when(activityService.findActivityById(id)).thenReturn(act);
		
		mockMvc.perform(get("/activities/{id}", id)).andExpect(status().isOk());
		
	}
	

}
