
package negocio.control;

import java.util.LinkedList;
import negocio.entidades.ServidorSMTP;

/**
 *
 * @author JORGE
 */
public class ConfiguradoraServidorSMTP {
    
    private static ConfiguradoraServidorSMTP instancia;
    private LinkedList servidores;

    private ConfiguradoraServidorSMTP(){
        servidores = new LinkedList();
    }

    public static ConfiguradoraServidorSMTP getInstancia() {
        if(instancia == null){
            instancia = new ConfiguradoraServidorSMTP();
        }
        return instancia;
    }
    
    public boolean CrearServidorSMTP(String host,int puerto, boolean usarSSL, String correoDestinatario, char[] contraseña){
        ServidorSMTP nuevoServidor = new ServidorSMTP();
        nuevoServidor.setContraseña(contraseña);
        nuevoServidor.setCorreoRemitente(correoDestinatario);
        nuevoServidor.setHost(host);
        nuevoServidor.setPuerto(puerto);
        nuevoServidor.setUsarSSL(usarSSL);
        servidores.add(nuevoServidor);
        return true;
    }

}
