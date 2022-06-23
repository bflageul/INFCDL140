package com.cesi.bloc.api.data;

public class JobImpl implements IJob {

    public JobImpl() {
    }

    private int id;
    private String job;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getJob() {
        return job;
    }

    @Override
    public void setJob(String job) {
        this.job = job;
    }
}
