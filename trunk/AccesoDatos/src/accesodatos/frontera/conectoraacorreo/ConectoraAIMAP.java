
package accesodatos.frontera.conectoraacorreo;

import accesodatos.frontera.drivercorreo.Autenticadora;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author Jorge A. Martínez
 */
public class ConectoraAIMAP implements ConectoraACorreo{

    private Properties props;
    private Autenticadora auth;
    transient private Session sesion;

    public ConectoraAIMAP(){

        props = new Properties();
        auth = new Autenticadora();
    }

    @Override
    public Store conectar() throws NoSuchProviderException, MessagingException {
        
        sesion = Session.getDefaultInstance(props, auth);
        Store store=null;
        store = sesion.getStore("imap");
        System.out.println("props: "+props.toString());
        System.out.println("user: "+auth.getCorreo()+"pass: "+String.valueOf(auth.getContrasena()));
        store.connect();
        return store;
    }

    @Override
    public void setHost(String host) {

      props.setProperty("mail.imap.host", host);
    }

    @Override
    public void setUsuario(String usuario) {

      props.setProperty("mail.imap.user", usuario);
      auth.setCorreo(usuario);
    }

    @Override
    public void setContrasena(String contrasena) {

      auth.setContrasena(contrasena.toCharArray());
    }

    @Override
    public void setPuerto(int puerto) {

      props.setProperty("mail.imap.port", String.valueOf(puerto));
    }

    @Override
    public void setUsarSSL(boolean usarSSL) {
        
      props.setProperty("mail.imap.ssl.enable", String.valueOf(usarSSL));
    }

}
