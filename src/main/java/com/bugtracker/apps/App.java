package com.bugtracker.apps;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.bugtracker.bugs.Bug;
import com.bugtracker.views.View;
import com.fasterxml.jackson.annotation.JsonView;

@Entity

public class App {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@JsonView({View.App.class, View.Bug.class})
	private long app_id;
	@JsonView({View.App.class, View.Bug.class})
	private String name;
	@Lob
	@JsonView({View.App.class, View.Bug.class})
	private String description;
	 
	@OneToMany(targetEntity = Bug.class, mappedBy = "app", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonView(View.App.class)
	private Set<Bug> bugs = new HashSet<>();

	public App(long app_id, String name, String description, Set<Bug> bugs) { 
		this.app_id = app_id;
		this.name = name;
		this.description = description;
		this.bugs = bugs; 
	}
 
	public App() {  }


	public long getApp_id() {
		return app_id;
	}

	public void setApp_id(long app_id) {
		this.app_id = app_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Bug> getBugs() {
		return bugs;
	}

	public void setBugs(Set<Bug> bugs) {
		this.bugs = bugs;
	}
	
	public void addBug(Bug bug) {
		this.bugs.add(bug);
	}
	
	public void removeBug(Bug bug) {
		this.bugs.remove(bug);
	}

 

}
