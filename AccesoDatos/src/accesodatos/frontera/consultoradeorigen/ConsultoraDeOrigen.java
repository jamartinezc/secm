
package accesodatos.frontera.consultoradeorigen;

/**
 *
 * @author Jorge A. Martinez
 */
public interface ConsultoraDeOrigen {

    public boolean abrir(String ruta);

    public String[] consultarColumnasDisponibles();

    /**
     * consulta el origen de los datos
     * @param columnas
     * @return
     */
    public String[][] consultarDatos(String[] columnas);
        
}
