package com.example.vertx.db;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;

public class JDBCService {

	private Vertx vertx;
	private JDBCClient jdbcClient;

	public JDBCService(Vertx vertx) {
		this.vertx = vertx;
		this.jdbcClient = JDBCClient.createShared(vertx,
				new JsonObject().put("url", "jdbc:hsqldb:mem:test?shutdown=true")
						.put("driver_class", "org.hsqldb.jdbcDriver")
						.put("max_pool_size", 30).put("user", "SA")
						.put("password", ""));
	}
	

}
