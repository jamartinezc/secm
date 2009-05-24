/*

 */

package accesodatos.frontera.drivercorreo;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Autenticadora extends Authenticator{
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
