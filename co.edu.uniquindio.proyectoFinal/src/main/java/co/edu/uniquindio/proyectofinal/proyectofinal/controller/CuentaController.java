package co.edu.uniquindio.proyectofinal.proyectofinal.controller;

import co.edu.uniquindio.proyectofinal.proyectofinal.controller.services.ICuentaControllerService;
import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.CuentaDTO;
import java.util.List;

public class CuentaController implements ICuentaControllerService {
    ModelFactoryController modelFactoryController;

    public CuentaController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }
    @Override
    public boolean crearCuenta(CuentaDTO cuentaDTO) {
        return false;
    }

    @Override
    public boolean actualizarCuenta(CuentaDTO cuentaDTO) {
        return false;
    }

    @Override
    public boolean eliminarCuenta(CuentaDTO cuentaDTO) {
        return false;
    }

    public List<CuentaDTO> obtenerCuentas() {
        return modelFactoryController.obtenerCuentas();
    }
}
