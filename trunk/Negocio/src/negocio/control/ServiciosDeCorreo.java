
package negocio.control;

import accesodatos.frontera.DriverBD;
import accesodatos.frontera.consultoradeorigen.ConsultoraDeBD;
import accesodatos.frontera.consultoradeorigen.ConsultoraDeOrigen;
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

    public String[] columnasDisponibles(ConsultoraDeOrigen consultora){

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
     */public ListaDeCorreos crearListaDeCorreos(int origen, Properties datos){
        ListaDeCorreos lista = AdministradoraListasDeCorreos.getInstancia().crearListaDeCorreos(origen, datos);
        OrigenDeDatos origenDatos = lista.getOrigenDeDatos();

        origenDatos.abrir();
//        String[] columnas = origenDatos.getComportamientoOrigen().consultarColumnasDisponibles();
        
        return lista;
    }

     /**
      * Guarda una lista con los campos definidos en columnas
      * @param lista la lista de correos a guardar
      * @param columnas una pareja de tipo {etiqueta, columna en origen de datos}.
      */
     public void guardarLista(ListaDeCorreos lista, Properties columnas){
         
     }

    //--------------------------------------------------------------------------
    //funcionamiento casos de uso:
    //--------------------------------------------------------------------------

    private void EnviarCorreos(){
        
    }
    
    private void crearCorreosAEnviar(){
        
    }


}
