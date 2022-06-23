package com.cesi.bloc.api;

import com.cesi.bloc.api.dao.Employees;
import com.cesi.bloc.api.data.EmployeeImpl;
import com.cesi.bloc.api.data.IEmployee;
import com.cesi.bloc.api.data.SimpleHttpResult;
import com.cesi.bloc.api.employees.EmployeeDetails;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.javatuples.Triplet;

import java.sql.SQLException;
import java.util.Base64;
import java.util.List;


public class EmployeeHandler {

    /**
     * Routed by GET '/employees'
     */
    public static void getAllEmployee(RoutingContext routingContext) {
        try {

            List<com.cesi.bloc.api.data.IEmployee> usersList = Employees.getAllEmployees();

            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(usersList));
        } catch (SQLException e) {
            e.printStackTrace();
            routingContext.response()
                    .setStatusCode(500)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(new SimpleHttpResult(500, "Internal server error")));
        } catch (DecodeException e) {
            e.printStackTrace();
            routingContext.response()
                    .setStatusCode(400)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(new SimpleHttpResult(400, "Invalid JSON")));
        }
    }

    /**
     * Routed by GET '/user/id'
     */
    public static void getEmployeeById(RoutingContext routingContext) {
        try {

            Triplet employeeById;
            int id = Integer.parseInt(routingContext.pathParam("id"));

            employeeById = Employees.getEmployeeById(id);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(employeeById));
        } catch (SQLException e) {
            e.printStackTrace();
            routingContext.response()
                    .setStatusCode(500)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(new SimpleHttpResult(500, "Internal server error")));
        } catch (DecodeException e) {
            e.printStackTrace();
            routingContext.response()
                    .setStatusCode(400)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(new SimpleHttpResult(400, "Invalid JSON")));
        } catch (NullPointerException e) {
            e.printStackTrace();
            routingContext.response()
                    .setStatusCode(400)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(new SimpleHttpResult(400, "Body is empty")));
        }
    }


    /**
     * Routed by POST '/employee'
     */
    public static void createEmployee(RoutingContext routingContext) {
        try {

            EmployeeDetails employeeDetails = Json.decodeValue(routingContext.getBodyAsString(), EmployeeDetails.class);

            // then execute update process
            EmployeeDetails employeeCreated = Employees.createEmployee(employeeDetails);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily("A new employee (AKA " + employeeCreated.firstname + ") has been created !"));
        } catch (SQLException e) {
            e.printStackTrace();
            routingContext.response()
                    .setStatusCode(500)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(new SimpleHttpResult(500, "Internal server error")));
        } catch (DecodeException e) {
            e.printStackTrace();
            routingContext.response()
                    .setStatusCode(400)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(new SimpleHttpResult(400, "Invalid JSON")));
        } catch (NullPointerException e) {
            e.printStackTrace();
            routingContext.response()
                    .setStatusCode(400)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(new SimpleHttpResult(400, "Body is empty")));
        }
    }

    /**
     * Routed by PUT '/employee/id'
     */

    public static void updateEmployeeById(RoutingContext routingContext) {
        int id = Integer.parseInt(routingContext.pathParam("id"));
        try {

            EmployeeDetails employeeDetails = Json.decodeValue(routingContext.getBodyAsString(), EmployeeDetails.class);
            IEmployee employee = new EmployeeImpl();

            // then execute update process
            EmployeeDetails userUpdated = Employees.updateEmployeeById(id, employeeDetails);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(employeeDetails.firstname + "'s information have been correctly updated !"));
        } catch (SQLException e) {
            e.printStackTrace();
            routingContext.response()
                    .setStatusCode(500)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(new SimpleHttpResult(500, "Internal server error")));
        } catch (DecodeException e) {
            e.printStackTrace();
            routingContext.response()
                    .setStatusCode(400)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(new SimpleHttpResult(400, "Invalid JSON")));
        } catch (NullPointerException e) {
            e.printStackTrace();
            routingContext.response()
                    .setStatusCode(400)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(new SimpleHttpResult(400, "Body is empty")));
        }
    }

    public static void removeEmployeeById(RoutingContext routingContext) {
        try {
            int id = Integer.parseInt(routingContext.pathParam("id"));

            Employees.removeEmployeeById(id);
            routingContext.response()
                    .setStatusCode(200)
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily("Employee removed !"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}