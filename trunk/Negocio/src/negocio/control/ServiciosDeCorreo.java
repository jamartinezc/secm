
package negocio.control;

import accesodatos.frontera.consultoradeorigen.ConsultoraDeBD;
import accesodatos.frontera.consultoradeorigen.ConsultoraDeOrigen;
import accesodatos.frontera.consultoradeorigen.factory.ConsultoraDeOrigenFactory;
import java.io.File;
import java.util.LinkedList;
import java.util.Properties;
import negocio.entidades.ListaDeCorreos;
import negocio.entidades.OrigenDeDatos;
import negocio.entidades.ServidorSMTP;

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

    public static LinkedList<ListaDeCorreos> consultarListasDeCorreo(){
        return AdministradoraListasDeCorreos.getInstancia().getListas();
    }

    public static LinkedList<ServidorSMTP> consultarServidoresSMTP(){
        return ConfiguradoraServidorSMTP.getInstancia().getServidores();
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
     public static void guardarLista(ListaDeCorreos lista, Properties columnas, String asunto, String mensaje, String[] adjuntos){
         
         File[] archivosAdjuntos = new File[adjuntos.length];
         for(int i=0; i<adjuntos.length;i++){
             archivosAdjuntos[i]=new File(adjuntos[i]);
         }
         AdministradoraListasDeCorreos.getInstancia().setColumnas(lista, columnas, asunto, mensaje, archivosAdjuntos);
         AdministradoraListasDeCorreos.getInstancia().guardar(lista);
     }

     public static void enviarLista(ListaDeCorreos lista, ServidorSMTP servidor){
         AdministradoraListasDeCorreos.getInstancia().enviarLista(lista, servidor);
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


        Properties columnas = new Properties();
        columnas.setProperty("#destinatariosTO#", "correos.correo");
        columnas.setProperty("#nombre#", "prueba.nombre");
        columnas.setProperty("#WHERE#", "id=id_correos AND id<=2");
        String[] archivos = new String[1];
        archivos[0]=("jack-the-black-cat-9439.jpg");
        guardarLista(lista, columnas,"parece que ya", "<h1>Mensaje desde Servicios de correo</h1><br>ya me llegó al mio y nuestros nombres salen en el mensaje, todo desde una BD<br>nombre:#nombre#", archivos);
        System.out.println(lista.getNombre());
        System.out.println(lista.getOrigenDeDatos().getOrigen());
        System.out.println(lista.getOrigenDeDatos().getColumnas());
    }

    public static void main(String[] args) {
        System.out.println("INICIO");
        ingresarListaDeCorreosBD();
        AdministradoraListasDeCorreos.getInstancia().abrir();
        System.out.println(AdministradoraListasDeCorreos.getInstancia().getListas().toString());
        AdministradoraListasDeCorreos.getInstancia().getListas().getFirst().getOrigenDeDatos().leerOrigenDeDatos();

        ServidorSMTP servidorSMTP=new  ServidorSMTP();
        servidorSMTP.setContrasena("AngelaJorgeElias".toCharArray());
        servidorSMTP.setCorreoRemitente("secm.prueba@gmail.com");
        servidorSMTP.setHost("smtp.gmail.com");
        servidorSMTP.setPuerto(465);
        servidorSMTP.setUsarSSL(true);
        AdministradoraListasDeCorreos.getInstancia().getListas().getFirst().setServidorSMTP(servidorSMTP);
        EnviadoraDeCorreos.getInstancia().enviarLista(AdministradoraListasDeCorreos.getInstancia().getListas().getFirst());
    }

}
