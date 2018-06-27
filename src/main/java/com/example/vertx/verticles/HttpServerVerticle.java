package com.example.vertx.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
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
		router.route("/").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "text/html").end("This is the Main page");
		});
		return router;
	}
}