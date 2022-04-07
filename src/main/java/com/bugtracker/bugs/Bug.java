package com.bugtracker.bugs;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.bugtracker.activities.Activity;
import com.bugtracker.apps.App;
import com.bugtracker.security.User;
import com.bugtracker.views.View;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Bug {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({View.Bug.class, View.User.class, View.App.class})
	private long bug_id;
	@JsonView({View.Bug.class, View.User.class, View.App.class})
	private String title;
	@Lob
	@JsonView({View.Bug.class, View.User.class, View.App.class})
	private String description;
	@JsonView({View.Bug.class, View.User.class, View.App.class})
	private Status status;
	@JsonView({View.Bug.class, View.User.class, View.App.class})
	private Date createdDate;
	@JsonView({View.Bug.class, View.User.class, View.App.class})
	private Date pickedDate;
	@JsonView({View.Bug.class, View.User.class, View.App.class})
	private Date doneDate;
	@JsonView({View.Bug.class, View.User.class, View.App.class})
	private int progress;

	@ManyToOne
	@JoinColumn(name ="app_id") 
	@JsonView(View.Bug.class)
	private App app; 
	   
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(
			name = "bugs_users",
			joinColumns = @JoinColumn(name = "bug_id"), 
			inverseJoinColumns = @JoinColumn(name = "user_id")
			)  
	@JsonView(View.Bug.class)
	private Set<User> users = new HashSet<>();
	 
	@OneToMany(targetEntity = Activity.class, mappedBy = "bug", cascade = CascadeType.MERGE, orphanRemoval = true ,fetch = FetchType.LAZY)
	@JsonView({View.Bug.class, View.User.class, View.App.class})
	private Set<Activity> activities = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	@JsonView({View.Bug.class, View.App.class})
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "creator", referencedColumnName ="user_id" )
	@JsonView({View.Bug.class, View.App.class})
	private User creator;
		
	public Bug() {  }

	public Bug(long bug_id, String title, String description, Status status,  Date createdDate, Date pickedDate,
			Date doneDate, App app, Set<User> users, Set<Activity> activities, User creator, User user, int progress) {

		this.bug_id = bug_id;
		this.title = title;
		this.description = description;
		this.createdDate = createdDate;
		this.pickedDate = pickedDate;
		this.doneDate = doneDate;
		this.app = app;
		this.creator = creator;
		this.users = users;
		this.activities = activities;
		this.status = status;
		this.user = user;
		this.progress = progress;
	}


	public long getBug_id() {
		return bug_id;
	}

	public void setBug_id(long bug_id) {
		this.bug_id = bug_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getPickedDate() {
		return pickedDate;
	}

	public void setPickedDate(Date pickedDate) {
		this.pickedDate = pickedDate;
	}

	public Date getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}
	
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users=users;
	}
	
	
	public Set<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Set<Activity> activities) {
		this.activities=activities;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
	
	public void removeUser(User user) {
		this.users.remove(user);
	}
	
	public void addActivity(Activity activity) {
		this.activities.add(activity);
	}
	
	public void removeActivity(Activity activity) {
		this.activities.remove(activity);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	
	
	
	
}


enum Status{STAGING, PROCESSING, DONE};
