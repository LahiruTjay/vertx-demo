package com.example.vertx.verticles;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.vertx.dto.CommonHttpResponse;
import com.example.vertx.routes.UserRouter;
import com.example.vertx.util.CommonConstants;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
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
		mainRouter.route().last().handler(routingContext -> {
			routingContext.response().setStatusCode(404).end(Json.encodePrettily(
					new CommonHttpResponse(CommonConstants.CODE_FAILED, CommonConstants.HTTP_404_NOT_FOUND)));
		});

		// Add sub-router for user service
		mainRouter.mountSubRouter("/users", new UserRouter().testRouter());
		mainRouter.mountSubRouter("/users", new UserRouter().getUserRouter());

		return mainRouter;
	}
}