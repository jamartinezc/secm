
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
     * consulta el origen de los datos
     * @param columnas
     * @return
     */
    public String[][] consultarDatos(String[] columnas);    
}
