package com.bugtracker.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.bugtracker.exceptions.ResourceNotFoundException;

@Service
public class RoleService {

	@Autowired
	RoleRepo roleRepo;
	
	
	public List<Role> getAllRoles(){
		return roleRepo.findAll();
	}
	
	public Role getRoleById(long id) {
		return roleRepo.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Can not find Role with id :"+id));
	}
	
	public Role addRole(Role role) throws DataIntegrityViolationException{
		return roleRepo.save(role);
	}
	
	public Role updateRoleById(long id, Role role) throws DataIntegrityViolationException {
		Role foundRole = roleRepo.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Can not find Role with id :"+id));
		foundRole.setRole(role.getRole());
		return roleRepo.save(foundRole);
	}
	
	public void deleteRoleById(long id) {
		Role role = roleRepo.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Can not find Role with id :"+id));
		roleRepo.delete(role);
	}
	
	public Role getRoleByName(String name) {
		Role role = roleRepo.findByName(name);
		if (role!=null) return role;
		else throw new ResourceNotFoundException("Can not find Role with name :"+name);
	}
}
