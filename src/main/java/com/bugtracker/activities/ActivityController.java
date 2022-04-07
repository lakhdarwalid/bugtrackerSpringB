package com.bugtracker.activities;


import java.security.Principal;
import java.util.List;

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
public class ActivityController {
		
	@Autowired
	ActivityService activityService;
	
	@GetMapping("/activities")
	@JsonView(View.Activity.class)
	public List<Activity> getAllActivities() {
		return activityService.findAllActivities();
	}
	
	
	@PostMapping("/activities")
	@JsonView(View.Activity.class)
	public ResponseEntity<Activity> addActivity(@RequestBody Activity activity, Principal principal){
			Activity tempActivity = activityService.addActivity(activity, principal);
			return new ResponseEntity<Activity>(tempActivity, HttpStatus.CREATED);
	}
	
	@GetMapping("/activities/{id}")	
	@JsonView(View.Activity.class)
	public Activity getActivityById(@PathVariable long id){
		return activityService.findActivityById(id);		
	}
	
	@PutMapping("/activities/{id}")
	@JsonView(View.Activity.class)
	public ResponseEntity<Activity> updateActivity(@PathVariable long id, @RequestBody Activity activity){
		Activity updatedAct = activityService.updateActivity(id, activity);
		return new ResponseEntity<Activity>(updatedAct, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/activities/{id}")
	@JsonView(View.Activity.class)
	public ResponseEntity<Integer> deleteActivity(@PathVariable long id){
		activityService.deleteActivity(id);
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
		
}
