package com.cesi.bloc.api;

import com.cesi.bloc.api.data.SimpleHttpResult;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        // Create a Router
        Router router = Router.router(vertx);

        // Allow POST requests on all urls
        router.route().handler(BodyHandler.create());

        // -- Set the routes in the router --
        // Main page
        router.get("/").handler(routingContext -> {
            routingContext.response()
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end("{\n" +
                            "\"body\": \"Welcome on the Agro CRUD API\",\n" +
                            "\"copyright\": \" 2022\"\n" +
                            "}");
        });

        // Get all employees handler routes
        router.get("/employees").handler(EmployeeHandler::getAllEmployee);

        // Get employee by id handler routes
        router.get("/employee/:id").handler(EmployeeHandler::getEmployeeById);

        // Create new employee handler routes
        router.post("/employee").handler(EmployeeHandler::createEmployee);

        // Update employee by id handler routes
        router.put("/employee/:id").handler(EmployeeHandler::updateEmployeeById);

        // Delete employee by id handler routes
        router.delete("/employee/:id").handler(EmployeeHandler::removeEmployeeById);

        // Serve static resources from the /assets directory
        router.route("/assets/*").handler(StaticHandler.create("assets"));

        // Create the HTTP server and show it port to the console
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(config().getInteger("http.port", 8888))
                .onSuccess(server ->
                        System.out.println(
                                "BHS API listening on http://localhost:" + server.actualPort() + "/"
                        )
                );
    }
}