package com.example.vertx.db;

import java.util.List;
import java.util.Optional;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class JDBCService {

	private JDBCClient jdbcClient;

	public JDBCService() {
		this(Vertx.vertx());
	}

	public JDBCService(Vertx vertx) {
		this.jdbcClient = JDBCClient.createShared(vertx,
				new JsonObject().put("url", "jdbc:mysql://localhost/demo_db?autoReconnect=true&useSSL=false")
						.put("driver_class", "com.mysql.cj.jdbc.Driver").put("user", "root").put("password", "root"));
	}

	private Handler<AsyncResult<SQLConnection>> connHandler(Future<?> future, Handler<SQLConnection> handler) {
		return conn -> {
			if (conn.succeeded()) {
				SQLConnection connection = conn.result();
				handler.handle(connection);
			} else {
				future.fail(conn.cause());
			}
		};
	}

	public Future<Optional<?>> getSingleEntityByParams(String sqlQuery, JsonArray array) {
		Future<Optional<?>> result = Future.future();
		jdbcClient.getConnection(connHandler(result, connection -> {
			connection.queryWithParams(sqlQuery, array, res -> {
				if (res.failed()) {
					result.fail(res.cause());
				} else {
					List<JsonObject> list = res.result().getRows();
					if (list == null || list.isEmpty()) {
						result.complete(Optional.empty());
					} else {
						result.complete(Optional.of(list));
					}
				}
				connection.close();
			});
		}));
		return result;
	}
}
