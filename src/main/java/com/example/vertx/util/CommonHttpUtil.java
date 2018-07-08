package com.example.vertx.util;

import java.util.function.Consumer;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class CommonHttpUtil {

	public void serviceUnavailable(RoutingContext routingContext) {
		routingContext.response().setStatusCode(503).end();
	}

	public void notFound(RoutingContext routingContext) {
		routingContext.response().setStatusCode(404).end();
	}

	public void badRequest(RoutingContext routingContext) {
		routingContext.response().setStatusCode(400).end();
	}

	public <T> Handler<AsyncResult<T>> resultHandler(RoutingContext routingContext, Consumer<T> consumer) {
		return res -> {
			if(res.succeeded()) {
				consumer.accept(res.result());
			} else {
				serviceUnavailable(routingContext);
			}
		};
	}

}
