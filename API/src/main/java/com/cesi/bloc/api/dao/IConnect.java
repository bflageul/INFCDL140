package com.cesi.bloc.api.dao;

import java.sql.Connection;

public interface IConnect {

    /**
     * Récupère la connection a la BDD
     *
     * @return la connection
     */
    Connection getConnection();

}