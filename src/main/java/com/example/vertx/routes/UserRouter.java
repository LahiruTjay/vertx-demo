package com.example.vertx.routes;

import com.example.vertx.service.UserService;
import com.example.vertx.service.impl.UserServiceImpl;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class UserRouter {

	private static Vertx vertx;
	private UserService userService;

	public UserRouter() {
		userService = new UserServiceImpl();
	}

	public Router getUserRouter() {
		Router router = Router.router(vertx);
		router.get("/:id").handler(userService::getUserById);
		return router;
	}
}
