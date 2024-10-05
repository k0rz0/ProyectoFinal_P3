package co.edu.uniquindio.proyectofinal.proyectofinal.utils;

import co.edu.uniquindio.proyectofinal.proyectofinal.model.Billetera;
import co.edu.uniquindio.proyectofinal.proyectofinal.model.Usuario;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {
    public static final String RUTA_ARCHIVO_MODELO_BILLETERA_BINARIO = "persistencia/model.dat";
    public static final String RUTA_ARCHIVO_USUARIOS = "persistencia/archivoUsuarios.txt";
    public static final String RUTA_ARCHIVO_LOG = "persistencia/log/BilleteraLog.txt";
    public static final String RUTA_ARCHIVO_MODELO_BILLETERA_XML = "persistencia/model.xml";

    public static void cargarDatosArchivos(Billetera billetera) throws FileNotFoundException, IOException {
        //cargar archivo de Usuarios
        ArrayList<Usuario> usuariosCargados = cargarUsuarios();
        if(usuariosCargados.size() > 0)
            billetera.getListaUsuarios().addAll(usuariosCargados);

        //cargar archivos empleados

        //cargar archivo transcciones

        //cargar archivo empleados

        //cargar archivo prestamo
    }
    public static void guardarUsuarios(List<Usuario> listaUsuarios) throws IOException {
        String contenido = "";
        for(Usuario usuario:listaUsuarios)
        {
            contenido+= usuario.getIdUsuario()+
                    "@@"+usuario.getNombre()+
                    "@@"+usuario.getCorreo()+
                    "@@"+usuario.getTelefono()+
                    "@@"+usuario.getDireccion()+
                    "@@"+usuario.getSaldo()+"\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_USUARIOS, contenido, false);
    }
    public static ArrayList<Usuario> cargarUsuarios() throws FileNotFoundException, IOException
    {
        ArrayList<Usuario> usuarios =new ArrayList<>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_USUARIOS);
        String linea="";
        for (int i = 0; i < contenido.size(); i++)
        {
            linea = contenido.get(i);//1;juan;uni1@;12454;Armenia;125444
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(linea.split("@@")[0]);
            usuario.setNombre(linea.split("@@")[1]);
            usuario.setCorreo(linea.split("@@")[2]);
            usuario.setTelefono(linea.split("@@")[3]);
            usuario.setDireccion(linea.split("@@")[4]);
            usuario.setSaldo(linea.split("@@")[5]);
            usuarios.add(usuario);
        }
        return usuarios;
    }
    public static Billetera cargarRecursoBilleteraBinario() {

        Billetera billetera = null;

        try {
            billetera = (Billetera)ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_BILLETERA_BINARIO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return billetera;
    }

    public static void guardarRecursoBilleteraBinario(Billetera billetera) {
        try {
            ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_BILLETERA_BINARIO, billetera);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static Billetera cargarRecursoBilleteraXML() {

        Billetera billetera = null;

        try {
            billetera = (Billetera) ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_BILLETERA_XML);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return billetera;

    }
    public static void guardarRecursoBilleteraXML(Billetera billetera) {

        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_BILLETERA_XML, billetera);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion)
    {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }
}
