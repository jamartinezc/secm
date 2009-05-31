
package negocio.entidades;

import accesodatos.frontera.conectoraacorreo.ConectoraACorreo;
import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author Administrador
 */
public class ListaNoReceptores implements Serializable{
    
    private String[] noReceptores;
    private String fraseDeEliminacion;
    private ConectoraACorreo servidorDeEntrada;

    public ListaNoReceptores() {
        this.noReceptores = new String[0];
    }

    public boolean buscarNoReceptor(String correoNoReceptor) {
        for (int i = 0; i < noReceptores.length; i++) {
            if(noReceptores[i].equals(correoNoReceptor)){
                return true;
            }
        }
        return false;
    }

    public void agregarNoReceptor(String noReceptor) {
        String[] nuevaLista = Arrays.copyOf(noReceptores, noReceptores.length+1);
        nuevaLista[nuevaLista.length-1]=noReceptor;
        noReceptores=nuevaLista;
    }

    public int cantidad(){
        if(noReceptores == null){
            return 0;
        }
        return noReceptores.length;
    }

    public void setServidorDeEntrada(ConectoraACorreo servidorDeEntrada, String asuntoDeEliminacion) {
        this.servidorDeEntrada = servidorDeEntrada;
        fraseDeEliminacion = asuntoDeEliminacion;
    }

    public void setFraseDeEliminacion(String fraseDeEliminacion) {
        this.fraseDeEliminacion = fraseDeEliminacion;
    }

    public void setServidorDeEntrada(ConectoraACorreo servidorDeEntrada) {
        this.servidorDeEntrada = servidorDeEntrada;
    }

    public String getFraseDeEliminacion() {
        return fraseDeEliminacion;
    }

    public ConectoraACorreo getServidorDeEntrada() {
        return servidorDeEntrada;
    }

    public String[] getNoReceptores() {
        return noReceptores;
    }

    public void setNoReceptores(String[] noReceptores) {
        this.noReceptores = noReceptores;
    }
}
