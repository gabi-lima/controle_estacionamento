package com.estacionamento;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("loginpanel"), 1024, 768);
        stage.setScene(scene);
        stage.show();

        /*
         * listar DB
         * ArrayList<Estacionamento> estlista = Mainlayout.listar();
         * for (Estacionamento esta : estlista) {
         * System.out.println("Nome: " + String.valueOf(esta.getNome()));
         * System.out.println("Carro: " + String.valueOf(esta.getCarro()));
         * System.out.println("Placa: " + String.valueOf(esta.getPlaca()));
         * System.out.println("Vaga: " + String.valueOf(esta.getVaga()));
         * }
         */
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}