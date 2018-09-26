package com.example.vertx.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class HSQLDBTestVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        final JDBCClient client = JDBCClient.createShared(vertx, new JsonObject().put("url", "jdbc:hsqldb:mem:test?shutdown=true")
            .put("driver_class", "org.hsqldb.jdbcDriver")
            .put("max_pool_size", 30)
            .put("user", "SA")
            .put("password", ""));

        client.getConnection(conn -> {

            if (conn.failed()) {
                System.err.println(conn.cause()
                    .getMessage());
                return;
            }

            final SQLConnection connection = conn.result();

            connection.execute("CREATE TABLE test(id int primary key, name varchar(255))", res -> {

                if (res.failed()) {
                    throw new RuntimeException(res.cause());
                }

                connection.execute("INSERT INTO test VALUES (1, 'Hello')", insert -> {
                    connection.query("SELECT * FROM test", result -> {
                        for (JsonArray line : result.result()
                            .getResults()) {
                            System.out.println(line.encode());
                        }
                        connection.close(done -> {
                            if (done.failed()) {
                                throw new RuntimeException(done.cause());
                            }
                        });
                    });
                });
            });
        });

    }

}
