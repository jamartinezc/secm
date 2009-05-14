
package negocio.control;

import accesodatos.frontera.consultoradeorigen.ConsultoraDeOrigen;
import accesodatos.frontera.consultoradeorigen.factory.ConsultoraDeOrigenFactory;
import java.util.Properties;
import negocio.entidades.ListaDeCorreos;
import negocio.entidades.OrigenDeDatos;


/**
 *
 * @author Jorge A. Martinez
 */
public class AdministradoraListasDeCorreos {

    private static AdministradoraListasDeCorreos instancia;

    public static AdministradoraListasDeCorreos getInstancia() {

        if(instancia == null){
            instancia = new AdministradoraListasDeCorreos();
        }
        return instancia;
    }

    private AdministradoraListasDeCorreos(){
    }

    public ListaDeCorreos crearListaDeCorreos(int origen, Properties datos){
        
        ConsultoraDeOrigen cons = ConsultoraDeOrigenFactory.create(origen, datos);
        ListaDeCorreos lista = new ListaDeCorreos();
        OrigenDeDatos origenDeDatos = new OrigenDeDatos();
        origenDeDatos.setComportamientoOrigen(cons);
        origenDeDatos.setOrigen(datos.getProperty("rutaOrigen"));
        lista.setOrigenDeDatos(origenDeDatos);

        return lista;
    }

    public boolean setColumnas(ListaDeCorreos lista, Properties columnas){
        lista.getOrigenDeDatos().setColumnas(columnas);
        //return guardar(lista);
        return true;
    }
}
