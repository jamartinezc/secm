/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package frontera;

import java.io.File;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;
import negocio.entidades.Correo;
import negocio.entidades.ServidorSMTP;

/**
 *
 * @author JORGE
 */
public class DriverCorreo {


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
            return new PasswordAuthentication(getCorreo(), getContrasena());
        }

    }

    public void enviarCorreo(Correo correoAenviar, ServidorSMTP servidorDeEnvio){

        String nombreServidorSMTP = servidorDeEnvio.getHost();
        String puertoSMTP = String.valueOf( servidorDeEnvio.getPuerto() );
        String SSLfactory = servidorDeEnvio.getSSL_FACTORY();
        String destinatario = servidorDeEnvio.getCorreoDestinatario();
        char[] contrasenia = servidorDeEnvio.getContraseña();

        Properties paramatrosCorreo = new Properties();
        paramatrosCorreo.put("mail.smtp.host", nombreServidorSMTP);
        paramatrosCorreo.put("mail.smtp.auth", "true");
        paramatrosCorreo.put("mail.debug", "false");
        paramatrosCorreo.put("mail.smtp.port", puertoSMTP);
        paramatrosCorreo.put("mail.smtp.socketFactory.port", puertoSMTP);
        paramatrosCorreo.put("mail.smtp.socketFactory.class", SSLfactory);
        paramatrosCorreo.put("mail.smtp.socketFactory.fallback", "true");
        Autenticadora auth = new Autenticadora();
        auth.setCorreo(destinatario);

        auth.setContrasena(contrasenia);

    }
}
