package com.bugtracker.security.util;

import com.bugtracker.views.View;
import com.fasterxml.jackson.annotation.JsonView;

public class ApiResponse<T>{
	@JsonView({View.App.class, View.Activity.class, View.Bug.class, View.User.class, View.Role.class})
	private long recordCount;
	@JsonView({View.App.class, View.Activity.class, View.Bug.class, View.User.class, View.Role.class})
	private T response;
	
	
	public ApiResponse() { }

	public ApiResponse(long recordCount, T response) {
		this.recordCount = recordCount;
		this.response = response;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}
	
}
