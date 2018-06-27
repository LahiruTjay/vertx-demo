package com.example.vertx;

import com.example.vertx.verticles.HttpVerticle;

import io.vertx.core.Vertx;

public class Main {

	private static Vertx vertx;

	public static void main(String[] args) {
		// VertxOptions options = new VertxOptions();
		vertx = Vertx.vertx();
		deployVerticle(HttpVerticle.class, "Http Vericle");
	}

	private static void deployVerticle(Class<HttpVerticle> verticleClass, String name) {
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
