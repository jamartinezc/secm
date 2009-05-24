

package accesodatos.frontera;

import accesodatos.frontera.conectoraacorreo.ConectoraACorreo;
import accesodatos.frontera.drivercorreo.Autenticadora;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.MessagingException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

/**
 *
 * @author JORGE
 */
public class DriverCorreo {

    private static DriverCorreo instancia;

    public static DriverCorreo getInstancia() {
        if(instancia == null){
            instancia = new DriverCorreo();
        }
        return instancia;
    }

    private DriverCorreo() {
    }

    /**
     * 
     * @param nombreServidorSMTP
     * @param puertoSMTP
     * @param SSLfactory
     * @param correoRemitente
     * @param destinatariosTO
     * @param destinatariosCC
     * @param destinatariosBCC
     * @param asunto
     * @param textoMensaje
     * @param adjuntos
     * @param contrasenia
     * @throws javax.mail.MessagingException
     */
    public void enviarCorreo(String nombreServidorSMTP, int puertoSMTP, String SSLfactory, String correoRemitente, char[] contrasenia, String[] destinatariosTO, String[] destinatariosCC, String[] destinatariosBCC, String asunto, String textoMensaje, File[] adjuntos) throws MessagingException{

//        String nombreServidorSMTP = servidorDeEnvio.getHost();
//        String puertoSMTP = String.valueOf( servidorDeEnvio.getPuerto() );
//        String SSLfactory = servidorDeEnvio.getSSL_FACTORY();
//        String destinatario = servidorDeEnvio.getCorreoRemitente();
//        char[] contrasenia = servidorDeEnvio.getContrasena();

        Properties paramatrosCorreo = new Properties();
        paramatrosCorreo.put("mail.smtp.host", nombreServidorSMTP);
        paramatrosCorreo.put("mail.smtp.auth", "true");
        paramatrosCorreo.put("mail.debug", "true");
        paramatrosCorreo.put("mail.smtp.port", String.valueOf(puertoSMTP));
        paramatrosCorreo.put("mail.smtp.socketFactory.port", String.valueOf(puertoSMTP));
        paramatrosCorreo.put("mail.smtp.socketFactory.class", SSLfactory);
        paramatrosCorreo.put("mail.smtp.socketFactory.fallback", "true");
        Autenticadora auth = new Autenticadora();
        auth.setCorreo(correoRemitente);

        auth.setContrasena(contrasenia);
        
        Session sesion = Session.getDefaultInstance(paramatrosCorreo, auth);

        //inicializar el mensaje
        Message mensaje = new MimeMessage(sesion);

        // ingresar los remitentes
        InternetAddress DireccionRemitente = new InternetAddress(correoRemitente);
        mensaje.setFrom(DireccionRemitente);

        //ingresar destinatarios
        InternetAddress[] DestinatariosTO = new InternetAddress[destinatariosTO.length];
		for (int i = 0; i < destinatariosTO.length; i++) {
			DestinatariosTO[i] = new InternetAddress(destinatariosTO[i]);
		}
        mensaje.setRecipients(Message.RecipientType.TO, DestinatariosTO);
        //ingresar destinatarios
        InternetAddress[] DestinatariosCC = new InternetAddress[destinatariosCC.length];
		for (int i = 0; i < destinatariosCC.length; i++) {
			DestinatariosCC[i] = new InternetAddress(destinatariosCC[i]);
		}
        mensaje.setRecipients(Message.RecipientType.CC, DestinatariosCC);
        //ingresar destinatarios
        InternetAddress[] DestinatariosBCC = new InternetAddress[destinatariosBCC.length];
		for (int i = 0; i < destinatariosBCC.length; i++) {
			DestinatariosBCC[i] = new InternetAddress(destinatariosBCC[i]);
		}
        mensaje.setRecipients(Message.RecipientType.BCC, DestinatariosBCC);

        //ingresar el asunto
        mensaje.setSubject(asunto);

        //estructura de un mensaje
        Multipart multiPart = new MimeMultipart("related");//se puede quitar?
        Multipart newMultiPart = new MimeMultipart("alternative");
        BodyPart nestedPart = new MimeBodyPart();
        nestedPart.setContent(newMultiPart);
        multiPart.addBodyPart(nestedPart);

        BodyPart part = new MimeBodyPart();
        part.setText("text message");
        newMultiPart.addBodyPart(part);
        //añadir mensaje a las part
        part = new MimeBodyPart();
        part.setContent(textoMensaje, "text/HTML");
        newMultiPart.addBodyPart(part);

        //adjuntar archivos
        for(int i=0;i<adjuntos.length;i++){
            BodyPart archivosBodyPart = new MimeBodyPart();
            File adjunto = adjuntos[i];
            DataSource fuenteDeArchivos = new FileDataSource(adjunto);
            archivosBodyPart.setDataHandler(new DataHandler(fuenteDeArchivos));
            archivosBodyPart.setFileName(adjunto.getName());
            newMultiPart.addBodyPart(archivosBodyPart);
        }

        //añadir las part al mensaje
        mensaje.setContent(multiPart);
        System.out.println("Pasa por: "+nombreServidorSMTP);
        //enviar el mensaje
        Transport.send(mensaje);
        System.out.println("Enviando correo...");
    }

    /**
     * Busca un correo con el terminoDeBusqueda especificado en el asunto, en la bandeja de entrada del correo especficado por conectora, y retorna la lista de remitentes de los correos encontrados.
     * @param conectora Conectora al correo en el cual se desea buscar
     * @param terminoDeBusqueda Palabra a buscar en el asunto.
     * @return Una lista de destinatarios de los correos encontrados o una lista vacia si no encuentra nada, o una lista con una o mas cadenas vacias, si uno o mas correos encontrados no tienen remitente
     * @throws javax.mail.MessagingException si no se puede buscar en los correos.
     */
    public String[] verificarSiExisteCorreoPorAsunto(ConectoraACorreo conectora, String terminoDeBusqueda) throws MessagingException {

        Store store = conectora.conectar();
        Folder folder = store.getFolder("inbox");
        Message[] mensajes = folder.search(new SubjectTerm(terminoDeBusqueda));
        String[] resultados = new String[mensajes.length];
        for (int i = 0; i < mensajes.length; i++) {
            Message mensaje = mensajes[i];
            Address[] from = mensaje.getFrom();
            if(from != null){
                if(from.length>=1){
                    resultados[i] = from[0].toString();
                }else{
                    resultados[i] = "";
                }
            }else{
                resultados[i] = "";
            }
        }

        try {
            folder.close(true);
            store.close();
        } catch (MessagingException ex) {//el usuario del metodo no tiene porque manejar el hecho que no se puedan cerrar.
            Logger.getLogger(DriverCorreo.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
            return resultados;
    }

    public static void main(String[] args) {
//        ServidorSMTP serv=new  ServidorSMTP();
//        Correo correo  = new Correo();
        DriverCorreo dc = getInstancia();

        char[] contrasenia = ("AngelaJorgeElias".toCharArray());
        String correoRemitente = ("secm.prueba@gmail.com");
        String nombreServidorSMTP = ("smtp.gmail.com");
        int puertoSMTP = (465);

        String[] TO = new String[1];
        TO[0]="jaguar.scratch@gmail.com";
        String[] CC = new String[0];
        String asunto = ("secm: prueba envío de correo");
        String textoMensaje = ("Esta es una prueba de envío de correo <br>" +
                          "<h1>con varios adjuntos</h1>(@)");

        File[] adjuntos = new File[3];
        adjuntos[0]=new File("prueba.properties");
        adjuntos[1]=new File("prueba2.properties");
        adjuntos[2]=new File("jack-the-black-cat-9439.jpg");

        try {
            dc.enviarCorreo(nombreServidorSMTP, puertoSMTP, "javax.net.ssl.SSLSocketFactory", correoRemitente, contrasenia, TO, CC, CC, asunto, textoMensaje, adjuntos);
        } catch (AddressException ex) {
            System.out.println("no existe la direccion");
            Logger.getLogger(DriverCorreo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            System.out.println("error desconocido: ");
            ex.printStackTrace();
            Logger.getLogger(DriverCorreo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
