/*

 */

package accesodatos.frontera.conectoraacorreo.conectorafactory;

import accesodatos.frontera.conectoraacorreo.ConectoraACorreo;
import accesodatos.frontera.conectoraacorreo.ConectoraAIMAP;
import accesodatos.frontera.conectoraacorreo.ConectoraAPOP3;
import java.util.Properties;

/**
 *
 * @author Jorge A. Martínez
 */
public class ConectoraFactory {

    public static final int POP3 = 1;
    public static final int IMAP = 2;

    public static ConectoraACorreo create(int tipo, Properties datos){
        switch (tipo){
            case POP3:{
                ConectoraAPOP3 servidorDeEntrada = new ConectoraAPOP3();
                servidorDeEntrada.setHost(datos.getProperty("host"));
                servidorDeEntrada.setPuerto(Integer.parseInt(datos.getProperty("puerto")));
                System.out.println("ConectoraFactory.create"+Boolean.parseBoolean(datos.getProperty("usarSSL")));
                servidorDeEntrada.setUsarSSL(Boolean.parseBoolean(datos.getProperty("usarSSL")));
                servidorDeEntrada.setUsuario(datos.getProperty("usuario"));
                servidorDeEntrada.setContrasena(datos.getProperty("contrasena"));

                return servidorDeEntrada;
            }

            case IMAP:{
                ConectoraAIMAP servidorDeEntrada = new ConectoraAIMAP();
                servidorDeEntrada.setHost(datos.getProperty("host"));
                servidorDeEntrada.setPuerto(Integer.parseInt(datos.getProperty("puerto")));
                servidorDeEntrada.setUsarSSL(Boolean.parseBoolean(datos.getProperty("usarSSL")));
                servidorDeEntrada.setUsuario(datos.getProperty("usuario"));
                servidorDeEntrada.setContrasena(datos.getProperty("contrasena"));

                return servidorDeEntrada;
            }

            default:{

                return null;
            }
        }

    }

}
