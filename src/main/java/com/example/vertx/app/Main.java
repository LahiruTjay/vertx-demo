package com.example.vertx.app;

import com.example.vertx.verticles.HttpServerVerticle;
import com.example.vertx.verticles.JDBCTestVerticle;

import io.vertx.core.Vertx;

public class Main {

	private static Vertx vertx;

	public static void main(String[] args) {
		// VertxOptions options = new VertxOptions();
		vertx = Vertx.vertx();
		deployVerticle(HttpServerVerticle.class, "Http Vericle");
		deployVerticle(JDBCTestVerticle.class, "JDBC Vericle");
	}

	private static void deployVerticle(Class<?> verticleClass, String name) {
		vertx.deployVerticle(verticleClass.getName(), response -> {
			if (response.succeeded()) {
				String deploymentId = response.result();
				System.out.println("Deployment Id : " + deploymentId);
			} else {
				response.cause().printStackTrace();
			}
		});
	}
}
