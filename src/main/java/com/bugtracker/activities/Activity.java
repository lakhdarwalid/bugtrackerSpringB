package com.bugtracker.activities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.bugtracker.bugs.Bug;
import com.bugtracker.security.User;
import com.bugtracker.views.View;
import com.fasterxml.jackson.annotation.JsonView;


@Entity
public class Activity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({View.Activity.class, View.Bug.class, View.User.class})
	private long act_id;
	@Lob
	@JsonView({View.Activity.class, View.Bug.class, View.User.class})
	private String description;
	@JsonView({View.Activity.class, View.Bug.class, View.User.class})
	private Date activityDate;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bug_id")
	@JsonView(View.Activity.class)
	private Bug bug;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonView({View.Activity.class, View.Bug.class})
	private User user;
	
	public Activity(long act_id, String description, Date activityDate, Bug bug, User user) {
		this.act_id = act_id;
		this.description = description;
		this.activityDate = activityDate;
		this.bug = bug;
		this.user = user;
	}

	public Activity() {  }

	public long getAct_id() {
		return act_id;
	}

	public void setAct_id(long act_id) {
		this.act_id = act_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		
		this.activityDate = activityDate;
	}
	
	
	public Bug getBug() {
		return bug;
	}

	public void setBug(Bug bug) {
		this.bug = bug;
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
