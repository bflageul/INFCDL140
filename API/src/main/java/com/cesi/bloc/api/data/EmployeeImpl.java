package com.cesi.bloc.api.data;

public class EmployeeImpl implements IEmployee {
    private int id;
    private String firsname;
    private String lastname;
    private String phone;
    private String cellphone;
    private String mail;
    private int jobId;
    private int cityId;

    public EmployeeImpl(int id, String firsname, String lastname, String phone, String cellphone, String mail, int job, int city) {
        this.id = id;
        this.firsname = firsname;
        this.lastname = lastname;
        this.phone = phone;
        this.cellphone = cellphone;
        this.mail = mail;
        this.jobId = job;
        this.cityId = city;
    }

    public EmployeeImpl() {
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getFirsname() {
        return firsname;
    }

    @Override
    public void setFirsname(String firsname) {
        this.firsname = firsname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getCellphone() {
        return cellphone;
    }

    @Override
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    @Override
    public String getMail() {
        return mail;
    }

    @Override
    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public int getJob() {
        return jobId;
    }

    @Override
    public void setJob(int job) {
        this.jobId = job;
    }

    @Override
    public int getCity() {
        return cityId;
    }

    @Override
    public void setCity(int city) {
        this.cityId = city;
    }

}
