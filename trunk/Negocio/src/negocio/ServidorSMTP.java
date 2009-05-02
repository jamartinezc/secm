/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio;

/**
 *
 * @author Administrador
 */
public class ServidorSMTP {
    
    private String host;
    private int puerto;
    private boolean usarSSL;
    private String correoDestinatario;
    private char[] contraseña;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public boolean isUsarSSL() {
        return usarSSL;
    }

    public void setUsarSSL(boolean usarSSL) {
        this.usarSSL = usarSSL;
    }

    public String getCorreoDestinatario() {
        return correoDestinatario;
    }

    public void setCorreoDestinatario(String correoDestinatario) {
        this.correoDestinatario = correoDestinatario;
    }

    public char[] getContraseña() {
        return contraseña;
    }

    public void setContraseña(char[] contraseña) {
        this.contraseña = contraseña;
    }
    
    

}
