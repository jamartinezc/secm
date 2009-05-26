
package negocio.entidades;

import accesodatos.frontera.conectoraacorreo.ConectoraACorreo;
import java.io.Serializable;
import java.util.Arrays;
import negocio.entidades.chequeonoreceptores.ServidorDeEntrada;

/**
 *
 * @author Administrador
 */
public class ListaNoReceptores implements Serializable{
    
    private String[] noReceptores;
    private String fraseDeEliminacion;
    private ConectoraACorreo servidorDeEntrada;//TODO agregarlo

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

    public String getFraseDeEliminacion() {
        return fraseDeEliminacion;
    }

    public ConectoraACorreo getServidorDeEntrada() {
        return servidorDeEntrada;
    }
    
}
