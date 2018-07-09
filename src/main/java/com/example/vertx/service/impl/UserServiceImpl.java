package com.example.vertx.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.vertx.db.JDBCService;
import com.example.vertx.models.SystemUser;
import com.example.vertx.service.UserService;
import com.example.vertx.util.CommonHttpUtil;
import com.example.vertx.util.SQLQueries;

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
		String sqlQuery = SQLQueries.SQL_INSERT_USER;
		JsonArray queryParam = new JsonArray().add(name).add(email);

		jdbcService.saveOrUpdateEntity(sqlQuery, queryParam).setHandler(resultHandler(routingContext, res -> {
			if (res) {
				successGetResponse(routingContext, userObj);
			} else {
				serviceUnavailable(routingContext);
			}
		}));
	}

	@Override
	public void updateUser(RoutingContext routingContext) {

		long id = Long.parseLong(routingContext.request().getParam("id"));

		JsonObject userObj = routingContext.getBodyAsJson();
		String name = userObj.getString("username");
		String email = userObj.getString("email");
		String sqlQuery = SQLQueries.SQL_UPDATE_USER;
		JsonArray queryParam = new JsonArray().add(name).add(email).add(id);

		jdbcService.saveOrUpdateEntity(sqlQuery, queryParam).setHandler(resultHandler(routingContext, res -> {
			if (res) {
				successGetResponse(routingContext, userObj);
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
				successGetResponse(routingContext, systemUser);
			}
		}));
	}

	@Override
	public void gerAllUsers(RoutingContext routingContext) {
		
		System.out.println("Comes here 2");
		
		String sqlQuery = SQLQueries.SQL_SELECT_ALL_USERS;
		JsonArray queryParam = new JsonArray();

		jdbcService.getSingleEntityByParams(sqlQuery, queryParam).setHandler(resultHandler(routingContext, res -> {
			if (!res.isPresent()) {
				notFound(routingContext);
			} else {
				List<JsonObject> list = (List<JsonObject>) res.get();
				List<SystemUser> userList = list.stream().map(obj -> new SystemUser().getSystemUser(obj))
						.collect(Collectors.toList());
				successGetResponse(routingContext, userList);
			}
		}));
	}

	@Override
	public void deleteUserById(RoutingContext routingContext) {
		Long id = Long.parseLong(routingContext.request().getParam("id"));
		String sqlQuery = SQLQueries.SQL_DELETE_USER_BY_ID;
		JsonArray queryParam = new JsonArray().add(id);

		jdbcService.saveOrUpdateEntity(sqlQuery, queryParam).setHandler(resultHandler(routingContext, res -> {
			if (res) {
				successGetResponse(routingContext, null);
			} else {
				serviceUnavailable(routingContext);
			}
		}));
	}

	@Override
	public void deleteAllUsers(RoutingContext routingContext) {
		String sqlQuery = SQLQueries.SQL_DELETE_ALL_USERS;
		JsonArray queryParam = new JsonArray();

		jdbcService.saveOrUpdateEntity(sqlQuery, queryParam).setHandler(resultHandler(routingContext, res -> {
			if (res) {
				successGetResponse(routingContext, null);
			} else {
				serviceUnavailable(routingContext);
			}
		}));
	}

}
