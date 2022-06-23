package com.cesi.bloc.api.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectImpl implements IConnect {

    /**
     * instance du service
     */
    private static IConnect instance;

    private ConnectImpl() {
        //do nothing
    }

    /**
     * Récupère l'instance du service
     *
     * @return l'instance du service
     */
    public static IConnect getInstance() {
        if (ConnectImpl.instance == null) {
            instance = new ConnectImpl();
        }
        return instance;
    }

    @Override
    /**
     * @see Connect#getConnection()
     */
    public Connection getConnection() {
        Connection connexion = null;

        try {
            Class.forName("org.postgresql.Driver");
            connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/agrodb","root","rootpassword");
        } catch (Exception e) {
            System.out.println(e);
        }

        return connexion;
    }
}