package co.edu.uniquindio.proyectofinal.proyectofinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PruebaRabbit extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BilleteraVirtual.class.getResource("Prueba-Rabbit.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Recibir datos RabbitMQ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
