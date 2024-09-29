package co.edu.uniquindio.proyectofinal.proyectofinal.controller;

import co.edu.uniquindio.proyectofinal.proyectofinal.controller.services.IModelFactoryService;
import co.edu.uniquindio.proyectofinal.proyectofinal.exceptions.UsuarioException;
import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.UsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.mappers.BilleteraMapper;
import co.edu.uniquindio.proyectofinal.proyectofinal.model.Billetera;
import co.edu.uniquindio.proyectofinal.proyectofinal.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyectofinal.utils.BilleteraUtils;
import javafx.scene.control.Alert;

import java.util.List;

public class ModelFactoryController implements IModelFactoryService {

    Billetera billetera;

    BilleteraMapper mapper = BilleteraMapper.INSTANCE;

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }


    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController(){
        cargarDatosBase();
    }
    private void cargarDatosBase(){
        billetera = BilleteraUtils.inicializarDatos();
    }

    public List<UsuarioDTO> obtenerUsuarios() {
        return mapper.getUsuariosDTO(billetera.getListaUsuarios());
    }

    @Override
    public boolean crearUsuario(UsuarioDTO usuarioDTO) {
        try {

            if (!billetera.verificarUsuarioExistente(usuarioDTO.idUsuario(), true)) {
                Usuario usuario = mapper.usuarioDTOToUsuario(usuarioDTO);
                billetera.getListaUsuarios().add(usuario);
            }
            return true;
        }catch (UsuarioException e){
            mostrarMensaje("Notificación Usuario", "Error al crear el usuario",  e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }

    @Override
    public boolean actualizarUsuario(UsuarioDTO usuarioDTO) {
        try {
            if (billetera.verificarUsuarioExistente(usuarioDTO.idUsuario(), false)) {
                Usuario usuario = mapper.usuarioDTOToUsuario(usuarioDTO);
                borrarUsuario(usuarioDTO.idUsuario());
                billetera.getListaUsuarios().add(usuario);
            }
            return true;
        }catch (Exception e){
            mostrarMensaje("Notificación Usuario", "Error al actualizar el usuario",  e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }

    @Override
    public boolean eliminarUsuario(UsuarioDTO usuarioDTO) {
        try {
            if (!billetera.verificarUsuarioExistenteEliminar(usuarioDTO.idUsuario())) {
                borrarUsuario(usuarioDTO.idUsuario());
            }
            return true;
        }catch (Exception e){
            mostrarMensaje("Notificación Usuario", "Error al eliminar el usuario",  e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
    private void borrarUsuario(String idUsuario) {
        billetera.getListaUsuarios().removeIf(u -> u.getIdUsuario().equals(idUsuario));
    }
}
