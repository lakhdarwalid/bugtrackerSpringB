package com.bugtracker.security;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.bugtracker.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	
	@Autowired
	UserRepo userRepo;
	
		
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	public User getUserById(long id) {
		return userRepo.findById(id).orElseThrow(
				()->new ResourceNotFoundException("can not find user with id : "+id));
	}
	
	public User addUser(User user) throws DataIntegrityViolationException{
		return userRepo.save(user);
	}
	
	public User updateUserById(long id, User user) throws DataIntegrityViolationException{
		User foundUser = userRepo.findById(id).orElseThrow(
					()->new ResourceNotFoundException("can not find user with id : "+id));
		foundUser.setUserName(user.getUserName());
		foundUser.setPassword(user.getPassword());
		foundUser.setActive(user.isActive());
		foundUser.getRole().getUsers().remove(foundUser);
		user.getRole().getUsers().add(foundUser);
		foundUser.setRole(user.getRole());
		foundUser.setEmail(user.getEmail());
		foundUser.setPhone(user.getPhone());
		return userRepo.save(foundUser); 
	}
	
	public void deleteUserById(long id) {
		User foundUser = userRepo.findById(id).orElseThrow(
				()->new ResourceNotFoundException("can not find user with id : "+id));
		userRepo.delete(foundUser);
	}
	
	public User getUserByUserName(String name) {
		return userRepo.findByUserName(name).orElseThrow(
				()->new ResourceNotFoundException("can not find user with name : "+name));
	}
	
	public List<User> getUsersLikeUserName(String name){
		return userRepo.searchByName(name);
	}
	
	public boolean doesUserExist(String userName) {
		 return userRepo.findByUserName(userName).isPresent();
	}
	
	public User matchUser(String userName, String qst1, String qst2, String ans1, String ans2) {
		User user = userRepo.findByUserName(userName).get();
			if (user.getSecretQuestion1().equals(qst1) &&
				user.getSecretQuestion2().equals(qst2) &&
				user.getSecretAnswer1().equals(ans1) &&
				user.getSecretAnswer2().equals(ans2)) {
				return user;
			}
			else throw new ResourceNotFoundException("Wrong Answers !!");
	}
	
	public User updatePassword(long id, String password) {
		User user = userRepo.findById(id).get();
		System.out.println(user.getPassword());
		user.setPassword(password);
		System.out.println(user.getPassword());
		return userRepo.save(user);
	}
	
	
	
}
