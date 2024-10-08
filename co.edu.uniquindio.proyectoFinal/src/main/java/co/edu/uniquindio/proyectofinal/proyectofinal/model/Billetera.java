package co.edu.uniquindio.proyectofinal.proyectofinal.model;

import co.edu.uniquindio.proyectofinal.proyectofinal.exceptions.UsuarioException;
import co.edu.uniquindio.proyectofinal.proyectofinal.model.services.IBilleteraService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Billetera  implements IBilleteraService, Serializable {

    private static final long serialVersionUID = 1L;
    private List<Usuario> listaUsuarios = new ArrayList<>();
    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    public void setUsuarios(List<Usuario> usuarios) {
        this.listaUsuarios = usuarios;
    }

    @Override
    public boolean verificarUsuarioExistente(String idUsuario, boolean crear) throws UsuarioException {
        if(usuarioExiste(idUsuario) && crear){
            throw new UsuarioException("El usuario con el id: " + idUsuario + " ya existe");
        }else if(!usuarioExiste(idUsuario) && !crear){
            throw new UsuarioException("El usuario con el id: " + idUsuario + " no existe");
        }else{
            return false;
        }
    }
    @Override
    public boolean verificarUsuarioExistenteEliminar(String idUsuario) throws UsuarioException {
        if(!usuarioExiste(idUsuario)){
            throw new UsuarioException("El usuario con el id: " + idUsuario + " no existe");
        }else{
            return false;
        }
    }

    private boolean usuarioExiste(String idUsuario) {
        boolean usuarioEncontrado = false;

        for(Usuario usuario : getListaUsuarios()){
            if (usuario.getIdUsuario().equalsIgnoreCase(idUsuario)){
                usuarioEncontrado = true;
                break;
            }
        }
       return usuarioEncontrado;
    }
}
