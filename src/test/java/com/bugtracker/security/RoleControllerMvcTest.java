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
@SpringBootTest(classes = {RoleControllerMvcTest.class})
public class RoleControllerMvcTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	RoleService roleServive;
	
	@InjectMocks
	RoleController roleController;
	
	@BeforeEach
	public void setup(WebApplicationContext context) {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Order(1)
	@Test
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldReturnListOfRoles() throws Exception {
		List<Role> roles = Stream.of(
				new Role(1,"admin", null),
				new Role(2,"user", null)
		).collect(Collectors.toList());
		
		when(roleServive.getAllRoles()).thenReturn(roles);
		
		this.mockMvc.perform(get("/roles"))
					.andExpect(status().isOk())
					.andDo(print());
	}
	
	
	@Test
	@Order(2)
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldReturnRoleById() throws Exception {
		Role role = new Role(1,"admin", null);
		long id = 1;
		when(roleServive.getRoleById(id)).thenReturn(role);
		
		this.mockMvc.perform(get("/roles/{id}",id))
					.andExpect(status().isOk())
					.andExpect(MockMvcResultMatchers
							.jsonPath(".role_id").value(1))
					.andExpect(MockMvcResultMatchers
							.jsonPath(".role").value("admin"));
	} 
	
	
	@Test
	@Order(3)
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldAddNewRole() throws Exception {
		Role role = new Role(1, "admin", null);
		
		when(roleServive.addRole(role)).thenReturn(role);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(role);
		
		this.mockMvc.perform(post("/roles")
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}
	
	
	@Test
	@Order(4)
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldUpdateRoleById() throws Exception {
		Role role = new Role(1, "admin", null);
		long id=1;
		
		when(roleServive.getRoleById(id)).thenReturn(role);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(role);
		
		this.mockMvc.perform(put("/roles/{id}",id)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isCreated());
	}
	
	
	
	@Test
	@Order(5)
	@WithMockUser(username = "aaa", password = "aaa", authorities = {"admin"})
	public void shouldDeleteRoleById() throws Exception {
		Role role = new Role(1, "admin", null);
		long id = 1;
		
		when(roleServive.getRoleById(id)).thenReturn(role);
		
		this.mockMvc.perform(delete("/roles/{id}",id))
					.andExpect(status().isOk());
		
		verify(roleServive, times(1)).deleteRoleById(id);
	}

}
