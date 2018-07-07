package com.example.vertx.service;

import io.vertx.ext.web.RoutingContext;

public interface UserService {

	void getUserById(RoutingContext routingContext);

}
