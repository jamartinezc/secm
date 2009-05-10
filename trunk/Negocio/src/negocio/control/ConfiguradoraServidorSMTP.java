
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
    
    public boolean CrearServidorSMTP(String host,int puerto, boolean usarSSL, String correoRemitente, char[] contraseña){
        ServidorSMTP nuevoServidor = new ServidorSMTP();
        nuevoServidor.setContrasena(contraseña);
        nuevoServidor.setCorreoRemitente(correoRemitente);
        nuevoServidor.setHost(host);
        nuevoServidor.setPuerto(puerto);
        nuevoServidor.setUsarSSL(usarSSL);
        nuevoServidor.guardar();
        servidores.add(nuevoServidor);
        return true;
    }

}
