package com.estacionamento;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.estacionamento.DAO.Conexao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

public class Mainlayout implements Initializable {
    @FXML
    private Button botaoAdd;

    @FXML
    private TextField inputCarro;

    @FXML
    private TextField inputNome;

    @FXML
    private TextField inputplaca;

    @FXML
    private ChoiceBox<String> vagaLivre;

    private String[] vagas = { "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "B1", "B2", "B3", "B4",
            "B5", "B6", "B7", "B8", "B9", "B10" };

    @FXML
    private TableColumn<Estacionamento, String> carro;

    @FXML
    private TableColumn<Estacionamento, String> nome;

    @FXML
    private TableColumn<Estacionamento, String> placa;

    @FXML
    private TableView<Estacionamento> tabela_estaciona;

    @FXML
    private TableColumn<Estacionamento, String> tempo_vaga;

    @FXML
    private TableColumn<Estacionamento, String> vaga;

    @FXML

    ObservableList<Estacionamento> dados = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // Adiciona as vagas ao ChoiceBox
        vagaLivre.getItems().addAll(vagas);
        // Adiciona os valores ao tableview
        nome.setCellValueFactory(new PropertyValueFactory<Estacionamento, String>("nome"));
        carro.setCellValueFactory(new PropertyValueFactory<Estacionamento, String>("carro"));
        placa.setCellValueFactory(new PropertyValueFactory<Estacionamento, String>("placa"));
        vaga.setCellValueFactory(new PropertyValueFactory<Estacionamento, String>("vaga"));
        tempo_vaga.setCellValueFactory(new PropertyValueFactory<Estacionamento, String>("tempo_vaga"));

        // Chamar o método listar() para obter os dados do banco de dados
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

    private boolean vagaDisponivel(String vaga) {
        for (Estacionamento estacionamento : dados) {
            if (estacionamento.getVaga().equals(vaga)) {
                return false; // Vaga já ocupada
            }
        }
        return true; // Vaga disponível
    }

    public void adicionar() {
        String nome = inputNome.getText();
        String carro = inputCarro.getText();
        String placa = inputplaca.getText();
        String vaga = vagaLivre.getValue();

        // Verifica se todos os campos estão preenchidos
        if (nome.isEmpty() || carro.isEmpty() || placa.isEmpty() || vaga == null) {
            System.out.println("Por favor, preencha todos os campos antes de adicionar.");
            return;
        }

        // Verifica se a vaga selecionada está disponível
        if (!vagaDisponivel(vaga)) {
            System.out.println("A vaga selecionada (" + vaga + ") não está disponível.");
            return;
        }

        try {
            Conexao connection = new Conexao();
            PreparedStatement prepared_statement = connection.conectarBD()
                    .prepareStatement(
                            "INSERT INTO estacionamento (nome, carro, placa, vaga, data_entrada, ocupado) VALUES (?, ?, ?, ?, ?)");
            prepared_statement.setString(1, nome);
            prepared_statement.setString(2, carro);
            prepared_statement.setString(3, placa);
            prepared_statement.setString(4, vaga);

            // Obter a data e hora atual
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dataHoraAtual = LocalDateTime.now();
            String horaformatada = dataHoraAtual.format(formatter);

            prepared_statement.setString(5, horaformatada);
            int ocupado = 1;
            prepared_statement.setInt(6, ocupado);

            prepared_statement.executeUpdate();

            prepared_statement.close();

            // Atualiza a tabela após a inserção no banco de dados
            Estacionamento novoEstacionamento = new Estacionamento(nome, carro, placa, vaga, dataHoraAtual.toString());
            dados.add(novoEstacionamento);

            // Limpa os campos de entrada após a adição
            inputNome.clear();
            inputCarro.clear();
            inputplaca.clear();
            vagaLivre.getSelectionModel().clearSelection();
        } catch (Exception e) {
            System.out.println("Erro ao adicionar no banco de dados: " + e.getMessage());

        }

    }

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
                        result_set.getString("vaga"),
                        result_set.getString("data_entrada"));

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
            prepared_statement.setString(5, estacionamento.getPlaca()); // Usa placa para identificar no BD
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
