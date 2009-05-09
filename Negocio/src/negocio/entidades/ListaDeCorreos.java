

package negocio.entidades;

import java.util.LinkedList;

/**
 *
 * @author Administrador
 */
public class ListaDeCorreos {
    
    private OrigenDeDatos origenDeDatos;
    private LinkedList<Correo> correos;
    private ListaNoReceptores noReceptores;
    private ServidorSMTP servidorSMTP;

    public ListaDeCorreos(){
        noReceptores = new ListaNoReceptores();
    }

    public LinkedList<Correo> getCorreos() {
        return correos;
    }

    public void setCorreos(LinkedList<Correo> correos) {
        this.correos = correos;
    }

    public ListaNoReceptores getNoReceptores() {
        return noReceptores;
    }

    public void setNoReceptores(ListaNoReceptores noReceptores) {
        this.noReceptores = noReceptores;
    }

    public OrigenDeDatos getOrigenDeDatos() {
        return origenDeDatos;
    }

    public void setOrigenDeDatos(OrigenDeDatos origenDeDatos) {
        this.origenDeDatos = origenDeDatos;
    }

    public ServidorSMTP getServidorSMTP() {
        return servidorSMTP;
    }

    public void setServidorSMTP(ServidorSMTP servidorSMTP) {
        this.servidorSMTP = servidorSMTP;
    }
    

}
