package co.edu.uniquindio.proyectofinal.proyectofinal.utils;

import co.edu.uniquindio.proyectofinal.proyectofinal.BilleteraVirtual;
import co.edu.uniquindio.proyectofinal.proyectofinal.model.Billetera;
import co.edu.uniquindio.proyectofinal.proyectofinal.model.Usuario;

public class BilleteraUtils {
    public static Billetera inicializarDatos() {

        Billetera billetera = new Billetera();

        Usuario usuario = new Usuario();
        usuario.setIdUsuario("1");
        usuario.setNombre("Leo");
        usuario.setCorreo("leo@gmail.com");
        usuario.setTelefono("123456789");
        usuario.setDireccion("av. siempre viva");
        usuario.setSaldo("500000");
        billetera.getListaUsuarios().add(usuario);

        usuario = new Usuario();
        usuario.setIdUsuario("2");
        usuario.setNombre("Ana");
        usuario.setCorreo("ana@gmail.com");
        usuario.setTelefono("987654321");
        usuario.setDireccion("calle falsa 123");
        usuario.setSaldo("300000");
        billetera.getListaUsuarios().add(usuario);

        usuario = new Usuario();
        usuario.setIdUsuario("3");
        usuario.setNombre("Carlos");
        usuario.setCorreo("carlos@hotmail.com");
        usuario.setTelefono("456123789");
        usuario.setDireccion("plaza central 4");
        usuario.setSaldo("150000");
        billetera.getListaUsuarios().add(usuario);
        System.out.println("Información de la billetera creada");
        return billetera;

    }

}
