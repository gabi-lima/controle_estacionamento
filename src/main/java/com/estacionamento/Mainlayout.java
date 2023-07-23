package com.estacionamento;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

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

    @FXML

    ObservableList<Estacionamento> dados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        nome.setCellValueFactory(new PropertyValueFactory<Estacionamento, String>("nome"));
        carro.setCellValueFactory(new PropertyValueFactory<Estacionamento, String>("carro"));
        placa.setCellValueFactory(new PropertyValueFactory<Estacionamento, String>("placa"));
        vaga.setCellValueFactory(new PropertyValueFactory<Estacionamento, String>("vaga"));

        // Chamar o método listar() para obter os dados do banco de
        // dados
        ArrayList<Estacionamento> listaEstacionamentos = listar();
        dados.addAll(listaEstacionamentos);

        tabela_estaciona.setItems(dados);
        // Tornando a tabela editável
        tabela_estaciona.setEditable(true);
        nome.setCellFactory(TextFieldTableCell.forTableColumn());
        carro.setCellFactory(TextFieldTableCell.forTableColumn());
        placa.setCellFactory(TextFieldTableCell.forTableColumn());
        vaga.setCellFactory(TextFieldTableCell.forTableColumn());

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

    @FXML
    void linhaSelecionada(MouseEvent event) {

    }

    // Manipulador de eventos para editar células da tabela
    @FXML
    private void onEditCommitNome(TableColumn.CellEditEvent<Estacionamento, String> event) {
        Estacionamento estacionamento = event.getRowValue();
        estacionamento.setNome(event.getNewValue());
        updateDatabase(estacionamento);
    }

    @FXML
    private void onEditCommitCarro(TableColumn.CellEditEvent<Estacionamento, String> event) {
        Estacionamento estacionamento = event.getRowValue();
        estacionamento.setCarro(event.getNewValue());
        updateDatabase(estacionamento);
    }

    @FXML
    private void onEditCommitPlaca(TableColumn.CellEditEvent<Estacionamento, String> event) {
        Estacionamento estacionamento = event.getRowValue();
        estacionamento.setPlaca(event.getNewValue());
        updateDatabase(estacionamento);
    }

    @FXML
    private void onEditCommitVaga(TableColumn.CellEditEvent<Estacionamento, String> event) {
        Estacionamento estacionamento = event.getRowValue();
        estacionamento.setVaga(event.getNewValue());
        updateDatabase(estacionamento);
    }

    // Atualiza a database após editar
    private void updateDatabase(Estacionamento estacionamento) {
        try {
            Conexao connection = new Conexao();
            PreparedStatement prepared_statement = connection.conectarBD()
                    .prepareStatement(
                            "UPDATE estacionamento SET nome = ?, carro = ?, placa = ?, vaga = ? WHERE placa = ?");
            prepared_statement.setString(1, estacionamento.getNome());
            prepared_statement.setString(2, estacionamento.getCarro());
            prepared_statement.setString(3, estacionamento.getPlaca());
            prepared_statement.setString(4, estacionamento.getVaga());
            prepared_statement.setString(5, estacionamento.getPlaca()); // Use the placa to identify the record to
                                                                        // update
            prepared_statement.executeUpdate();

            prepared_statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to delete the Estacionamento object from the TableView and database
    @FXML
    private void deletarEstacionamento(ActionEvent event) {

    }
}
