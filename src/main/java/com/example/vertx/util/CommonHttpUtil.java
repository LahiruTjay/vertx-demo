package com.example.vertx.util;

import java.util.function.Consumer;

import com.example.vertx.dto.CommonHttpResponse;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class CommonHttpUtil {

    public void successGetResponse(RoutingContext routingContext, Object result) {
        routingContext.response()
            .setStatusCode(200)
            .putHeader("content-type", "application/json")
            .end(Json.encodePrettily(new CommonHttpResponse(CommonConstants.CODE_SUCCESSFUL, CommonConstants.CODE_SUCCESSFUL, result)));
    }

    public void serviceUnavailable(RoutingContext routingContext) {
        routingContext.response()
            .setStatusCode(503)
            .end(Json.encodePrettily(new CommonHttpResponse(CommonConstants.CODE_FAILED, CommonConstants.HTTP_504_UNAVAILABLE)));
    }

    public void notFound(RoutingContext routingContext) {
        routingContext.response()
            .setStatusCode(404)
            .end(Json.encodePrettily(new CommonHttpResponse(CommonConstants.CODE_FAILED, CommonConstants.HTTP_404_NOT_FOUND)));
    }

    public void badRequest(RoutingContext routingContext) {
        routingContext.response()
            .setStatusCode(400)
            .end(Json.encodePrettily(new CommonHttpResponse(CommonConstants.CODE_FAILED, CommonConstants.HTTP_400_BAD_REQUEST)));
    }

    public <T> Handler<AsyncResult<T>> resultHandler(RoutingContext routingContext, Consumer<T> consumer) {
        return res -> {
            if (res.succeeded()) {
                consumer.accept(res.result());
            } else {
                serviceUnavailable(routingContext);
            }
        };
    }

}
