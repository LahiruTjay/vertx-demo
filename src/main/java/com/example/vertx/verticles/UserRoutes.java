package com.example.vertx.verticles;

import com.example.vertx.service.UserService;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class UserRoutes {

	public static Route getUserById(Router router) {
		Route route = router.route("/users/:id").handler(routingContext -> {
			long id = Long.parseLong(routingContext.request().getParam("id"));
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(UserService.getUserById(id)));
		});
		return route;
	}

	public static Route getAllUsers(Router router) {
		Route route = router.route("/users").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "application/json; charset=utf-8")
					.end(Json.encodePrettily(UserService.getAllUsers()));
		});
		return route;
	}
}
