
package negocio.control;

import accesodatos.frontera.conectoraacorreo.ConectoraACorreo;
import accesodatos.frontera.conectoraacorreo.ConectoraAIMAP;
import accesodatos.frontera.conectoraacorreo.conectorafactory.ConectoraFactory;
import accesodatos.frontera.consultoradeorigen.ConsultoraDeBD;
import accesodatos.frontera.consultoradeorigen.ConsultoraDeOrigen;
import accesodatos.frontera.consultoradeorigen.factory.ConsultoraDeOrigenFactory;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import negocio.entidades.ListaDeCorreos;
import negocio.entidades.ListaNoReceptores;
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

    public static void ingresarNoReceptores(ListaDeCorreos lista, String[] noReceptores){
        AdministradoraListasDeCorreos.getInstancia().eliminarRegistrosdeLista(lista, noReceptores);
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
     * @see ConsultoraDeOrigenFactory
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

     public static void eliminarRegistrosdeLista(ListaDeCorreos lista, String[] correosAEliminar){
         AdministradoraListasDeCorreos.getInstancia().eliminarRegistrosdeLista(lista, correosAEliminar);
     }

     /**
      * Ingresa un correo a verificar por correos de noReceptores
      * @param host host del servidor de correo
      * @param puerto puerto del servidor de correo
      * @param usarSSL true - si se quiere usar <br> false - si no se desea usar
      * @param usuario usuario de la cuenta de correo
      * @param contrasena contraseña de la cuenta de correo
      * @param tipo tipo de cuenta de correo, tomar valor de ConectoraFactory
      * @see ConectoraFactory
      * @param fraseDeEliminacion frase que debe tener el asunto si se quiere elminar de la cuenta
      * @param noReceptores ListaDeNoReceptores a la que se quiere ingresar el correo
      */
     public static void ingresarServidorDeEliminacion(String host, String puerto, String usarSSL, String usuario, char[] contrasena, int tipo, String fraseDeEliminacion, ListaDeCorreos lista){
        Properties datos = new Properties();
        datos.setProperty("host", host);
        datos.setProperty("puerto", puerto);
        datos.setProperty("usarSSL", usarSSL);
        datos.setProperty("usuario", usuario);
        datos.setProperty("contrasena", String.valueOf(contrasena));

        AdministradoraListasDeCorreos.getInstancia().ingresarServidorDeEliminacion(datos, tipo, fraseDeEliminacion, lista);
     }
     
    //--------------------------------------------------------------------------
    //funcionamiento casos de uso:
    //--------------------------------------------------------------------------

    private static void EnviarCorreos(){
    }
    
    private static void programarCorreosAEnviar(){
        //Crear la lista
//        Properties datos = new Properties();
//        datos.setProperty("rutaOrigen", "empleados.csv");
//
//        ListaDeCorreos lista = crearListaDeCorreos("listaUno", ConsultoraDeOrigenFactory.ARCHIVO_CVS, datos);
//
//        columnasDisponibles(lista.getOrigenDeDatos().getComportamientoOrigen());
//        Properties columnas = new Properties();
//        columnas.setProperty("#destinatariosTO#", "correo");
//        columnas.setProperty("#nombre#", "nombre");
//        String[] archivos = new String[1];
//        archivos[0]=("jack-the-black-cat-9439.jpg");

        // crear el servidor SMTP
//        ServidorSMTP servidorSMTP=new  ServidorSMTP();
//        servidorSMTP.setContrasena("AngelaJorgeElias".toCharArray());
//        servidorSMTP.setCorreoRemitente("secm.prueba@gmail.com");
//        servidorSMTP.setHost("smtp.gmail.com");
//        servidorSMTP.setPuerto(465);
//        servidorSMTP.setUsarSSL(true);

        ConfiguradoraServidorSMTP.getInstancia().CrearServidorSMTP("smtp.gmail.com", 465, true, "secm2.prueba@gmail.com", "AngelaJorgeElias".toCharArray());
        ServidorSMTP servidorSMTP = ConfiguradoraServidorSMTP.getInstancia().getServidores().getFirst();
        
//        guardarLista(lista, columnas,"prueba desde un .cvs", "<h1>Mensaje desde Servicios de correo</h1><br>va otro con datos tomados de un .cvs<br>nombre:#nombre#", archivos);

        AdministradoraListasDeCorreos.getInstancia().abrir();
        ListaDeCorreos lista = AdministradoraListasDeCorreos.getInstancia().buscar("listaDos");
        Calendar.getInstance().getTime();
        Date fechaEnvio = new Date(Calendar.getInstance().getTime().getTime()+(1000*30));
        ProgramadoraDeEnvios.getInstancia().programarEnvio(lista, servidorSMTP, fechaEnvio, (1000*30));
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

    private static void ingresarListaDeCorreosArchivo(){
        Properties datos = new Properties();
        datos.setProperty("rutaOrigen", "empleados.csv");
        
        ListaDeCorreos lista = crearListaDeCorreos("listaDos", ConsultoraDeOrigenFactory.ARCHIVO_CVS, datos);


        columnasDisponibles(lista.getOrigenDeDatos().getComportamientoOrigen());
        Properties columnas = new Properties();
        columnas.setProperty("#destinatariosTO#", "correo");
        columnas.setProperty("#nombre#", "nombre");
        String[] archivos = new String[1];
        archivos[0]=("jack-the-black-cat-9439.jpg");


        guardarLista(lista, columnas,"pruebas no remitente 2", "<h1>Mensaje desde Servicios de correo</h1><br>Este correo debería llegarle a Angela, pero no a mi<br>Su nombre es:#nombre#", archivos);
        
        System.out.println(lista.getNombre());
        System.out.println(lista.getOrigenDeDatos().getOrigen());
        System.out.println(lista.getOrigenDeDatos().getColumnas());
    }

    public static void main(String[] args) {
//        GuardarSMTP("smtp.gmail.com", 465, true, "secm.prueba@gmail.com", "AngelaJorgeElias".toCharArray());
        AdministradoraListaNoreceptores.getInstancia();
        ProgramadoraDeEnvios.getInstancia();
        System.out.println("INICIO");
//        ingresarListaDeCorreosArchivo();
//        System.out.println(AdministradoraListasDeCorreos.getInstancia().getListas().toString());
//        ServidorSMTP servidorSMTP=new  ServidorSMTP();
//        servidorSMTP.setContrasena("AngelaJorgeElias".toCharArray());
//        servidorSMTP.setCorreoRemitente("secm.prueba@gmail.com");
//        servidorSMTP.setHost("smtp.gmail.com");
//        servidorSMTP.setPuerto(465);
//        servidorSMTP.setUsarSSL(true);
//        ConfiguradoraServidorSMTP.getInstancia().abrir();
//        ServidorSMTP servidorSMTP = ConfiguradoraServidorSMTP.getInstancia().getServidores().getFirst();
//        AdministradoraListasDeCorreos.getInstancia().abrir();
//        AdministradoraListasDeCorreos.getInstancia().enviarLista(AdministradoraListasDeCorreos.getInstancia().getListas().getFirst(), servidorSMTP);


        //ingreso de no receptores a mano:
//        String[] noReceptores = {"violetamf3@hotmail.com"};
//        AdministradoraListasDeCorreos.getInstancia().abrir();
//        ListaDeCorreos lista = AdministradoraListasDeCorreos.getInstancia().getListas().getFirst();
//        ingresarNoReceptores(lista, noReceptores);
//
//        AdministradoraListasDeCorreos.getInstancia().abrir();
//        ListaDeCorreos lista = AdministradoraListasDeCorreos.getInstancia().buscar("listaDos");
//        String usuario = "secm.prueba@gmail.com";
//        String contrasena = "AngelaJorgeElias";
//        String host = "imap.gmail.com";
//        ingresarServidorDeEliminacion(host, "993", "true", usuario, contrasena.toCharArray(), ConectoraFactory.IMAP, "ELIMINAME DE listaDos", lista);


//        programarCorreosAEnviar();
    }

}
