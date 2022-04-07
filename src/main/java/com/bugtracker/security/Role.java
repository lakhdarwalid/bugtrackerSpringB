package com.bugtracker.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.bugtracker.views.View;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Role{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({View.User.class, View.Role.class})
	private long role_id;
	@JsonView({View.User.class, View.Role.class})
	private String role;
	@OneToMany(mappedBy = "role", cascade = CascadeType.MERGE, fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonView(View.Role.class)
	private Set<User> users = new HashSet<User>();
		
	public Role() {   }

	public Role(long role_id, String role, Set<User> users) {
		this.role_id = role_id;
		this.role = role;
		this.users = users; 
	}

	public long getRole_id() {
		return role_id;
	}

	public void setRole_Id(long role_id) {
		this.role_id = role_id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<User> getUsers() {
		return users;
	}
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
	
	public void removeUser(User user) {
		this.users.remove(user);
	}
	
	

	@Override
	public String toString() {
		return "Role [role_id=" + role_id + ", role=" + role + "]";
	}

	
	
	
	
}
