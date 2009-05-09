
package negocio.control;

import negocio.entidades.ListaDeCorreos;

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



    //--------------------------------------------------------------------------
    //funcionamiento casos de uso:
    //--------------------------------------------------------------------------

    private void EnviarCorreos(){
        
    }
    
    private void crearCorreosAEnviar(){
        
    }


}
