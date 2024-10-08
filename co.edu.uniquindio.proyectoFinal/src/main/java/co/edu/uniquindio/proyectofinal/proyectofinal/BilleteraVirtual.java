package co.edu.uniquindio.proyectofinal.proyectofinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;

public class BilleteraVirtual extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BilleteraVirtual.class.getResource("crud-usuario-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Billetera Virtual!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}