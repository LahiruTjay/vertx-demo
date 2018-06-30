package com.example.vertx.verticles;

import com.example.vertx.service.UserService;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class UserRouter {

	public static Vertx vertx;

	public static Router getUserRouter() {
		Router router = Router.router(vertx);
		router.get("/").handler(UserRouter::getAllUsers);
		router.get("/:id").handler(UserRouter::getUserById);
		return router;
	}

	private static void getAllUsers(RoutingContext routingContext) {
		routingContext.response().putHeader("contetent-type", "application/json")
				.end(Json.encodePrettily(UserService.getAllUsers()));
	}

	private static void getUserById(RoutingContext routingContext) {
		long id = Long.parseLong(routingContext.request().getParam("id"));
		routingContext.response().putHeader("contetent-type", "application/json")
				.end(Json.encodePrettily(UserService.getUserById(id)));
	}
}
