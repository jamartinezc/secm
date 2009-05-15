
package negocio.control;

import accesodatos.frontera.DriverBD;
import accesodatos.frontera.consultoradeorigen.ConsultoraDeBD;
import accesodatos.frontera.consultoradeorigen.ConsultoraDeOrigen;
import accesodatos.frontera.consultoradeorigen.factory.ConsultoraDeOrigenFactory;
import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import java.util.Properties;
import negocio.entidades.ListaDeCorreos;
import negocio.entidades.OrigenDeDatos;

/**
 *
 * @author JORGE
 */
public class ServiciosDeCorreo {
    
    public static boolean GuardarSMTP(String host,int puerto, boolean usarSSL, String correoDestinatario, char[] contraseña){
        ConfiguradoraServidorSMTP configuradoraServidorSMTP = ConfiguradoraServidorSMTP.getInstancia();
        configuradoraServidorSMTP.CrearServidorSMTP(host,puerto, usarSSL, correoDestinatario, contraseña);
        return true;
    }

    public static ListaDeCorreos consultarListasDeCorreo(){
        return null;
    }

    public static ConsultoraDeBD consultarBD(String usuario, char[] contraseña, String driver, String protocolo , String direccion, String baseDeDatos){
        
        ConsultoraDeBD cd = new ConsultoraDeBD(driver,protocolo, usuario, String.valueOf(contraseña), baseDeDatos);

        cd.abrir(direccion);

        return cd;
    }

    public static String[] columnasDisponibles(ConsultoraDeOrigen consultora){

        return consultora.consultarColumnasDisponibles();
    }

//    public String[][] consultarDatos(ListaDeCorreos lista, String[] columnas){
//        return lista.getOrigenDeDatos().getComportamientoOrigen().consultarDatos(columnas);
//    }

    /**
     * Crea una ListaDeCorreos nueva, abre el origen.
     * @param origen Determina que tipo de origen de datos se tomará, debe ser tomado de ConsultoraDeOrigenFactory
     * @param datos Properties del origen de datos, estos dependen de que origen se quiere.
     * @return la listaDeCorreo creada.
     */public static ListaDeCorreos crearListaDeCorreos(String nombre, int origen, Properties datos){
        ListaDeCorreos lista = AdministradoraListasDeCorreos.getInstancia().crearListaDeCorreos(nombre, origen, datos);
        OrigenDeDatos origenDatos = lista.getOrigenDeDatos();

        origenDatos.abrir();
//        String[] columnas = origenDatos.getComportamientoOrigen().consultarColumnasDisponibles();
        
        return lista;
    }

     /**
      * Guarda una lista con los campos definidos en columnas
      * @param lista la lista de correos a guardar
      * @param columnas una pareja de tipo {#etiqueta#, columna en origen de datos}.
      * Si la consulta es a una BD el ultimo parámetro debe ser del tipo ("","WHERE"+condicion SQL)
      */
     public static void guardarLista(ListaDeCorreos lista, Properties columnas){
         lista.getOrigenDeDatos().setColumnas(columnas);
         AdministradoraListasDeCorreos.getInstancia().guardar(lista);
     }

    //--------------------------------------------------------------------------
    //funcionamiento casos de uso:
    //--------------------------------------------------------------------------

    private static void EnviarCorreos(){
        
    }
    
    private static void crearCorreosAEnviar(){
        
    }

    private static void ingresarListaDeCorreosBD(){

        Properties datos = new Properties();
        //String driver, String protocolo, String usuarioBD, String contrasenaBD, String baseDeDatos
        datos.setProperty("rutaOrigen", "localhost");
        datos.setProperty("driver", "com.mysql.jdbc.Driver");
        datos.setProperty("protocolo", "mysql://");
        datos.setProperty("usuarioBD", "test");
        datos.setProperty("contrasenaBD", "tset");
        datos.setProperty("baseDeDatos", "test");

        ListaDeCorreos lista = crearListaDeCorreos("listaDos", ConsultoraDeOrigenFactory.BD, datos);

        System.out.println(lista.getNombre());
        System.out.println(lista.getOrigenDeDatos().getOrigen());
        System.out.println(lista.getOrigenDeDatos().getColumnas());

        Properties columnas = new Properties();
        columnas.setProperty("#nombre#", "prueba.nombre");
        columnas.setProperty("WHERE", "where id<=2");
        guardarLista(lista, columnas);
        
    }

    public static void main(String[] args) {
//        ingresarListaDeCorreosBD();
        AdministradoraListasDeCorreos.getInstancia().abrir();
        System.out.println(AdministradoraListasDeCorreos.getInstancia().listas.toString());
        AdministradoraListasDeCorreos.getInstancia().listas.getFirst().getOrigenDeDatos().leerOrigenDeDatos();
    }

}
