package com.bugtracker.apps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bugtracker.exceptions.ResourceNotFoundException;

@Service
public class AppService {

	@Autowired
	AppRepo appRepo;
	
	
	public List<App> getAllApps(){
		return appRepo.findAll();
	}
	
	
	public Page<App> getAllAppsSorted(int page, int size, String field){
		return appRepo.findAll(PageRequest.of(page, size, Sort.by(field)));
	}
	
	public App getAppById(long id) {
		return appRepo.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Can not find App with id :"+id));
	}
	
	public List<App> searchAppByName(String App){
		return appRepo.SearchByName(App);
	}
	
	public App getAppByName(String name) {
		return appRepo.findByName(name);
	}
	
	public App addApp(App app) throws DataIntegrityViolationException{
		return appRepo.save(app);
	}
	
	public App updateAppById(long id, App app) {
		App foundApp = appRepo.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Can not find App with id :"+id));
		foundApp.setName(app.getName());
		foundApp.setDescription(app.getDescription());
	/*	if (app.getBugs().size()>0) {
				foundApp.getBugs().clear();
				foundApp.setBugs(app.getBugs());
		}*/
		return appRepo.save(foundApp);
	}
	
	public void deleteAppById(long id) {
		App app = appRepo.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Can not find App with id :"+id));
		appRepo.delete(app);
	}
}
