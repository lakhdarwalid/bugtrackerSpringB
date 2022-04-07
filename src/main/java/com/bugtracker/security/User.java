package com.bugtracker.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bugtracker.activities.Activity;
import com.bugtracker.bugs.Bug;
import com.bugtracker.views.View;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class User{
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({View.User.class, View.Role.class, View.Bug.class, View.Activity.class})
	private long user_id;
	@JsonView({View.User.class, View.Role.class, View.Bug.class, View.Activity.class})
	private String userName;
	@JsonView({View.User.class, View.Role.class, View.Bug.class})
	private String password;
	@JsonView({View.User.class, View.Role.class, View.Bug.class})
	private boolean active;
	@JsonView({View.User.class, View.Role.class, View.Bug.class})
	private String email;
	@JsonView({View.User.class, View.Role.class, View.Bug.class})
	private String phone;
	
	@JsonView(View.User.class)
	private String secretQuestion1;
	
	@JsonView(View.User.class)
	private String secretAnswer1;
	
	@JsonView(View.User.class)
	private String secretQuestion2;
	
	@JsonView(View.User.class)
	private String secretAnswer2;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	@JsonView({View.User.class})
	private Role role;
	
	@JsonView(View.User.class)
	@ManyToMany(targetEntity = Bug.class, cascade = CascadeType.MERGE, mappedBy = "users", fetch = FetchType.EAGER)
	private Set<Bug> bugs = new HashSet<>();
	
	@JsonView(View.User.class)
	@OneToMany(targetEntity = Bug.class, cascade = CascadeType.MERGE, mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Bug> bugsForUser = new HashSet<>();
	
	@JsonView(View.User.class)
	@OneToMany(targetEntity = Bug.class, cascade = CascadeType.MERGE, mappedBy = "creator", fetch = FetchType.LAZY)
	private Set<Bug> createdbugsByUser = new HashSet<>();
	
	@JsonView(View.User.class)
	@OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Activity> activities = new HashSet<>();
		  
	public User(long user_id, String userName, String password, boolean active, Role role,
			String secretQuestion1, String secretAnswer1, String secretQuestion2, String secretAnswer2,
			String email, String phone,Set<Bug> bugs, Set<Activity> activities) {
		this.user_id = user_id;
		this.userName = userName;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.active = active;
		this.role = role;
		this.email = email;
		this.phone = phone;
		this.bugs = bugs;
		this.activities = activities;
		this.secretQuestion1 = secretQuestion1;
		this.secretAnswer1 = secretAnswer1;
		this.secretQuestion2 = secretQuestion2;
		this.secretAnswer2 = secretAnswer2;
	} 
	
	public User() { }

	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Role getRole() {
		return role;
	}
	
	
	public void setRole(Role role) {
		this.role = role;
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public Set<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}
	
	public void addActivity(Activity activity) {
		this.activities.add(activity);
	}
	
	public void removeActivity(Activity activity) {
		this.activities.remove(activity);
	}

	public Set<Bug> getBugsForUser() {
		return bugsForUser;
	}

	public void setBugsForUser(Set<Bug> bugsForUser) {
		this.bugsForUser = bugsForUser;
	}
	
	public void addBugToUser(Bug bug) {
		this.bugsForUser.add(bug);
	}
	
	public void removeBugFromUser(Bug bug) {
		this.bugsForUser.remove(bug);
	}

	public Set<Bug> getCreatedbugsByUser() {
		return createdbugsByUser;
	}

	public void setCreatedbugsByUser(Set<Bug> createdbugsByUser) {
		this.createdbugsByUser = createdbugsByUser;
	}

	public String getSecretQuestion1() {
		return secretQuestion1;
	}

	public void setSecretQuestion1(String secretQuestion1) {
		this.secretQuestion1 = secretQuestion1;
	}

	public String getSecretAnswer1() {
		return secretAnswer1;
	}

	public void setSecretAnswer1(String secretAnswer1) {
		this.secretAnswer1 = secretAnswer1;
	}

	public String getSecretQuestion2() {
		return secretQuestion2;
	}

	public void setSecretQuestion2(String secretQuestion2) {
		this.secretQuestion2 = secretQuestion2;
	}

	public String getSecretAnswer2() {
		return secretAnswer2;
	}

	public void setSecretAnswer2(String secretAnswer2) {
		this.secretAnswer2 = secretAnswer2;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", userName=" + userName + ", password=" + password + ", active=" + active
				+ ", email=" + email + ", phone=" + phone + ", role=" + role + ", bugs=" + bugs + ", activities="
				+ activities + "]";
	}
	
}
