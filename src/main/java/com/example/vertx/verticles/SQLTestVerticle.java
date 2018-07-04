package com.example.vertx.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class SQLTestVerticle extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		final JDBCClient client = JDBCClient.createShared(vertx,
				new JsonObject().put("url", "jdbc:mysql://localhost/demo_db")
						.put("driver_class", "com.mysql.cj.jdbc.Driver").put("user", "root").put("password", "root"));

		client.getConnection(conn -> {

			if (conn.failed()) {
				System.err.println(conn.cause().getMessage());
				return;
			}

			final SQLConnection connection = conn.result();

			connection.query("SELECT * FROM user", result -> {

				for (JsonArray line : result.result().getResults()) {
					System.out.println(line.encode());
				}

				connection.close(done -> {
					if (done.failed()) {
						throw new RuntimeException(done.cause());
					}
				});
			});
		});

	}

}
