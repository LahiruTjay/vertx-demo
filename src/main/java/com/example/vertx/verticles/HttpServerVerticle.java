package com.example.vertx.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;

public class HttpServerVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> future) {
		vertx.createHttpServer().requestHandler(getRouter()::accept).listen(8080, result -> {
			if (result.succeeded()) {
				future.complete();
			} else {
				future.fail(result.cause());
			}
		});
	}

	private Router getRouter() {
		Router router = Router.router(vertx);
		UserRoutes.getAllUsers(router);
		UserRoutes.getUserById(router);
		return router;
	}
}