package co.edu.uniquindio.proyectofinal.proyectofinal.rabbit.producer.controller.service;

public interface IModelFactoryService {
    void producirMensaje(String queue, String message);
}
