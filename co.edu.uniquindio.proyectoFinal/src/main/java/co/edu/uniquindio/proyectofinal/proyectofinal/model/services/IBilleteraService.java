package co.edu.uniquindio.proyectofinal.proyectofinal.model.services;

import co.edu.uniquindio.proyectofinal.proyectofinal.exceptions.CuentaException;
import co.edu.uniquindio.proyectofinal.proyectofinal.exceptions.UsuarioException;
import co.edu.uniquindio.proyectofinal.proyectofinal.model.Usuario;

public interface IBilleteraService {

    public boolean verificarUsuarioExistente(String idUsuario, boolean crear) throws UsuarioException;
    public boolean verificarUsuarioExistenteEliminar(String idUsuario) throws UsuarioException;

    boolean verificarCuentaExistente(String idCuenta, boolean crear) throws CuentaException;

    boolean verificarCuentaExistenteEliminar(String idCuenta) throws CuentaException;
}
