package com.example.vertx.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.vertx.db.JDBCService;
import com.example.vertx.db.SQLQueries;
import com.example.vertx.models.SystemUser;
import com.example.vertx.service.UserService;
import com.example.vertx.util.CommonHttpUtil;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserServiceImpl extends CommonHttpUtil implements UserService {

	private JDBCService jdbcService;

	public UserServiceImpl() {
		jdbcService = new JDBCService();
	}

	@Override
	public void saveUser(RoutingContext routingContext) {

		JsonObject userObj = routingContext.getBodyAsJson();
		String name = userObj.getString("username");
		String email = userObj.getString("email");

		String sqlQuery = "INSERT INTO user (username, email) VALUES (?, ?)";
		JsonArray queryParam = new JsonArray().add(name).add(email);

		jdbcService.saveOrUpdateEntity(sqlQuery, queryParam).setHandler(resultHandler(routingContext, res -> {
			if (res) {
				routingContext.response().putHeader("content-type", "application/json")
						.end(Json.encodePrettily(userObj));
			} else {
				serviceUnavailable(routingContext);
			}
		}));
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

		jdbcService.getSingleEntityByParams(sqlQuery, queryParam).setHandler(resultHandler(routingContext, res -> {
			if (!res.isPresent()) {
				notFound(routingContext);
			} else {
				List<JsonObject> list = (List<JsonObject>) res.get();
				SystemUser systemUser = new SystemUser().getSystemUser(list.get(0));
				routingContext.response().putHeader("content-type", "application/json")
						.end(Json.encodePrettily(systemUser));
			}
		}));
	}

	@Override
	public void gerAllUsers(RoutingContext routingContext) {
		String sqlQuery = SQLQueries.SQL_SELECT_ALL_USERS;
		JsonArray queryParam = new JsonArray();

		jdbcService.getSingleEntityByParams(sqlQuery, queryParam).setHandler(resultHandler(routingContext, res -> {
			if (!res.isPresent()) {

			} else {
				List<JsonObject> list = (List<JsonObject>) res.get();
				List<SystemUser> userList = list.stream().map(obj -> new SystemUser().getSystemUser(obj))
						.collect(Collectors.toList());
				routingContext.response().putHeader("content-type", "application/json")
						.end(Json.encodePrettily(userList));
			}
		}));
	}

}
