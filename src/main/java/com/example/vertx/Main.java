package com.example.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class Main {

	private static Vertx vertx;

	public static void main(String[] args) {
		VertxOptions options = new VertxOptions();
		vertx = Vertx.vertx(options);
		deployVerticle(HttpVerticle.class, "Http Vericle");
	}

	private static void deployVerticle(Class verticleClass, String name) {
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
