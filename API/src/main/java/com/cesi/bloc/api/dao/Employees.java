package com.cesi.bloc.api.dao;

import com.cesi.bloc.api.data.*;
import com.cesi.bloc.api.employees.EmployeeDetails;
import org.javatuples.Triplet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Employees {

    /**
     * Check if job exists     *
     * @param job
     */
    public static boolean doesJobExist(String job) throws SQLException {

        String query =  "SELECT COUNT(*) FROM job WHERE jobName = ?;";

        IConnect conn = ConnectImpl.getInstance();
        PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
        preparedStatement.setString(1, job);

        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        System.out.println(rs.getInt("count"));
        if (rs.getInt("count") > 0) {
            return true;
        }
        return false;
    }

    /**
     * Check if city exists     *
     * @param city
     */
    public static boolean doesCityExist(String city) throws SQLException {

        String query =  "SELECT COUNT(*) FROM city WHERE cityName = ?;";

        IConnect conn = ConnectImpl.getInstance();
        PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
        preparedStatement.setString(1, city);

        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        System.out.println(rs.getInt("count"));
        if (rs.getInt("count") > 0) {
            return true;
        }
        return false;
    }

    /**
     * Function rendering a list of all employees registered in database     *
     * @return List<Employee>
     */
    public static List<com.cesi.bloc.api.data.IEmployee> getAllEmployees() throws SQLException {

        String query = "SELECT * FROM employee;";

        IConnect conn = ConnectImpl.getInstance();
        Statement statement = conn.getConnection().createStatement();
        ResultSet result = statement.executeQuery(query);

        List<com.cesi.bloc.api.data.IEmployee> employeesList = new ArrayList<>();

        while (result.next()) {
                com.cesi.bloc.api.data.IEmployee employee = new EmployeeImpl();
                employee.setId(result.getInt("id"));
                employee.setFirsname(result.getString("firstname"));
                employee.setLastname(result.getString("lastname"));
                employee.setPhone(result.getString("phone"));
                employee.setCellphone(result.getString("cellphone"));
                employee.setJob(result.getInt("jobId"));
                employee.setCity(result.getInt("cityId"));
                employeesList.add(employee);
        }
        return employeesList;
    }

    /**
     * Function specific employee, specified by its Id     *
     * @return employee
     */
    public static Triplet getEmployeeById(int userId) throws SQLException {

        String query = "SELECT employee.id, employee.firstname, employee.lastname, employee.phone, employee.cellphone, employee.mail, job.job, city.city FROM employee " +
                "LEFT JOIN job ON job.id = employee.jobId " +
                "LEFT JOIN city ON city.id = employee.cityId" +
                "WHERE employee.id = ?;";

        Triplet<ICity, IJob, IEmployee> triplet = null;
        IEmployee employee = new EmployeeImpl();
        IJob job = new JobImpl();
        ICity city = new CityImpl();

        IConnect conn = ConnectImpl.getInstance();
        PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            employee.setId(result.getInt("id"));
            employee.setFirsname(result.getString("firstname"));
            employee.setLastname(result.getString("lastname"));
            employee.setPhone(result.getString("phone"));
            employee.setCellphone(result.getString("cellphone"));
            employee.setJob(result.getInt("jobId"));
            employee.setCity(result.getInt("cityId"));
            job.setJob(result.getString("jobName"));
            city.setCity(result.getString("cityName"));

            triplet = new Triplet<>(city, job, employee);
        }
        return triplet;
    }

    /**
     * Function creating a new employee.     *
     * @return employeeDetails
     */
    public static EmployeeDetails createEmployee(EmployeeDetails employeeDetails) throws SQLException {

        IEmployee employee = new EmployeeImpl();
        IJob job = new JobImpl();
        ICity city = new CityImpl();

        boolean jobExist = doesJobExist(employeeDetails.jobName);
        boolean cityExist = doesCityExist(employeeDetails.cityName);
        int jobId;
        int cityId;

        IConnect conn = ConnectImpl.getInstance();

        if (!jobExist) {
            String query = "INSERT INTO job(jobName) values (?) RETURNING id;";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
            preparedStatement.setString(1, employeeDetails.jobName);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            jobId = rs.getInt("id");
        }else {
            String query = "SELECT id FROM job WHERE jobName = ?;";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
            preparedStatement.setString(1, employeeDetails.jobName);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            jobId = rs.getInt("id");
        }

        if (!cityExist) {
            String query = "INSERT INTO city(cityName) values (?) RETURNING id;";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
            preparedStatement.setString(1, employeeDetails.cityName);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            cityId = rs.getInt("id");
        }else {
            String query = "SELECT id FROM city WHERE cityName = ?;";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
            preparedStatement.setString(1, employeeDetails.cityName);
            ResultSet rs = preparedStatement.executeQuery(query);
            rs.next();
            cityId = rs.getInt("id");
        }

        String query = "INSERT INTO employee(firstname, lastname, phone, cellphone, mail, jobId, cityId) values (?, ?, ?, ?, ?, ?, ?) RETURNING id;";
        PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
        preparedStatement.setString(1, employeeDetails.firstname);
        preparedStatement.setString(2, employeeDetails.lastname);
        preparedStatement.setString(3, employeeDetails.phone);
        preparedStatement.setString(4, employeeDetails.cellphone);
        preparedStatement.setString(5, employeeDetails.mail);
        preparedStatement.setInt(6, jobId);
        preparedStatement.setInt(7, cityId);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        int userId = rs.getInt("id");

        return employeeDetails;
    }

    /**
     * @param id
     * @throws SQLException
     */
    public static EmployeeDetails updateEmployeeById(int id, EmployeeDetails employeeDetails) throws SQLException {

        IEmployee employee = new EmployeeImpl();
        IConnect conn = ConnectImpl.getInstance();

        String query = "SELECT id FROM job WHERE jobName = ?;";
        PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
        preparedStatement.setString(1, employeeDetails.jobName);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        int jobId = rs.getInt("id");

        query = "SELECT id FROM city WHERE cityName = ?;";
        preparedStatement = conn.getConnection().prepareStatement(query);
        preparedStatement.setString(1, employeeDetails.cityName);
        rs = preparedStatement.executeQuery();
        rs.next();
        int cityId= rs.getInt("id");

        query = "UPDATE employee SET (firstname,lastname, phone, cellphone, mail, jobId, cityId) = (?, ?, ?, ?, ?, ?, ?) WHERE id = ?;";
        preparedStatement = conn.getConnection().prepareStatement(query);

        preparedStatement.setString(1, employeeDetails.firstname);
        preparedStatement.setString(2, employeeDetails.lastname);
        preparedStatement.setString(3, employeeDetails.phone);
        preparedStatement.setString(4, employeeDetails.cellphone);
        preparedStatement.setString(5, employeeDetails.mail);
        preparedStatement.setInt(6, jobId);
        preparedStatement.setInt(7, cityId);
        preparedStatement.setInt(8, id);
        preparedStatement.executeUpdate();

        return employeeDetails;
    }

 /*
  */
    public static void removeEmployeeById(int id) throws SQLException {
        String query = "DELETE FROM employee WHERE id = ?;";
        IConnect conn = ConnectImpl.getInstance();
        PreparedStatement preparedStatement = conn.getConnection().prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
}