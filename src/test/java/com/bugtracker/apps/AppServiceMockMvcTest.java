package com.bugtracker.apps;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;


@ComponentScan(basePackages = "com.bugtracker" )
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = {AppServiceMockMvcTest.class})
public class AppServiceMockMvcTest {
		
	@Autowired
	AppService appService;
	
	@MockBean
	AppRepo appRepo;
	
	@Test
	public void shouldReturnListOfApps() {
		List<App> apps = Stream.of(
				new App(1, "app1", "app number one",  null),
				new App(2, "app2", "app number two",  null)
				).collect(Collectors.toList());
		
		when(appRepo.findAll()).thenReturn(apps);
		
		assertEquals(2, appService.getAllApps().size());
		
	}
}
