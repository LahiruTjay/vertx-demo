package com.example.vertx.routes;

import com.example.vertx.service.UserService;
import com.example.vertx.service.impl.UserServiceImpl;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class UserRouter {

    private static Vertx vertx;
    private UserService userService;

    public UserRouter() {
        userService = new UserServiceImpl();
    }

    public Router testRouter() {
        Router router = Router.router(vertx);
        router.get()
            .handler(routingContext -> {
                System.out.println("Comes here 1");
                routingContext.next();
            });
        return router;
    }

    public Router getUserRouter() {
        Router router = Router.router(vertx);
        router.get("/").handler(userService::gerAllUsers);
        router.get("/:id").handler(userService::getUserById);
        router.post("/").handler(userService::saveUser);
        router.patch("/:id").handler(userService::updateUser);
        router.delete("/:id").handler(userService::deleteUserById);
        router.delete("/").handler(userService::deleteAllUsers);
        return router;
    }
}
