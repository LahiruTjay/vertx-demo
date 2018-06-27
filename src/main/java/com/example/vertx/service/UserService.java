package com.example.vertx.service;

import java.util.Arrays;
import java.util.List;

import com.example.vertx.models.SystemUser;

public class UserService {

	private List<SystemUser> userList = Arrays.asList(new SystemUser(1, "Lahiru", "lahiru@vertx.com"),
			new SystemUser(1, "Tony", "tony@vertx.com"));

	public List<SystemUser> getAllUsers() {
		return userList;
	}

}
