package com.example.vertx.service;

import java.util.Arrays;
import java.util.List;

import com.example.vertx.models.SystemUser;

public class UserService {

	private static List<SystemUser> userList = Arrays.asList(new SystemUser(1, "Lahiru", "lahiru@vertx.com"),
			new SystemUser(1, "Tony", "tony@vertx.com"));

	public static SystemUser getUserById(long id) {
		return userList.parallelStream().filter(user -> user.getId() == id).findFirst().get();
	}

	public static List<SystemUser> getAllUsers() {
		return userList;
	}

}
