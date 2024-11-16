package co.edu.uniquindio.proyectofinal.proyectofinal.controller;

public class LoginController {
    ModelFactoryController modelFactoryController;

    public LoginController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    public int validation(String user, String password) {
        return modelFactoryController.validation(user,password);
    }
}
