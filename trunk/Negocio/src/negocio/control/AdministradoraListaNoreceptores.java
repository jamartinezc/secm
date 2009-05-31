/*

 */

package negocio.control;

import negocio.entidades.ListaNoReceptores;

/**
 *
 * @author Jorge A. Martínez
 */
public class AdministradoraListaNoreceptores {

    private Thread triggerChequeo;

    private static AdministradoraListaNoreceptores instancia;

    public static AdministradoraListaNoreceptores getInstancia() {
        
        if(instancia == null){
            instancia = new AdministradoraListaNoreceptores();
        }
        return instancia;
    }

    private AdministradoraListaNoreceptores(){

        triggerChequeo = new Thread(new TriggerDeNoReceptores());
        triggerChequeo.start();
    }

    public ListaNoReceptores crearListaNoReceptores(){
        
        ListaNoReceptores nuevaLista = new ListaNoReceptores();
        return nuevaLista;
    }

    public void agregarNoReceptores(ListaNoReceptores listaNoReceptores, String[] correos){

        for (int i = 0; i < correos.length; i++) {
            if( ! correos[i].equals("") ){
                System.out.println("Eliminando a:"+correos[i]);
                listaNoReceptores.agregarNoReceptor(correos[i]);
            }
        }
    }

    public boolean contiene(ListaNoReceptores listaNoReceptores, String[] correos){

        for (int i = 0; i < correos.length; i++) {
            String correo = correos[i];
            if(listaNoReceptores.buscarNoReceptor(correo)){
                return true;
            }
        }
        return false;
    }
}
