
package accesodatos.frontera.consultoradeorigen;

import java.io.Serializable;

/**
 *
 * @author Jorge A. Martinez
 */
public interface ConsultoraDeOrigen extends Serializable {

    public boolean abrir(String ruta);

    public String[] consultarColumnasDisponibles();

    /**
     * consulta el origen de los datos.
     * @param columnas Las columnas a consultar.
     * @return los datos solicitados. Si no se puede leer el orígen, se retorna null.
     */
    public String[][] consultarDatos(String[] columnas);    
}
