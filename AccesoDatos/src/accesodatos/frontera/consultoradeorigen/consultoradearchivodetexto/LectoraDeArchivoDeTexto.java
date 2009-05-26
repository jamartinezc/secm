/*

 */

package accesodatos.frontera.consultoradeorigen.consultoradearchivodetexto;

import java.io.BufferedReader;
import java.io.Serializable;

/**
 *
 * @author Jorge A. Martínez
 */
public interface LectoraDeArchivoDeTexto extends Serializable{


    public String[] consultarColumnasDisponibles(BufferedReader archivo);


    public String[][] consultarDatos(BufferedReader archivo, int[] columnas);

}
