/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio.control;

import java.util.LinkedList;
import negocio.entidades.ServidorSMTP;

/**
 *
 * @author JORGE
 */
public class ConfiguradoraServidorSMTP {
    
    private LinkedList Servidores;
    
    public boolean CrearServidorSMTP(String host,int puerto, boolean usarSSL, String correoDestinatario, char[] contraseña){
        ServidorSMTP nuevoServidor = new ServidorSMTP();
        nuevoServidor.setContraseña(contraseña);
        nuevoServidor.setCorreoDestinatario(correoDestinatario);
        nuevoServidor.setHost(host);
        nuevoServidor.setPuerto(puerto);
        nuevoServidor.setUsarSSL(usarSSL);
        Servidores.add(nuevoServidor);
        return true;
    }

}
