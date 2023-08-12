package com.estacionamento.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public Connection conectarBD() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:estacionamento.db";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("erro de conexão:" + e.getMessage());
        }
        return conn;
    }

}