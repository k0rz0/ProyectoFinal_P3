package co.edu.uniquindio.proyectofinal.proyectofinal.controller.services;

import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.UsuarioDTO;

public interface IUsuarioControllerService {

    boolean crearUsuario(UsuarioDTO usuarioDTO);

    boolean actualizarUsuario(UsuarioDTO usuarioDTO);

    boolean eliminarUsuario(UsuarioDTO usuarioDTO);
}
