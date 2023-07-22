module com.estacionamento {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.estacionamento to javafx.fxml;

    exports com.estacionamento;
}
