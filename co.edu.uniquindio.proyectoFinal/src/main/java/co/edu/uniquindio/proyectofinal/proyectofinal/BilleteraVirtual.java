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
        FXMLLoader fxmlLoader = new FXMLLoader(BilleteraVirtual.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Billetera Virtual!");
        stage.setScene(scene);
        stage.show();

        // Abrir nuevas instancias de la App
        //abrirNuevaVentana();
        //abrirNuevaVentana();

    }

    public static void main(String[] args) {
        launch();
    }

    public static void abrirNuevaVentana() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BilleteraVirtual.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage nuevoStage = new Stage();
            nuevoStage.setTitle("Billetera Virtual!");
            nuevoStage.setScene(scene);
            nuevoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}