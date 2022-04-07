package com.bugtracker.bugs;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugtracker.activities.Activity;
import com.bugtracker.activities.ActivityRepo;
import com.bugtracker.exceptions.ResourceNotFoundException;
import com.bugtracker.security.User;
import com.bugtracker.security.UserService;

@Service
public class BugService {

	@Autowired
	BugRepo bugRepo;
	
	@Autowired
	ActivityRepo activityRepo;
	
	@Autowired
	UserService userService;
	
	
	public List<Bug> getAllBugs(){
		return bugRepo.findAll();
	}
	
	public Bug getBugById(long id) {
		return bugRepo.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Can not find Bug with id :"+id));
	}
	
	public Bug addBug(Bug bug, Principal principal) {
		User user = userService.getUserByUserName(principal.getName());
		bug.addUser(user);
		bug.setUser(user);
		bug.setCreator(user);
		bug.setStatus(Status.STAGING);
		bug.setProgress(0);
		Bug savedBug = bugRepo.save(bug);
		Activity activity = new Activity();
		activity.setActivityDate(savedBug.getCreatedDate());
		activity.setBug(savedBug);
		activity.setUser(user);
		activity.setDescription("Bug #"+savedBug.getBug_id()+" was created on "+savedBug.getCreatedDate());
		user.addActivity(activity);
		activityRepo.save(activity);
		return savedBug;
	}
	
	public Bug updateBug(long id, Bug bug) {
		Bug tempBug = bugRepo.findById(id)
							 .orElseThrow(()->new ResourceNotFoundException("Bug not found"));
		User user = bug.getUser();
		tempBug.setUser(user);
		Set<User> users = tempBug.getUsers();
		if (!users.contains(user))users.add(user);
		tempBug.getUsers().clear();
		tempBug.setUsers(users);
		tempBug.setPickedDate(bug.getPickedDate());
		tempBug.setDoneDate(bug.getDoneDate());
		tempBug.setStatus(bug.getStatus());
		Bug savedBug = bugRepo.save(tempBug);
		Activity activity = new Activity();
		activity.setBug(savedBug);
		activity.setUser(user);
		if (savedBug.getStatus()== Status.PROCESSING ) {
		activity.setDescription("Bug #"+savedBug.getBug_id()+" was assigned to : "+user.getUserName()+" on "+savedBug.getPickedDate());
		activity.setActivityDate(savedBug.getPickedDate());
		}
		if (savedBug.getStatus()== Status.DONE ) {
			activity.setDescription("Bug #"+savedBug.getBug_id()+" Done By : "+user.getUserName()+" on "+savedBug.getDoneDate());
			activity.setActivityDate(savedBug.getDoneDate());
		}
		activityRepo.save(activity);
		return savedBug;
	}
	
	public void deleteBugById(long id) {
		Bug bug = bugRepo.findById(id)
				 .orElseThrow(()->new ResourceNotFoundException("Bug not found"));
		bugRepo.delete(bug);
	}
	
	public List<Bug> searchBugByTitle(String name){
		return bugRepo.SearchBugByTitle(name);
	}
	
	public Set<Bug> getBugsByUser(Principal principal){
		User user = userService.getUserByUserName(principal.getName());
		Set<Bug> bugs = user.getCreatedbugsByUser();
		return bugs;
	}
}
