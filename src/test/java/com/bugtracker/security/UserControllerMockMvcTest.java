package com.bugtracker.security;



import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;



@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages = {"com.bugtracker"})
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {UserControllerMockMvcTest.class})
public class UserControllerMockMvcTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserService userService;

	@InjectMocks
	UserController userController;

	@BeforeEach
	public void setup(WebApplicationContext context) {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	@Order(1)
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldReturnListOfUsersThroughHttpIsOK() throws Exception {
		List<User> users = Stream.of(
				new User(1, "walid", "wal", true, null ,"wal@wal.com", "123-123-1234", null, null, null, null, null, null),
				new User(2, "lakh", "lak", true, null ,"lakh@wal.com", "123-000-1234", null, null, null, null, null, null)
				).collect(Collectors.toList());
		when(userService.getAllUsers()).thenReturn(users);

		this.mockMvc.perform(get("/users")).andExpect(status().isOk());
		
	}
	
	@Test
	@Order(2)
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldReturnUserByIdIsOk() throws Exception {
		User user  = new User(2, "walid", "wal", true, null ,"wal@wal.com", "123-123-1234", null, null, null, null, null, null);
		long id = 2;
		when(userService.getUserById(id)).thenReturn(user);
		
		this.mockMvc.perform(get("/users/{id}",id))
					.andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers
							.jsonPath(".user_id").value(2))
					.andExpect(MockMvcResultMatchers
							.jsonPath(".userName").value("walid"))
					.andDo(print());
	}
	
	@Test
	@Order(3)
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldAddUser() throws Exception {
		User user  = new User(2, "walid", "wal", true, null ,"wal@wal.com", "123-123-1234", null, null, null, null, null, null);
		
		when(userService.addUser(user)).thenReturn(user);
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(user);
		
		this.mockMvc.perform(post("/users")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}
	
	@Test
	@Order(4)
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldUpdateUser() throws Exception {
		
		User user  = new User(2, "walid", "wal", true, null ,"wal@wal.com", "123-123-1234", null, null, null, null, null, null);
		long id = 2;
		when(userService.getUserById(id)).thenReturn(user);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(user);
		
		this.mockMvc.perform(put("/users/{id}",id)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	@Test
	@Order(5)
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldDeleteUserById() throws Exception {
		User user  = new User(2, "walid", "wal", true, null ,"wal@wal.com", "123-123-1234", null, null, null, null, null, null);
		
		long id=2;
		when(userService.getUserById(id)).thenReturn(user);
		
		this.mockMvc.perform(delete("/users/{id}",id)).andExpect(status().isOk());
		
		verify(userService , times(1)).deleteUserById(id);
	}

}
