package com.bugtracker.apps;



import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bugtracker.security.util.ApiResponse;
import com.bugtracker.views.View;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class AppController {
	
	@Autowired
	AppService appService;
	
	@GetMapping("/apps")
	@JsonView(View.App.class)
	public List<App> app() {
		return appService.getAllApps();
	}
	
	@GetMapping("/apps/{page}/{size}/{field}")
	@JsonView(View.App.class)
	public ApiResponse<List<App>> appsSorted(@PathVariable int page, @PathVariable int size, @PathVariable String field) {
		List<App> apps = appService.getAllAppsSorted(page,size, field).getContent();
		long numberOfEl = appService.getAllAppsSorted(page,size, field).getTotalElements();
		return new ApiResponse<>(numberOfEl, apps); 
	}
	
	@PostMapping("/apps")
	@JsonView(View.App.class)
	public ResponseEntity<App> saveApp(@RequestBody App app){
		return new ResponseEntity<App>(appService.addApp(app), HttpStatus.CREATED) ;
	}
		
	@GetMapping("/apps/{id}")
	@JsonView(View.App.class)
	public App getAppById(@PathVariable long id){
		return appService.getAppById(id);
	}
	
	@PutMapping("/apps/{id}")
	@JsonView(View.App.class)
	public ResponseEntity<App> updateApp(@PathVariable long id, @RequestBody App app){
		 return new ResponseEntity<App>(appService.updateAppById(id, app), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/apps/{id}")
	@JsonView(View.App.class)
	public ResponseEntity<Integer> deleteApp(@PathVariable long id){
		appService.deleteAppById(id);
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
		
	@GetMapping("/apps/aps={name}")
	@JsonView(View.App.class)
	public List<App> SearchByName(@PathVariable String name){
		return appService.searchAppByName(name);
	}
	
	@GetMapping("/apps/ap={name}")
	@JsonView(View.App.class)
	public App findByName(@PathVariable String name) {
		return appService.getAppByName(name);
	}

}
