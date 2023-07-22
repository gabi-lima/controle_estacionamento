package com.estacionamento;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.estacionamento.DAO.Conexao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        ArrayList<Estacionamento> listaEstacionamentos = Conexao.listar();
        dados.addAll(listaEstacionamentos);

        tabela_estaciona.setItems(dados);

    }

}
