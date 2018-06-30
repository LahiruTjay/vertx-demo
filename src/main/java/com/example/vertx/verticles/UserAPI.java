package com.example.vertx.verticles;

import com.example.vertx.service.UserService;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class UserAPI {

	public static void getAllUsers(RoutingContext routingContext) {
		routingContext.response().putHeader("contetent-type", "application/json")
				.end(Json.encodePrettily(UserService.getAllUsers()));
	}

	public static void getUserById(RoutingContext routingContext) {
		long id = Long.parseLong(routingContext.request().getParam("id"));
		routingContext.response().putHeader("contetent-type", "application/json")
				.end(Json.encodePrettily(UserService.getUserById(id)));
	}
}
