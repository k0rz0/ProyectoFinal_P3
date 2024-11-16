package co.edu.uniquindio.proyectofinal.proyectofinal.controller;

import co.edu.uniquindio.proyectofinal.proyectofinal.controller.services.IModelFactoryService;
import co.edu.uniquindio.proyectofinal.proyectofinal.exceptions.UsuarioException;
import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.CuentaDTO;
import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.dto.UsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyectofinal.mapping.mappers.BilleteraMapper;
import co.edu.uniquindio.proyectofinal.proyectofinal.model.Billetera;
import co.edu.uniquindio.proyectofinal.proyectofinal.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyectofinal.utils.BilleteraUtils;
import co.edu.uniquindio.proyectofinal.proyectofinal.utils.Persistencia;
import javafx.scene.control.Alert;

import java.io.IOException;
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
//1. inicializar datos y luego guardarlo en archivos
        System.out.println("invocación clase singleton");
//        cargarDatosBase();
//      salvarDatosPrueba();

        //2. Cargar los datos de los archivos
        //cargarDatosDesdeArchivos();

        //3. Guardar y Cargar el recurso serializable binario
//		cargarResourceBinario();
//		guardarResourceBinario();

        //4. Guardar y Cargar el recurso serializable XML
//		guardarResourceXML();
//		cargarResourceXML();

        //Siempre se debe verificar si la raiz del recurso es null

        if(billetera == null){
            cargarDatosBase();
            guardarResourceXML();
        }
        registrarAccionesSistema("Inicio de sesión", 1, "inicioSesión");
    }
    private void cargarDatosBase(){
        billetera = BilleteraUtils.inicializarDatos();
    }
    public Billetera getBilletera() {
        return billetera;
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
                registrarAccionesSistema("Usuario agregado: "+ usuario.getNombre(),1,"agregarUsuario");
            }
            return true;
        }catch (UsuarioException e){
            mostrarMensaje("Notificación Usuario", "Error al crear el usuario",  e.getMessage(), Alert.AlertType.ERROR);
            registrarAccionesSistema(e.getMessage(),3,"agregarUsuario");
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
                registrarAccionesSistema("Usuario actualizado: "+ usuario.getNombre(),1,"actualizarUsuario");
            }
            return true;
        }catch (Exception e){
            mostrarMensaje("Notificación Usuario", "Error al actualizar el usuario",  e.getMessage(), Alert.AlertType.ERROR);
            registrarAccionesSistema(e.getMessage(),3,"actualizarUsuario");
            return false;
        }
    }

    @Override
    public boolean eliminarUsuario(UsuarioDTO usuarioDTO) {
        try {
            if (!billetera.verificarUsuarioExistenteEliminar(usuarioDTO.idUsuario())) {
                borrarUsuario(usuarioDTO.idUsuario());
                registrarAccionesSistema("Usuario eliminado: "+ usuarioDTO.nombre(),1,"eliminarUsuario");
            }
            return true;
        }catch (Exception e){
            mostrarMensaje("Notificación Usuario", "Error al eliminar el usuario",  e.getMessage(), Alert.AlertType.ERROR);
            registrarAccionesSistema(e.getMessage(),3,"eliminarUsuario");
            return false;
        }
    }
    private void borrarUsuario(String idUsuario) {
        billetera.getListaUsuarios().removeIf(u -> u.getIdUsuario().equals(idUsuario));
    }
    private void salvarDatosPrueba() {
        try {
            Persistencia.guardarUsuarios(getBilletera().getListaUsuarios());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void cargarDatosDesdeArchivos() {
        billetera = new Billetera();
        try {
            Persistencia.cargarDatosArchivos(billetera);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void cargarResourceXML() {
        billetera = Persistencia.cargarRecursoBilleteraXML();
    }
    private void guardarResourceXML() {
        Persistencia.guardarRecursoBilleteraXML(billetera);
    }
    private void cargarResourceBinario() {
        billetera = Persistencia.cargarRecursoBilleteraBinario();
    }

    private void guardarResourceBinario() {
        Persistencia.guardarRecursoBilleteraBinario(billetera);
    }
    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }
    public int validation(String user, String password) {
        return Persistencia.validation(user,password);
    }

    public List<CuentaDTO> obtenerCuentas() {
        return mapper.getCuentaDTO(billetera.getListaCuentas());
    }
}
