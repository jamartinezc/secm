
package negocio.control;

import accesodatos.frontera.DriverBD;
import accesodatos.frontera.consultoradeorigen.ConsultoraDeBD;
import accesodatos.frontera.consultoradeorigen.ConsultoraDeOrigen;
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

    public String[][] consultarDatos(ConsultoraDeOrigen origen, String[] columnas){
        return origen.consultarDatos(columnas);
    }

    //--------------------------------------------------------------------------
    //funcionamiento casos de uso:
    //--------------------------------------------------------------------------

    private void EnviarCorreos(){
        
    }
    
    private void crearCorreosAEnviar(){
        
    }


}
