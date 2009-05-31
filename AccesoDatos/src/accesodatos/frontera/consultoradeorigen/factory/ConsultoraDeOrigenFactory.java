/*

 */

package accesodatos.frontera.consultoradeorigen.factory;

import accesodatos.frontera.consultoradeorigen.*;
import accesodatos.frontera.consultoradeorigen.consultoradearchivodetexto.LectoraDeCVS;
import java.util.Properties;

/**
 *
 * @author Jorge A. Martínez
 */
public  class ConsultoraDeOrigenFactory {
    public static final int BD=1;
    public static final int ARCHIVO_CVS=2;


    /**
     * Crea una ConsultoraDeOrigen de la implementación indicada con tipo.
     * @param tipo Debe ser tomada de las constantes de esta clase
     * @param datos Los datos necesarios para crear el origen:<br>
     * <h4>Base De Datos</h4>
      <table border="1" width="1" cellspacing="1">
          <thead>
              <tr>
                  <th>Key</th>
                  <th>Información</th>
              </tr>
          </thead>
          <tbody>
              <tr>
                  <td>driver</td>
                  <td>Clase a usar para el driver de conexión, p.e: com.mysql.jdbc.Driver</td>
              </tr>
              <tr>
                  <td>protocolo</td>
                  <td>Parte de la String de conexión a la base de datos, que va antes de la URL, p.e: mysql://</td>
              </tr>
              <tr>
                  <td>usuarioBD</td>
                  <td>Usuario de la base de datos.</td>
              </tr>
              <tr>
                  <td>contrasenaBD</td>
                  <td>contraseña de la base de datos.</td>
              </tr>
              <tr>
                  <td>baseDeDatos.</td>
                  <td>Nombre de la base de datos en el motor de basede datos, si no aplica (como en OracleXE) dejar vacio.</td>
              </tr>
          </tbody>
      </table>
     * <br>
     * 
     *
     * @return
     */
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

            case ARCHIVO_CVS:{

                LectoraDeCVS lectoraCVS = new LectoraDeCVS();
                String delimitador = datos.getProperty("delimitador", ";");
                lectoraCVS.setDelimitador(delimitador);
                o = new ConsultoraDeArchivoDeTexto(lectoraCVS);

                break;
            }
            default:{
                o=null;
            }
        }
        return o;
    }

}
