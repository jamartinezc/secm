

package accesodatos.frontera;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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

    private static class Autenticadora extends Authenticator{
        private char[] contrasena;
        private String correo;

        /**
         * @return the contrasena
         */
        public char[] getContrasena() {
            return contrasena;
        }

        /**
         * @param contrasena the contrasena to set
         */
        public void setContrasena(char[] contrasena) {
            this.contrasena = contrasena;
        }

        /**
         * @return the correo
         */
        public String getCorreo() {
            return correo;
        }

        /**
         * @param correo the correo to set
         */
        public void setCorreo(String correo) {
            this.correo = correo;
        }
        
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(getCorreo(), String.copyValueOf(getContrasena()));
        }

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
        
        //enviar el mensaje
        Transport.send(mensaje);
        System.out.println("Enviando correo...");
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
