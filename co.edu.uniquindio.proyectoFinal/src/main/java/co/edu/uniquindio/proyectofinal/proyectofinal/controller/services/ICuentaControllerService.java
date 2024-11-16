package co.edu.uniquindio.proyectofinal.proyectofinal.controller.services;

import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.CuentaDTO;
import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.UsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyectofinal.model.Cuenta;

public interface ICuentaControllerService {

    boolean crearCuenta(CuentaDTO cuentaDTO);

    boolean actualizarCuenta(CuentaDTO cuentaDTO);

    boolean eliminarCuenta(CuentaDTO cuentaDTO);
}
