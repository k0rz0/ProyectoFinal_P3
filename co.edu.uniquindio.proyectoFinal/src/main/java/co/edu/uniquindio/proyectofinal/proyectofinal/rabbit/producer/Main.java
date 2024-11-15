package co.edu.uniquindio.proyectofinal.proyectofinal.rabbit.producer;
import co.edu.uniquindio.proyectofinal.proyectofinal.rabbit.producer.controller.ModelFactoryController;
import static co.edu.uniquindio.proyectofinal.proyectofinal.rabbit.producer.util.Constantes.QUEUE_NUEVA_PUBLICACION;

public class Main {
    public static void main(String[] args) {
        ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
        String mensaje = "";
        mensaje += "100;";
        mensaje += "NUEVO_PRODUCTO";
        modelFactoryController.producirMensaje(QUEUE_NUEVA_PUBLICACION, mensaje);
    }
}