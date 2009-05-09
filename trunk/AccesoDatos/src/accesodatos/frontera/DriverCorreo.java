/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
import negocio.entidades.Correo;
import negocio.entidades.ServidorSMTP;

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

    public void enviarCorreo(Correo correoAenviar, ServidorSMTP servidorDeEnvio) throws MessagingException{

        String nombreServidorSMTP = servidorDeEnvio.getHost();
        String puertoSMTP = String.valueOf( servidorDeEnvio.getPuerto() );
        String SSLfactory = servidorDeEnvio.getSSL_FACTORY();
        String destinatario = servidorDeEnvio.getCorreoRemitente();
        char[] contrasenia = servidorDeEnvio.getContraseña();

        Properties paramatrosCorreo = new Properties();
        paramatrosCorreo.put("mail.smtp.host", nombreServidorSMTP);
        paramatrosCorreo.put("mail.smtp.auth", "true");
        paramatrosCorreo.put("mail.debug", "true");
        paramatrosCorreo.put("mail.smtp.port", puertoSMTP);
        paramatrosCorreo.put("mail.smtp.socketFactory.port", puertoSMTP);
        paramatrosCorreo.put("mail.smtp.socketFactory.class", SSLfactory);
        paramatrosCorreo.put("mail.smtp.socketFactory.fallback", "true");
        Autenticadora auth = new Autenticadora();
        auth.setCorreo(destinatario);

        auth.setContrasena(contrasenia);
        
        Session sesion = Session.getDefaultInstance(paramatrosCorreo, auth);

        //inicializar el mensaje
        Message mensaje = new MimeMessage(sesion);

        // ingresar los remitentes
        InternetAddress DireccionRemitente = new InternetAddress(servidorDeEnvio.getCorreoRemitente());
        mensaje.setFrom(DireccionRemitente);

        //ingresar destinatarios
        InternetAddress[] DestinatariosTO = new InternetAddress[correoAenviar.getDestinatariosTO().length];
		for (int i = 0; i < correoAenviar.getDestinatariosTO().length; i++) {
			DestinatariosTO[i] = new InternetAddress(correoAenviar.getDestinatariosTO()[i]);
		}
        mensaje.setRecipients(Message.RecipientType.TO, DestinatariosTO);
        //ingresar destinatarios
        InternetAddress[] DestinatariosCC = new InternetAddress[correoAenviar.getDestinatariosCC().length];
		for (int i = 0; i < correoAenviar.getDestinatariosCC().length; i++) {
			DestinatariosCC[i] = new InternetAddress(correoAenviar.getDestinatariosCC()[i]);
		}
        mensaje.setRecipients(Message.RecipientType.CC, DestinatariosCC);
        //ingresar destinatarios
        InternetAddress[] DestinatariosBCC = new InternetAddress[correoAenviar.getDestinatariosBCC().length];
		for (int i = 0; i < correoAenviar.getDestinatariosBCC().length; i++) {
			DestinatariosBCC[i] = new InternetAddress(correoAenviar.getDestinatariosBCC()[i]);
		}
        mensaje.setRecipients(Message.RecipientType.BCC, DestinatariosBCC);

        //ingresar el asunto
        mensaje.setSubject(correoAenviar.getAsunto());

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
        part.setContent(correoAenviar.getMensaje(), "text/HTML");
        newMultiPart.addBodyPart(part);

        //adjuntar archivos
        //TODO adjuntar multiples archivos
        for(int i=0;i<correoAenviar.getAdjuntos().length;i++){
            BodyPart archivosBodyPart = new MimeBodyPart();
            File adjunto = correoAenviar.getAdjuntos()[i];
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
        ServidorSMTP serv=new  ServidorSMTP();
        Correo correo  = new Correo();
        DriverCorreo dc = getInstancia();

        serv.setContraseña("AngelaJorgeElias".toCharArray());
        serv.setCorreoRemitente("secm.prueba@gmail.com");
        serv.setHost("smtp.gmail.com");
        serv.setPuerto(465);
        serv.setUsarSSL(true);

        String[] TO = new String[1];
        TO[0]="5g6e5rthg6f5r4@gmail.com";
        String[] CC = new String[1];
        CC[0]="kmf7lfr67une45@gmail.com";
        correo.setDestinatariosTO(TO);
        correo.setDestinatariosCC(CC);
        correo.setAsunto("secm: prueba envío de correo");
        correo.setMensaje("Esta es una prueba de envío de correo <br>" +
                          "<h1>con varios adjuntos</h1>(@)");

        File[] adjuntos = new File[3];
        adjuntos[0]=new File("prueba.properties");
        adjuntos[1]=new File("prueba2.properties");
        adjuntos[2]=new File("jack-the-black-cat-9439.jpg");
        correo.setAdjuntos(adjuntos);

        try {
            dc.enviarCorreo(correo, serv);
        } catch (AddressException ex) {
            System.out.println("no existe la direccion");
            Logger.getLogger(DriverCorreo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            System.out.println("error desconocido: ");
            System.out.println(ex.getCause().getMessage());
            Logger.getLogger(DriverCorreo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
