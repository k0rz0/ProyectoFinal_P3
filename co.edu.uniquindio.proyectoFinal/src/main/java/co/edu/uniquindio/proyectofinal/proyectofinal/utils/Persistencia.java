package co.edu.uniquindio.proyectofinal.proyectofinal.utils;

import co.edu.uniquindio.proyectofinal.proyectofinal.model.Billetera;
import co.edu.uniquindio.proyectofinal.proyectofinal.model.Usuario;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Persistencia {
    public static final String RUTA_ARCHIVO_MODELO_BILLETERA_BINARIO = "persistencia/model.dat";
    public static final String RUTA_ARCHIVO_USUARIOS = "persistencia/archivoUsuarios.txt";
    public static final String RUTA_ARCHIVO_LOG = "persistencia/log/BilleteraLog.txt";
    public static final String RUTA_ARCHIVO_MODELO_BILLETERA_XML = "persistencia/model.xml";
    public static final String RUTA_ARCHIVO_DB = "persistencia/db_properties.txt";

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
    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }

    public static int validation(String user, String password) {


        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO_DB))) {
            properties.load(reader);

            // Validar datos de administrador y usuario normal
            if (validateCredentials(properties, "admin", "passwordAdmin", user, password)) {
                return 1;
            }
            if (validateCredentials(properties, "user", "password", user, password)) {
                return 2;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private static boolean validateCredentials(Properties properties, String userKey, String passwordKey, String user, String password) {
        String userData = properties.getProperty(userKey);
        String passwordData = properties.getProperty(passwordKey);

        if (userData != null && passwordData != null) {
            String[] users = userData.split("##");
            String[] passwords = passwordData.split("##");

            for (int i = 0; i < users.length && i < passwords.length; i++) {
                if (users[i].trim().equals(user) && passwords[i].trim().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

}
