package com.bugtracker.apps;


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
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {AppControllerMockMvcTest.class})
public class AppControllerMockMvcTest {
		
		@Autowired
		private MockMvc mockMvc;
		
		@MockBean
		AppService appService;
			
		@InjectMocks
		private AppController appController;
				
		
		@BeforeEach
		public void setup(WebApplicationContext context) {
			mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();							 
			
		}
		
		
		@Test
		@Order(1)
		@WithMockUser(username = "aaa",password = "aaa", authorities = {"user"} )
		public void shouldReturnListOfApps() throws Exception {
			
			List<App> apps = Stream.of(
					new App(1, "app1", "app number one",  null),
					new App(2, "app2", "app number two",  null)
					).collect(Collectors.toList());
			
			when(appService.getAllApps()).thenReturn(apps);
			
			mockMvc.perform(get("/apps")).andExpect(status().isOk())
						.andDo(print());
		}
		
		@Test
		@Order(2)
		@WithMockUser(username = "aaa",password = "aaa", authorities = {"admin"} )
		public void shouldReturnAppById() throws Exception {
			App app = new App(1, "app1", "app number one", null);
			long id =1;
			
			when(appService.getAppById(id)).thenReturn(app);
			
			mockMvc.perform(get("/apps/{id}",id))
						.andExpect(status().isOk())
						.andDo(print());
		}
		
		
		@Test
		@Order(3)
		@WithMockUser(username = "aaa",password = "aaa", authorities = {"user"} )
		public void ShouldAddNewApp() throws Exception {
			App app = new App(1, "app1", "app number one", null);
			
			when(appService.addApp(app)).thenReturn(app);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonBody = mapper.writeValueAsString(app);
			
			this.mockMvc.perform(post("/apps")
								.content(jsonBody)
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		}
		
		@Test
		@Order(4)
		@WithMockUser(username = "aaa",password = "aaa", authorities = {"user"} )
		public void shouldUpdateAppById() throws Exception {
			App app = new App(1, "app1", "app number one", null);
			long id = 1;
			
			when(appService.getAppById(id)).thenReturn(app);
			ObjectMapper mapper = new ObjectMapper();
			String jsonBody = mapper.writeValueAsString(app);
			
			this.mockMvc.perform(put("/apps/{id}",id)
								.content(jsonBody)
								.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
			
		}
		
		@Test
		@Order(5)
		@WithMockUser(username = "aaa",password = "aaa", authorities = {"user"} )
		public void shouldDeleteAppById() throws Exception {
			App app = new App(1, "app1", "app number one", null);
			long id = 1;
			
			when(appService.getAppById(id)).thenReturn(app);
			
			this.mockMvc.perform(delete("/apps/{id}",id)).andExpect(status().isOk());
			verify(appService, times(1)).deleteAppById(id);
		}
		
		
		
}
