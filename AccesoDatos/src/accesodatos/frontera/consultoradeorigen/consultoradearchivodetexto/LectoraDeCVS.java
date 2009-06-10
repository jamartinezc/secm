/*

 */

package accesodatos.frontera.consultoradeorigen.consultoradearchivodetexto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Jorge A. Martínez
 */
public class LectoraDeCVS implements LectoraDeArchivoDeTexto, Serializable{

    private String delimitador;

    public LectoraDeCVS(){
        delimitador=";";
    }

    public String getDelimitador() {
        return delimitador;
    }

    public void setDelimitador(String delimitador) {
        this.delimitador = delimitador;
    }

    @Override
    public String[] consultarColumnasDisponibles(BufferedReader archivo) {

        try {
            String columnas=archivo.readLine();
            String[] columnasDisponibles = columnas.split(delimitador);
            return columnasDisponibles;
        } catch (IOException ex) {
            return null;
        }

    }

    /**
     * Retorna los datos solicitados por columnas
     * @param archivo BufferedReader del cual se quieren sacar los datos
     * @param columnas Las columnas solicitadas, en el orden que se requieren
     * @return Los datos solocitados. Si la columna solicitada no es válida
     * esa columna tendrá como dato una cadena vacia, si ocurre un error de
     * lectura se retorna <b>null</b>.
     */
    @Override
    public String[][] consultarDatos(BufferedReader archivo, int[] columnas) {
        LinkedList<String[]> listaDatos = new LinkedList<String[]>();
        String lineaDatos;

        try {
            while ((lineaDatos = archivo.readLine()) != null) {
                String[] registroListaDatos;
                String[] registro = lineaDatos.split(delimitador);
                registroListaDatos = new String[columnas.length];
                Arrays.fill(registroListaDatos, "");
                for (int i = 0; i < columnas.length; i++) {
                    int col = columnas[i];
                    try {
                        registroListaDatos[i] = registro[col];
                    } catch (IndexOutOfBoundsException ex) {
                        registroListaDatos[i] = "";
                    }
                }
                listaDatos.add(registroListaDatos);
            }
        } catch (IOException ex) {
            return null;
        }
        return listaDatos.toArray(new String[0][]);
    }
}
