package com.cesi.bloc.api.employees;

import java.sql.Date;

/**
 * Special class used to create a list of users in UsersManagerImpl.getAllUsers (then routed with /users)
 */
public class EmployeeDetails {
    public String firstname;
    public String lastname;
    public String phone;
    public String cellphone;
    public String mail;
    public String jobName;
    public String cityName;
}