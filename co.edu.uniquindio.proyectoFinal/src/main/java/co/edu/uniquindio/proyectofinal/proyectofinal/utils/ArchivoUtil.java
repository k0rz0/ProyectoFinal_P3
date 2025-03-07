package co.edu.uniquindio.proyectofinal.proyectofinal.utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Esta clase teine metodo estaticos que permite usarlos sin crear instancias de la clase
 * Lo que se hizo fue crear esta libreria para el manejo de los archivos
 * @author Admin
 *
 */
public class ArchivoUtil {

    static String fechaSistema = "";
    /**
     * Este metodo recibe una cadena con el contenido que se quiere guardar en el archivo
     * @param ruta es la ruta o path donde esta ubicado el archivo
     * @throws IOException
     */
    public static void guardarArchivo(String ruta, String contenido, boolean flagAnexarContenido) throws IOException {
        // Uso de try-with-resources para garantizar el cierre automático
        try (FileWriter fw = new FileWriter(ruta, flagAnexarContenido);
             BufferedWriter bfw = new BufferedWriter(fw)) {

            // Escribimos el contenido en el archivo
            bfw.write(contenido);
        }
        // No es necesario cerrar manualmente los recursos dentro del try-with-resources
    }


    /**
     * ESte metodo retorna el contendio del archivo ubicado en una ruta,con la lista de cadenas.
     * @param ruta
     * @return
     * @throws IOException
     */
    public static ArrayList<String> leerArchivo(String ruta) throws IOException {

        ArrayList<String>  contenido = new ArrayList<String>();
        FileReader fr=new FileReader(ruta);
        BufferedReader bfr=new BufferedReader(fr);
        String linea="";
        while((linea = bfr.readLine())!=null)
        {
            contenido.add(linea);
        }
        bfr.close();
        fr.close();
        return contenido;
    }

    private static Logger LOGGER = Logger.getLogger(ArchivoUtil.class.getName());
    public static void guardarRegistroLog(String mensajeLog, int nivel, String accion, String rutaArchivo) {
        cargarFechaSistema();
        try {
            FileHandler fileHandler = new FileHandler(rutaArchivo, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            Level logLevel = switch (nivel) {
                case 1 -> Level.INFO;
                case 2 -> Level.WARNING;
                case 3 -> Level.SEVERE;
                default -> Level.INFO;
            };
            LOGGER.log(logLevel, String.format("%s,%s,%s", accion, mensajeLog, fechaSistema));

            fileHandler.close(); // Cierra solo este manejador, no afecta otros del Logger.
        } catch (IOException | SecurityException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private static void cargarFechaSistema() {
        fechaSistema = java.time.LocalDate.now().toString();
    }




    //------------------------------------SERIALIZACIÓN  y XML
    /**
     * Escribe en el fichero que se le pasa el objeto que se le envia
     *
     * @param rutaArchivo
     *            path del fichero que se quiere escribir
     * @throws IOException
     */

    @SuppressWarnings("unchecked")
    public static Object cargarRecursoSerializado(String rutaArchivo)throws Exception
    {
        Object aux = null;
//		Empresa empresa = null;
        ObjectInputStream ois = null;
        try {
            // Se crea un ObjectInputStream
            ois = new ObjectInputStream(new FileInputStream(rutaArchivo));

            aux = ois.readObject();

        } catch (Exception e2) {
            throw e2;
        } finally {
            if (ois != null)
                ois.close();
        }
        return aux;
    }


    public static void salvarRecursoSerializado(String rutaArchivo, Object object)	throws Exception {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo));
            oos.writeObject(object);
        } catch (Exception e) {
            throw e;
        } finally {
            if (oos != null)
                oos.close();
        }
    }




    public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {

        XMLDecoder decodificadorXML;
        Object objetoXML;

        decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
        objetoXML = decodificadorXML.readObject();
        decodificadorXML.close();
        return objetoXML;

    }

    public static void salvarRecursoSerializadoXML(String rutaArchivo, Object objeto) throws IOException {

        XMLEncoder codificadorXML;

        codificadorXML = new XMLEncoder(new FileOutputStream(rutaArchivo));
        codificadorXML.writeObject(objeto);
        codificadorXML.close();

    }




}