
package negocio.control;


import accesodatos.frontera.DriverCorreo;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import negocio.entidades.Correo;
import negocio.entidades.ListaDeCorreos;
import negocio.entidades.ServidorSMTP;

/**
 *
 * @author Jorge A. Martinez
 */
public class EnviadoraDeCorreos {

    private static EnviadoraDeCorreos instancia;

    /**
     * retorna el objeto instancia de esta clase.
     * @return unica instancia de esta clase.
     */
    public static EnviadoraDeCorreos getInstancia() {
        if(instancia == null){
            instancia = new EnviadoraDeCorreos();
        }
        return instancia;
    }

    private EnviadoraDeCorreos() {
    }

    /**
     * Envía los correos contenidos en una lista de correos por medio de un servidor SMTP
     * @param lista ListaDeCorreos a enviar
     * @return true si el correo fue enviado, false si hubo errores de envio.
     */
    public boolean enviarLista(ListaDeCorreos lista){
        boolean errores = false;
        lista.cargarCorreos();
        LinkedList<Correo> correos = lista.getCorreos();
        Iterator<Correo> correoIt = correos.iterator();

        DriverCorreo driver = DriverCorreo.getInstancia();

        while (correoIt.hasNext()) {
            Correo correo = correoIt.next();
            try {
//                driver.enviarCorreo(correo, lista.getServidorSMTP());
                driver.enviarCorreo(lista.getServidorSMTP().getHost(),
                        lista.getServidorSMTP().getPuerto(),
                        lista.getServidorSMTP().getSSL_FACTORY(),
                        lista.getServidorSMTP().getCorreoRemitente(),
                        lista.getServidorSMTP().getContrasena(),
                        correo.getDestinatariosTO(),
                        correo.getDestinatariosCC(),
                        correo.getDestinatariosBCC(),
                        correo.getAsunto(),
                        correo.getMensaje(),
                        correo.getAdjuntos());
            } catch (MessagingException ex) {
                //TODO registrar errores en el log
                ex.printStackTrace();
                Logger.getLogger(EnviadoraDeCorreos.class.getName()).log(Level.SEVERE, null, ex);
                errores = true;
            }
            
        }
        return !errores;
    }

    public static void main(String[] args) {

        ServidorSMTP serv=new  ServidorSMTP();
        Correo correo  = new Correo();
        LinkedList<Correo> correos = new LinkedList<Correo>();

        serv.setContrasena("AngelaJorgeElias".toCharArray());
        serv.setCorreoRemitente("secm.prueba@gmail.com");
        serv.setHost("smtp.gmail.com");
        serv.setPuerto(465);
        serv.setUsarSSL(true);

        //primer correo
        String[] TO = new String[1];
        TO[0]="jaguar.scratch@gmail.com";
        correo.setDestinatariosTO(TO);
        correo.setAsunto("secm: prueba envío de correo 1");
        correo.setMensaje("Esta es una prueba de envío de correo 1<br>" +
                          "Desde Enviadora <br>" +
                          "<h1>con varios adjuntos</h1>(@)");

        File[] adjuntos = new File[3];
        adjuntos[0]=new File("prueba.properties");
        adjuntos[1]=new File("prueba2.properties");
        adjuntos[2]=new File("jack-the-black-cat-9439.jpg");
        correo.setAdjuntos(adjuntos);

        correos.add(correo);

        //segundo correo
        correo = new Correo();
        TO = new String[1];
        TO[0]="jaguar.scratch@gmail.com";
        correo.setDestinatariosTO(TO);
        correo.setAsunto("secm: prueba envío de correo 2");
        correo.setMensaje("Esta es una prueba de envío de correo 2<br>" +
                          "Desde Enviadora <br>" +
                          "<h1>con varios adjuntos</h1>(@)");

        adjuntos = new File[3];
        adjuntos[0]=new File("prueba.properties");
        adjuntos[1]=new File("prueba2.properties");
        adjuntos[2]=new File("jack-the-black-cat-9439.jpg");
        correo.setAdjuntos(adjuntos);

        correos.add(correo);


         ListaDeCorreos lista = new ListaDeCorreos();

         lista.setCorreos(correos);
         lista.setServidorSMTP(serv);

         EnviadoraDeCorreos.getInstancia().enviarLista(lista);

    }
}
