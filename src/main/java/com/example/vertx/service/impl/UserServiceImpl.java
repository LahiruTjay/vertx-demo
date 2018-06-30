package com.example.vertx.service.impl;

import java.util.Arrays;
import java.util.List;

import com.example.vertx.dto.CommonHttpResponse;
import com.example.vertx.models.SystemUser;
import com.example.vertx.service.UserService;

public class UserServiceImpl implements UserService{

	private static List<SystemUser> userList = Arrays.asList(new SystemUser(1, "Lahiru", "lahiru@vertx.com"),
			new SystemUser(2, "Tony", "tony@vertx.com"));

	public CommonHttpResponse getAllUsers() {
		return new CommonHttpResponse("SUCCESS", "", userList);
	}

	public CommonHttpResponse getUserById(long id) {
		SystemUser systemUser = userList.parallelStream().filter(user -> user.getId() == id).findFirst().orElse(null);
		return new CommonHttpResponse("SUCCESS", "", systemUser);
	}

}
