/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio.entidades;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class ListaNoReceptores implements Serializable{
    
    private String[] noReceptor;

    public ListaNoReceptores() {
        this.noReceptor = new String[0];
    }

    public String[] getNoReceptor() {
        return noReceptor;
    }

    public void setNoReceptor(String[] noReceptor) {
        this.noReceptor = noReceptor;
    }

}
