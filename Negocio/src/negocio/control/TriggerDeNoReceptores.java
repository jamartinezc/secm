
package negocio.control;

import accesodatos.frontera.DriverCorreo;
import accesodatos.frontera.conectoraacorreo.ConectoraACorreo;
import java.util.Iterator;
import java.util.LinkedList;
import javax.mail.MessagingException;
import negocio.entidades.ListaDeCorreos;

/**
 *
 * @author Jorge A. Martínez
 */
public class TriggerDeNoReceptores implements Runnable{

    private boolean ejecutar;

    @Override
    public void run() {
        ejecutar=true;
        while(ejecutar){
            AdministradoraListasDeCorreos.getInstancia().abrir();
            LinkedList<ListaDeCorreos> listas = AdministradoraListasDeCorreos.getInstancia().getListas();

            Iterator<ListaDeCorreos> it = listas.iterator();
            while (it.hasNext()) {
                ListaDeCorreos lista = it.next();
                ConectoraACorreo servidor = lista.getNoReceptores().getServidorDeEntrada();
                if (servidor != null) {
                    try {
                        System.out.println("Verificando no receptores para: "+lista.getNombre());
                        String[] correosAEliminar = DriverCorreo.getInstancia().verificarSiExisteCorreoPorAsunto(servidor, lista.getNoReceptores().getFraseDeEliminacion());
                        //guardar la lista modificada
                        AdministradoraListasDeCorreos.getInstancia().eliminarRegistrosdeLista(lista, correosAEliminar);

                    } catch (MessagingException ex) {
                        System.out.println("No se pudo verificar el correo: " + servidor);
                    }catch(SecurityException ex){
                        System.out.println("No se pudo verificar el correo: " + servidor);
                    }
                }else{System.out.println(lista.getNombre()+" no tiene servidor");}
            }
            try {
//                Thread.sleep(24*60*60*1000);//esperar 24 horas entre chequeos
                Thread.sleep(30*1000);//para debug
            } catch (InterruptedException ex) {
                System.out.println("Se interrumpió la pausa de revición, revizando por no receptores...");
            }
        }
    }

    public boolean isEjecutar() {
        return ejecutar;
    }

    public void setEjecutar(boolean ejecutar) {
        this.ejecutar = ejecutar;
    }

}
