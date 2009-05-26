
package accesodatos.frontera.conectoraacorreo;

import accesodatos.frontera.drivercorreo.Autenticadora;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author Jorge A. Martínez
 */
public class ConectoraAPOP3 implements ConectoraACorreo{

    private Properties props;
    private Autenticadora auth;

    public ConectoraAPOP3(){
        props = new Properties();
        auth = new Autenticadora();
    }

    @Override
    public Store conectar() throws NoSuchProviderException, MessagingException {
        Session sesion = Session.getDefaultInstance(props, auth);
        Store store=null;
        store = sesion.getStore("pop3");
        store.connect();
        return store;
    }

    @Override
    public void setHost(String host) {
      props.setProperty("mail.pop3.host", host);
    }

    @Override
    public void setUsuario(String usuario) {
      props.setProperty("mail.pop3.user", usuario);
      auth.setCorreo(usuario);
    }

    @Override
    public void setContrasena(String contrasena) {
      auth.setContrasena(contrasena.toCharArray());
    }

    @Override
    public void setUsarSSL(boolean usarSSL) {
      props.setProperty("mail.pop3.ssl.enable", String.valueOf(usarSSL));
      if(usarSSL){
          props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
          String puerto = props.getProperty("mail.pop3.port");
          if(puerto != null){
              props.setProperty("mail.pop3.socketFactory.port", puerto);
          }
      }
      
    }

    @Override
    public void setPuerto(int puerto) {

      props.setProperty("mail.pop3.port", String.valueOf(puerto));

      String SSLhabilitado = props.getProperty("mail.pop3.ssl.enable");
      if(SSLhabilitado != null){
          if(SSLhabilitado.equals("true")){
            props.setProperty("mail.pop3.socketFactory.port", String.valueOf(puerto));
          }
      }
    }

}
