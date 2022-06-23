package com.cesi.bloc.api.data;

public class CityImpl implements ICity {

    public CityImpl() {
    }

    private int id;
    private String city;

    @Override
    public int getId() {
        return id;
    }


    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }
}
