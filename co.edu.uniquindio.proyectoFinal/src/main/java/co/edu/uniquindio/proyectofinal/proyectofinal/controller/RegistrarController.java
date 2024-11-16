 package co.edu.uniquindio.proyectofinal.proyectofinal.controller;

 import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.UsuarioDTO;
 import co.edu.uniquindio.proyectofinal.proyectofinal.model.Usuario;

 public class RegistrarController {
    ModelFactoryController modelFactoryController;
    public RegistrarController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }
    public boolean registrarUsuario(UsuarioDTO usuarioDTO) {
        return modelFactoryController.crearUsuario(usuarioDTO);
    }
}
