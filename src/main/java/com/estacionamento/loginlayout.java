package com.estacionamento;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.estacionamento.DAO.Conexao;

public class loginlayout {

    @FXML
    private Button botaoLogin;

    @FXML
    private TextField campoSenha;

    @FXML
    private TextField campoUsuario;

    @FXML
    private Text logoTexto;

    @FXML
    private Label testeTeste;

    @FXML
    void confirmarDados(ActionEvent event) {

        String nome_usuario = campoUsuario.getText();
        String senha_usuario = campoSenha.getText();
        Usuario user = new Usuario();

        user.setNome_usuario(nome_usuario);
        user.setSenha_usuario(senha_usuario);
        if (campoUsuario.getText().isBlank() == false && campoSenha.getText().isBlank() == false) {
            validarLogin();

        } else {
            testeTeste.setText("Cheque o nome de usuário e senha!");
        }

    }

    private void validarLogin() {
        String nome_usuario = campoUsuario.getText();
        String senha_usuario = campoSenha.getText();

        try {
            Conexao connection = new Conexao();
            PreparedStatement prepared_statement = connection.conectarBD()
                    .prepareStatement("select * from usuario where nome_usuario =? and senha_usuario = ?");
            prepared_statement.setString(1, nome_usuario);
            prepared_statement.setString(2, senha_usuario);
            ResultSet result_set = prepared_statement.executeQuery();

            if (result_set.next()) {

                entrarApp();

            } else {
                testeTeste.setText("Cheque o nome de usuário e senha! ");
            }
            result_set.close();
            prepared_statement.close();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    private void entrarApp() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainlayout.fxml"));
        Parent root = loader.load();
        Stage mainStage = new Stage();
        mainStage.setScene(new Scene(root));
        mainStage.show();
        botaoLogin.getScene().getWindow().hide();
    };
}
