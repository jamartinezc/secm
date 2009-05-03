/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio.control;

/**
 *
 * @author JORGE
 */
public class ServiciosDeCorreo {
    
    private ConfiguradoraServidorSMTP configuradorasServidorSMPT;
    
    public boolean GuardarSMTP(String host,int puerto, boolean usarSSL, String correoDestinatario, char[] contraseña){
        configuradorasServidorSMPT.CrearServidorSMTP(host,puerto, usarSSL, correoDestinatario, contraseña);
        return true;
    }

}
