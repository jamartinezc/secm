/*

 */

package accesodatos.frontera.consultoradeorigen.factory;

import accesodatos.frontera.consultoradeorigen.*;
import java.util.Properties;

/**
 *
 * @author Jorge A. Martínez
 */
public  class ConsultoraDeOrigenFactory {
    public static final int BD=1;
    public static final int ARCHIVO=2;


    public static ConsultoraDeOrigen create(int tipo, Properties datos){
        ConsultoraDeOrigen o;
        switch(tipo){
            case BD:{
                String driver = datos.getProperty("driver");
                String protocolo = datos.getProperty("protocolo");
                String usuarioBD = datos.getProperty("usuarioBD");
                String contrasenaBD = datos.getProperty("contrasenaBD");
                String baseDeDatos = datos.getProperty("baseDeDatos");

                o = new ConsultoraDeBD(driver, protocolo, usuarioBD, contrasenaBD, baseDeDatos);
                break;
            }

            case ARCHIVO:{
                o=null;
            }
            default:{
                o=null;
            }
        }
        return o;
    }

}
