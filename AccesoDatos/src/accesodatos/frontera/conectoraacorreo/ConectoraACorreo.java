
package accesodatos.frontera.conectoraacorreo;

import javax.mail.NoSuchProviderException;
import javax.mail.Store;

/**
 *
 * @author Jaguar
 */
public interface ConectoraACorreo {

    public Store conectar() throws NoSuchProviderException;

    public void setHost(String host);
    public void setPuerto(int puerto);
    public void setUsuario(String usuario);
    public void setContrasena(String contrasena);
    public void setUsarSSL(boolean usarSSL);
}
