package com.example.vertx.service.impl;

import com.example.vertx.db.JDBCService;
import com.example.vertx.db.SQLQueries;
import com.example.vertx.models.SystemUser;
import com.example.vertx.service.UserService;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserServiceImpl implements UserService {

	private JDBCService jdbcService;

	public UserServiceImpl() {
		jdbcService = new JDBCService();
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

	// This should be common
	private <T> Handler<AsyncResult<T>> resultHandler(RoutingContext routingContext, Consumer<T> consumer) {
		return res -> {
			if (res.succeeded()) {
				consumer.accept(res.result());
			} else {
				serviceUnavailable(routingContext);
			}
		};
	}

	@Override
	public void getUserById(RoutingContext routingContext) {

		String param = routingContext.request().getParam("id");
		if (param == null) {
			badRequest(routingContext);
			return;
		}

		String sqlQuery = SQLQueries.SQL_SELECT_USER_BY_ID;
		JsonArray queryParam = new JsonArray().add(Long.parseLong(param));

		jdbcService.getEntityByParams(sqlQuery, queryParam).setHandler(resultHandler(routingContext, res -> {
			if (!res.isPresent()) {
				notFound(routingContext);
			} else {
				SystemUser systemUser = new SystemUser().getSystemUser((JsonObject) res.get());
				routingContext.response().putHeader("content-type", "application/json")
						.end(Json.encodePrettily(systemUser));
			}
		}));
	}

}
