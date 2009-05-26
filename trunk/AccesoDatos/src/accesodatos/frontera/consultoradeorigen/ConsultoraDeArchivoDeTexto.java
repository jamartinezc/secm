/*

 */

package accesodatos.frontera.consultoradeorigen;

import accesodatos.frontera.consultoradeorigen.consultoradearchivodetexto.LectoraDeArchivoDeTexto;
import accesodatos.frontera.consultoradeorigen.consultoradearchivodetexto.LectoraDeCVS;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge A. Martínez
 */
public class ConsultoraDeArchivoDeTexto implements ConsultoraDeOrigen,Serializable{

    transient private BufferedReader archivo;
    private LectoraDeArchivoDeTexto comportamientoLeer;
    transient private boolean leido;
    private String[] columnasDisponibles;

    public ConsultoraDeArchivoDeTexto(LectoraDeArchivoDeTexto lectoraDeArchivoDeTexto){
        comportamientoLeer=lectoraDeArchivoDeTexto;
    }

    public BufferedReader getArchivo() {
        return archivo;
    }

    public void setArchivo(BufferedReader archivo) {
        this.archivo = archivo;
    }

    public String[] getColumnasDisponibles() {
        return columnasDisponibles;
    }

    public void setColumnasDisponibles(String[] columnasDisponibles) {
        this.columnasDisponibles = columnasDisponibles;
    }

    public LectoraDeArchivoDeTexto getComportamientoLeer() {
        return comportamientoLeer;
    }

    public void setComportamientoLeer(LectoraDeArchivoDeTexto comportamientoLeer) {
        this.comportamientoLeer = comportamientoLeer;
    }

    /**
     * Verifica si ya se ha abierto un origen de datos con esta clase, si no entonces lo abre.
     * @param ruta ruta del origen de datos.
     * @return true: Si se pudo abrir el origen (o si estaba abierto)<br>
     *         false: Si si no se ha podido abrir el origen.
     */
    @Override
    public boolean abrir(String ruta) {
        //verificar si ya está abierto el archivo:
        boolean abierto=false;
        if( archivo !=  null ){
            abierto=true;
            try {
                archivo.reset();
            } catch (IOException ex) {
                abierto=false;
            }
        }

        if(!abierto){
            try {
                archivo = new BufferedReader(new FileReader(ruta));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ConsultoraDeArchivoDeTexto.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        leido=false;
        return true;
    }

    /**
     * Lee las columnas disponibles en este archivo, funciona unicamente si no se ha leido nada de este origen y si la primera linea del archivo tiene los nombres de las columnas.
     * @return null Si ya se ha leido algo o si no se puede leer, de lo contrario retorna la lista de los nombres de las columnas.
     */
    @Override
    public String[] consultarColumnasDisponibles() {//TODO eliminar dependencia de este metodo

        if(!leido){
            columnasDisponibles = comportamientoLeer.consultarColumnasDisponibles(archivo);
            return columnasDisponibles;
        }else{
            return null;
        }
    }

    /**
     * Retorna los valores de las columnas solicitadas
     * @param columnas los nombres de las columnas como se indica en el resultado
     * de consultarColumnasDisponibles(), si no se ha llamado a consultarColumnasDisponibles(), se debe
     * indicar los números de colúmna a consultar contando de izquierda a derecha desde cero.
     * Si se solicita una columna inválida se retornará una cadena vacia para ese dato.
     * @return los datos resultantes de la consulta.
     */
    @Override
    public String[][] consultarDatos(String[] columnas) {

        //indicar cuales columnas consultar (en numeros).
        int[] columnasNumero = new int[columnas.length];
        if(columnasDisponibles != null){
            for (int i = 0; i < columnas.length; i++) {
                String columna = columnas[i];
                columnasNumero[i] = -1;
                for (int j = 0; j < columnasDisponibles.length; j++) {
                    String cDisponible = columnasDisponibles[j];
                    if(cDisponible.equals(columna)){
                        columnasNumero[i] = j;
                    }
                }
            }
        }else{
            for (int i = 0; i < columnas.length; i++) {
                try {
                    columnasNumero[i] = Integer.parseInt(columnas[i]);
                } catch (NumberFormatException ex) {
                    columnasNumero[i] = -1;
                }
            }
        }

        String[][] datos = comportamientoLeer.consultarDatos(archivo, columnasNumero);

        if(datos[0][0].equals(columnas[0])){
            datos = Arrays.copyOfRange(datos, 1, datos.length);
        }

        leido=true;
        return datos;
    }


    public static void main(String[] args) {
        ConsultoraDeArchivoDeTexto cons = new ConsultoraDeArchivoDeTexto(new LectoraDeCVS());
        cons.abrir("empleados.csv");
        String[] cols = cons.consultarColumnasDisponibles();

        for (int i = 0; i < cols.length; i++) {
            System.out.print(cols[i]+"  ");
        }
        System.out.println("");
//        String[] colsSelec = Arrays.copyOfRange(cols, 0, 2);
        String[] colsSelec = {"correo","nombre"};
        
        String[][] datos = cons.consultarDatos(colsSelec);

        for (int i = 0; i < datos.length; i++) {
            String[] linea = datos[i];
            for (int j = 0; j < linea.length; j++) {
                System.out.print(linea[j]+"  ");
            }
            System.out.println("");
        }
    }
}
