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
        return modelFactoryController.crearCuenta(cuentaDTO);
    }

    @Override
    public boolean actualizarCuenta(CuentaDTO cuentaDTO) {
        return modelFactoryController.actualizarCuenta(cuentaDTO);
    }

    @Override
    public boolean eliminarCuenta(CuentaDTO cuentaDTO) {
        return modelFactoryController.eliminarCuenta(cuentaDTO);
    }

    public List<CuentaDTO> obtenerCuentas() {
        return modelFactoryController.obtenerCuentas();
    }
}
