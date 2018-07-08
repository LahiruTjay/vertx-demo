package com.example.vertx.service;

import io.vertx.ext.web.RoutingContext;

public interface UserService {

	void saveUser(RoutingContext routingContext);
	
	void updateUser(RoutingContext routingContext);

	void getUserById(RoutingContext routingContext);

	void gerAllUsers(RoutingContext routingContext);
	
	void deleteUserById(RoutingContext routingContext);
	
	void deleteAllUsers(RoutingContext routingContext);

}
