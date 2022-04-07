package com.bugtracker.bugs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bugtracker.security.User;
@SpringBootTest(classes = {TestBugService.class})
public class TestBugService {
			
	@MockBean
	BugRepo bugRepo;
	
	@InjectMocks
	BugService bugService;
	
	List<Bug> bugs;
	Bug bug;
	
	@Test
	public void shouldaddBug() {
		Principal mockPrincipal = Mockito.mock(Principal.class);
	    Mockito.when(mockPrincipal.getName()).thenReturn("me");
		User user = new User(1, "walid", "walid", true, null, null, null,null, null, null, null, null, null);
		
		Bug bug = new Bug(1, "bug 1", "bug", Status.STAGING, null, null, null, null,null, null, null, null,0);
		
		//when(bugRepo.save(bug)).thenReturn(bug);
		
	//	Bug savedBug = bugService.addBug(bug, mockPrincipal);
		
	//	assertEquals(savedBug, bug);
		
	}
}
