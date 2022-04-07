package com.bugtracker.security;


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
public class RoleController {
	
	@Autowired
	RoleService roleService;
	
	
	
	@GetMapping(value = "/roles")
	@JsonView(View.Role.class)
	public List<Role> findAll(){		
		return roleService.getAllRoles();
	}
	
	
	@GetMapping("/roles/{id}")
	@JsonView(View.Role.class)
	public Role findById(@PathVariable long id) {
		return roleService.getRoleById(id);
	}
	
	
	@PostMapping(value = "/roles")
	@JsonView(View.Role.class)
	public ResponseEntity<Role> save(@RequestBody Role role) {
		return new ResponseEntity<Role>(roleService.addRole(role), HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/roles/{id}")
	@JsonView(View.Role.class)
	public ResponseEntity<Role> updateRole(@PathVariable long id, @RequestBody Role role){
		return new ResponseEntity<Role>(roleService.updateRoleById(id, role), HttpStatus.CREATED);
	}
	
	
	@DeleteMapping(value = "/roles/{id}")
	@JsonView(View.Role.class)
	public ResponseEntity<Integer> delete(@PathVariable long id) {
		roleService.deleteRoleById(id);
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/roles/role={name}")
	@JsonView(View.Role.class)
	public ResponseEntity<Role> findByName(@PathVariable String name) {
		return new ResponseEntity<Role>(roleService.getRoleByName(name), HttpStatus.FOUND);
	}
	
}
