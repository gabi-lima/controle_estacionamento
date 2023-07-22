package com.estacionamento.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import com.estacionamento.Estacionamento;

public class Conexao {
    public static ArrayList<Estacionamento> listar() {
        try (Connection connection = DriverManager
                .getConnection("jdbc:sqlite:D:\\java\\DB\\estacionamento.db")) {

            ArrayList<Estacionamento> response = new ArrayList<Estacionamento>();
            PreparedStatement prepared_statement = connection.prepareStatement("select * from estacionamento");
            ResultSet result_set = prepared_statement.executeQuery();

            while (result_set.next()) {
                Estacionamento estacionamento = new Estacionamento(
                        result_set.getString("nome"),
                        result_set.getString("carro"),
                        result_set.getString("placa"),
                        result_set.getString("vaga"));

                response.add(estacionamento);
            }
            result_set.close();
            prepared_statement.close();

            return response;
        } catch (Exception e) {

            System.out.println(e.getMessage());
            return null;

        }

    }

}