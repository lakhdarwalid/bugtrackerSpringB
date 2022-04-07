package com.bugtracker.bugs;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
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

@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages = "com.bugtracker" )
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = {TestBugController.class})
public class TestBugController {
		
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	BugService bugService;
	
	@InjectMocks
	BugController bugController;
	
	@BeforeEach
	public void setup(WebApplicationContext context) {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	
	@Test
	@Order(1)
	@WithMockUser(username = "aaa",password = "aaa", authorities = {"user"} )
	public void ShouldAddNewBug() throws Exception {
		Bug bug = new Bug(1, "bug1", "bug",Status.STAGING, null, null, null, null, null, null, null, null, 0);
		Principal principal = null;		
	    when(bugService.addBug(bug, principal)).thenReturn(bug);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(bug);
		
		this.mockMvc.perform(post("/bugs")
							.content(jsonBody)
							.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}
	
	@Test
	@Order(2)
	@WithMockUser(username = "aaa",password = "aaa", authorities = {"user"} )
	public void shouldReturnListOfBugs() throws Exception {
		List<Bug> bugs = Stream.of(
				new Bug(1, "bug1", "bug",Status.STAGING, null, null, null, null, null, null, null, null, 0),
				new Bug(2, "bug2", "bug2",Status.STAGING, null, null, null, null, null, null, null, null, 0),
				new Bug(3, "bug3", "bug3",Status.STAGING, null, null, null, null, null, null, null, null, 0)
				).collect(Collectors.toList());
		
		when(bugService.getAllBugs()).thenReturn(bugs);
		
		mockMvc.perform(get("/bugs")).andExpect(status().isOk())
									 .andDo(print());
	}
	
	@Test
	@Order(3)
	@WithMockUser(username = "aaa",password = "aaa", authorities = {"user"} )
	public void shouldReturnBugById() throws Exception {
		Bug bug = new Bug(1, "bug1", "bug",Status.STAGING, null, null, null, null, null, null, null, null, 0);
		long id = 1;
		
		when(bugService.getBugById(id)).thenReturn(bug);
		
		mockMvc.perform(get("/bugs/{id}", id)).andExpect(status().isOk())
										 .andDo(print());
	}
	
	@Test
	@Order(4)
	@WithMockUser(username = "aaa",password = "aaa", authorities = {"user"} )
	public void shouldUpdateBugById() throws Exception {
	Bug bug = new Bug(1, "bug1", "bug",Status.STAGING, null, null, null, null, null, null, null, null, 0);
	long id = 1;
	when(bugService.getBugById(id)).thenReturn(bug);
	
	ObjectMapper mapper = new ObjectMapper();
	String jsonValue = mapper.writeValueAsString(bug);
	
	mockMvc.perform(put("/bugs/{id}", id).content(jsonValue)
										 .contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}
	
	
	@Test
	@Order(5)
	@WithMockUser(username = "aaa",password = "aaa", authorities = {"user"} )
	public void shouldDeleteBugById() throws Exception {
		Bug bug = new Bug(1, "bug1", "bug",Status.STAGING, null, null, null, null, null, null, null, null, 0);
		long id = 1;
		when(bugService.getBugById(id)).thenReturn(bug);
	
		mockMvc.perform(delete("/bugs/{id}", id)).andExpect(status().isOk());
		
		verify(bugService, times(1)).deleteBugById(id)	;								
	}
}
