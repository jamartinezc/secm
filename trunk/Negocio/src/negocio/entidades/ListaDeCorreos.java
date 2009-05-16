

package negocio.entidades;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Administrador
 */
public class ListaDeCorreos implements Serializable{

    private String nombre;
    private OrigenDeDatos origenDeDatos;
    private LinkedList<Correo> correos;
    private ListaNoReceptores noReceptores;
    private ServidorSMTP servidorSMTP;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ListaDeCorreos(){
        noReceptores = new ListaNoReceptores();
    }

    public LinkedList<Correo> getCorreos() {
        return correos;
    }

    public void setCorreos(LinkedList<Correo> correos) {
        this.correos = correos;
    }
    
    public void cargarCorreos(){
        correos = new LinkedList<Correo>();
        Correo[] aCargar = origenDeDatos.leerOrigenDeDatos();
        for (int i = 0; i < aCargar.length; i++) {
            correos.add(aCargar[i]);
        }
    }

    public ListaNoReceptores getNoReceptores(){
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

    public String toString(){
        return "{"+nombre+origenDeDatos+"}";
    }
    

}
