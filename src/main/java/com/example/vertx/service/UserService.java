package com.example.vertx.service;

import io.vertx.ext.web.RoutingContext;

public interface UserService {

	void saveUser(RoutingContext routingContext);

	void getUserById(RoutingContext routingContext);

	void gerAllUsers(RoutingContext routingContext);

}
