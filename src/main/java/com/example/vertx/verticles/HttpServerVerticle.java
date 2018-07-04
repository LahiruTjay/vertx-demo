package com.example.vertx.verticles;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

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

		Router mainRouter = Router.router(vertx);

		Set<HttpMethod> httpMethods = Stream.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH).collect(Collectors.toSet());
		Set<String> headers = Stream.of("Content-Type", "Access-Control-Allow-Origin").collect(Collectors.toSet());

		mainRouter.route().handler(CorsHandler.create("*").allowedHeaders(headers).allowedMethods(httpMethods));
		mainRouter.route().handler(BodyHandler.create());

		// Add sub-router for user service
		mainRouter.mountSubRouter("/users", new UserRouter().getUserRouter());
		
		return mainRouter;
	}
}