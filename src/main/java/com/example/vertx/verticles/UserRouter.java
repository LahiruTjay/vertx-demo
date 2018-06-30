package com.example.vertx.verticles;

import com.example.vertx.service.UserService;
import com.example.vertx.service.impl.UserServiceImpl;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class UserRouter {

	private static Vertx vertx;
	private UserService userService;

	public UserRouter() {
		userService = new UserServiceImpl();
	}

	public Router getUserRouter() {
		Router router = Router.router(vertx);
		router.get("/").handler(this::getAllUsers);
		router.get("/:id").handler(this::getUserById);
		return router;
	}

	private void getAllUsers(RoutingContext routingContext) {
		routingContext.response().putHeader("contetent-type", "application/json")
				.end(Json.encodePrettily(userService.getAllUsers()));
	}

	private void getUserById(RoutingContext routingContext) {
		long id = Long.parseLong(routingContext.request().getParam("id"));
		routingContext.response().putHeader("contetent-type", "application/json")
				.end(Json.encodePrettily(userService.getUserById(id)));
	}
}
