package com.example.vertx.routes;

import com.example.vertx.db.JDBCService;
import com.example.vertx.models.SystemUser;
import com.example.vertx.service.UserService;
import com.example.vertx.service.impl.UserServiceImpl;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class UserRouter {

	private static Vertx vertx;
	private UserService userService;
	private JDBCService jdbcService;

	public UserRouter() {
		userService = new UserServiceImpl();
		jdbcService = new JDBCService();
	}

	public Router getUserRouter() {
		Router router = Router.router(vertx);
		router.get("/").handler(this::getAllUsers);
		router.get("/:id").handler(this::getUserById);
		return router;
	}

	private void serviceUnavailable(RoutingContext routingContext) {
		routingContext.response().setStatusCode(503).end();
	}

	private void notFound(RoutingContext routingContext) {
		routingContext.response().setStatusCode(404).end();
	}

	private void badRequest(RoutingContext routingContext) {
		routingContext.response().setStatusCode(400).end();
	}

	private <T> Handler<AsyncResult<T>> resultHandler(RoutingContext routingContext, Consumer<T> consumer) {
		return res -> {
			if (res.succeeded()) {
				consumer.accept(res.result());
			} else {
				serviceUnavailable(routingContext);
			}
		};
	}

	private void getAllUsers(RoutingContext routingContext) {
		routingContext.response().putHeader("contetent-type", "application/json")
				.end(Json.encodePrettily(userService.getAllUsers()));
	}

	private void getUserById(RoutingContext routingContext) {

		String param = routingContext.request().getParam("id");

		if (param == null) {
			return;
		}

		long userId = Long.parseLong(param);

		/*jdbcService.getUserById(userId).setHandler(resultHandler(routingContext, res -> {
			if (!res.isPresent()) {
				notFound(routingContext);
			} else {
				String userObj = Json.encodePrettily(res.get());
				routingContext.response().putHeader("content-type", "application/json").end(userObj);
			}
		}));*/
		
		
		jdbcService.getEntityByParams("SELECT * FROM user WHERE id = ?", new JsonArray().add(userId)).setHandler(resultHandler(routingContext, res -> {
			if (!res.isPresent()) {
				notFound(routingContext);
			} else {
				
				//String userObj = Json.encodePrettily(res.get());
				
				SystemUser user = new SystemUser().getSystemUser((JsonObject) res.get());
				routingContext.response().putHeader("content-type", "application/json").end(Json.encodePrettily(user));
			}
		}));
	}

	private void createUser(RoutingContext routingContext) {

	}

	private void updateUser(RoutingContext routingContext) {

	}

	private void deleteUserById(RoutingContext routingContext) {

	}

	private void deleteAllUsers(RoutingContext routingContext) {

	}
}
