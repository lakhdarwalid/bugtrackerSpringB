package com.bugtracker.activities;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugtracker.exceptions.ResourceNotFoundException;
import com.bugtracker.security.User;
import com.bugtracker.security.UserService;

@Service
public class ActivityService {
	
	@Autowired
	ActivityRepo activityRepo;
	
	@Autowired
	UserService userService;
	
	public List<Activity> findAllActivities(){
		return activityRepo.findAll();
	}
	
	
	public Activity addActivity(Activity activity, Principal principal){
		User user= userService.getUserByUserName(principal.getName());
	//	activity.setDesciption("Bug with id: " + activity.getBug().getBug_id() +" was created by "+user.getUserName());
		activity.setUser(user);
		return activityRepo.save(activity);
	}
	
	public Activity findActivityById(long id) {
		return activityRepo.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Activity with id "+id+" not available !!"));
	}
	
	public Activity updateActivity(long id, Activity activity) {
		Activity tempActivity = activityRepo.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Activity with id "+id+" not available !!"));
				
		return activityRepo.save(tempActivity);
	}
	
	
	public void deleteActivity(long id) {
		Activity activity = activityRepo.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Activity with id "+id+" not available !!"));
		activityRepo.delete(activity);
	}

}
