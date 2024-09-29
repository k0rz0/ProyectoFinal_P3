package co.edu.uniquindio.proyectofinal.proyectofinal.controller;

import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.UsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyectofinal.controller.services.IUsuarioControllerService;

import java.util.List;

public class UsuarioController implements IUsuarioControllerService {

    ModelFactoryController modelFactoryController;

    public UsuarioController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }
    @Override
    public boolean crearUsuario(UsuarioDTO usuarioDTO) {
        return modelFactoryController.crearUsuario(usuarioDTO);
    }

    @Override
    public boolean actualizarUsuario(UsuarioDTO usuarioDTO) {
        return modelFactoryController.actualizarUsuario(usuarioDTO);
    }

    @Override
    public boolean eliminarUsuario(UsuarioDTO usuarioDTO) {
        return modelFactoryController.eliminarUsuario(usuarioDTO);
    }
    public List<UsuarioDTO> obtenerUsuarios() {
        return modelFactoryController.obtenerUsuarios();
    }
}
