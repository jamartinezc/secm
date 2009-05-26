
package negocio.control;

import accesodatos.frontera.DriverCorreo;
import accesodatos.frontera.conectoraacorreo.ConectoraACorreo;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import negocio.entidades.ListaDeCorreos;
import negocio.entidades.ListaNoReceptores;

/**
 *
 * @author Jorge A. Martínez
 */
public class TriggerDeNoReceptores implements Runnable{

    private boolean ejecutar;

    @Override
    public void run() {
        ejecutar=false;
        while(ejecutar){
            LinkedList<ListaDeCorreos> listas = AdministradoraListasDeCorreos.getInstancia().getListas();

            Iterator<ListaDeCorreos> it = listas.iterator();
            while (it.hasNext()) {
                ListaDeCorreos lista = it.next();
                ConectoraACorreo servidor = lista.getNoReceptores().getServidorDeEntrada();
                try {

                    String[] correosAEliminar = DriverCorreo.getInstancia().verificarSiExisteCorreoPorAsunto(servidor, lista.getNoReceptores().getFraseDeEliminacion());
                    ListaNoReceptores noReceptores = lista.getNoReceptores();
                    AdministradoraListaNoreceptores.getInstancia().agregarNoReceptores(noReceptores, correosAEliminar);
                    
                } catch (MessagingException ex) {
                    Logger.getLogger(TriggerDeNoReceptores.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("No se pudo verificar el correo: "+servidor);
                }
            }
            try {
                Thread.sleep(24*60*60*1000);//esperar 24 horas entre chequeos
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
