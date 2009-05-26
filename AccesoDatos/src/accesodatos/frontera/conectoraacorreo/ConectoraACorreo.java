
package accesodatos.frontera.conectoraacorreo;

import java.io.Serializable;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Store;

/**
 *
 * @author Jaguar
 */
public interface ConectoraACorreo extends Serializable{

    public Store conectar() throws NoSuchProviderException, MessagingException;

    public void setHost(String host);
    public void setPuerto(int puerto);
    public void setUsuario(String usuario);
    public void setContrasena(String contrasena);
    public void setUsarSSL(boolean usarSSL);
}
