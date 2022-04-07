package com.bugtracker.bugs;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.views.View;
import com.fasterxml.jackson.annotation.JsonView;


@RestController
public class BugController {
	@Autowired
	BugService bugService;
	
	@GetMapping("/bugs")
	@JsonView(View.Bug.class)
	public List<Bug> getAllBugs(){
		return bugService.getAllBugs();
	}
	
	@GetMapping("/bugs/{id}")
	@JsonView(View.Bug.class)
	public Bug getBugById(@PathVariable long id){
			return bugService.getBugById(id);
	}
	
	@GetMapping("/bugs/title={title}")
	@JsonView(View.Bug.class)
	public List<Bug> searchByTitle(@PathVariable String title){
		return bugService.searchBugByTitle(title);
	}
	
	@PostMapping("/bugs")
	@JsonView(View.Bug.class)
	public ResponseEntity<Bug> addBug(@RequestBody Bug bug, Principal principal){
			return new ResponseEntity<Bug>(bugService.addBug(bug, principal) , HttpStatus.CREATED);
	}
	
	@PutMapping("/bugs/{id}")
	@JsonView(View.Bug.class)
	public ResponseEntity<Bug> updateBug(@PathVariable long id, @RequestBody Bug bug){
			return new ResponseEntity<Bug>(bugService.updateBug(id, bug), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/bugs/{id}")
	@JsonView(View.Bug.class)
	public ResponseEntity<Integer> deleteBug(@PathVariable long id){
		bugService.deleteBugById(id);
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@GetMapping("/bugs/bugsbyUser")
	@JsonView(View.Bug.class)
	public Set<Bug> getBugsByUser(Principal principal){
		return bugService.getBugsByUser(principal);
	}

}
