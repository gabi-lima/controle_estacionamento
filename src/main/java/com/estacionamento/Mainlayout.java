package com.estacionamento;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.estacionamento.DAO.Conexao;

public class Mainlayout implements Initializable {

    @FXML
    private TableColumn<Estacionamento, String> carro;

    @FXML
    private TableColumn<Estacionamento, String> nome;

    @FXML
    private TableColumn<Estacionamento, String> placa;

    @FXML
    private TableView<Estacionamento> tabela_estaciona;

    @FXML
    private TableColumn<?, ?> tempo_vaga;

    @FXML
    private TableColumn<Estacionamento, String> vaga;

    ObservableList<Estacionamento> dados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        nome.setCellValueFactory(new PropertyValueFactory<Estacionamento, String>("nome"));
        carro.setCellValueFactory(new PropertyValueFactory<Estacionamento, String>("carro"));
        placa.setCellValueFactory(new PropertyValueFactory<Estacionamento, String>("placa"));
        vaga.setCellValueFactory(new PropertyValueFactory<Estacionamento, String>("vaga"));

        // Chamar o método listar() da classe Conexao para obter os dados do banco de
        // dados
        ArrayList<Estacionamento> listaEstacionamentos = listar();
        dados.addAll(listaEstacionamentos);

        tabela_estaciona.setItems(dados);

    }

    /*
     * (Connection connection = DriverManager
     * .getConnection("jdbc:sqlite:D:\\java\\DB\\estacionamento.db"))
     */
    public static ArrayList<Estacionamento> listar() {
        try {
            Conexao connection = new Conexao();
            ArrayList<Estacionamento> response = new ArrayList<Estacionamento>();
            PreparedStatement prepared_statement = connection.conectarBD()
                    .prepareStatement("select * from estacionamento");
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
