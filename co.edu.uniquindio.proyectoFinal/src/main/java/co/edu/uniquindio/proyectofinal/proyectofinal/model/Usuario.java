package co.edu.uniquindio.proyectofinal.proyectofinal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idUsuario;
    private String nombre;
    private String correo;
    private String telefono;
    private String direccion;
    private String Saldo;
    /*private ArrayList<Cuenta> Cuentas = new ArrayList<>();*/

    public Usuario() {
    }

    public Usuario(String idUsuario, String saldo, String direccion, String telefono, String correo, String nombre) {
        this.idUsuario = idUsuario;
        Saldo = saldo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.nombre = nombre;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSaldo() {
        return Saldo;
    }

    public void setSaldo(String saldo) {
        Saldo = saldo;
    }
}
