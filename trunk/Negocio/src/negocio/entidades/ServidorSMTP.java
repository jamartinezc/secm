
package negocio.entidades;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class ServidorSMTP implements Serializable{
    
    private String host;
    private int puerto;
    private boolean usarSSL;
    private String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private String correoRemitente;
    private char[] contrasena;

    public ServidorSMTP() {
        host="";
        puerto=-1;
        usarSSL=true;
        SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        correoRemitente = "";
        contrasena=new char[0];
    }

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
    
    public String getSSL_FACTORY() {
        return SSL_FACTORY;
    }
    
    public void setSSL_FACTORY(String SSL_FACTORY) {
        this.SSL_FACTORY = SSL_FACTORY;
    }

    public String getCorreoRemitente() {
        return correoRemitente;
    }

    public void setCorreoRemitente(String correoDestinatario) {
        this.correoRemitente = correoDestinatario;
    }

    public char[] getContrasena() {
        return contrasena;
    }

    public void setContrasena(char[] contrasena) {
        this.contrasena = contrasena;
    }

    
    public boolean guardar(){
        XMLEncoder e;
        int i=1;
        String nombreArchivo="./config"+i+".xml";
        File archivo = new File(nombreArchivo);
        while(archivo.exists()){
            i++;
            nombreArchivo="./config"+i+".xml";
            archivo = new File(nombreArchivo);
        }

        try {
            archivo.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(ServidorSMTP.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        try {
            FileOutputStream fos = new FileOutputStream(nombreArchivo);
            e = new XMLEncoder(new BufferedOutputStream(fos));
            e.writeObject(this);
            e.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServidorSMTP.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    public boolean abrir(String Nombrearchivo){
         try {
            XMLDecoder d = new XMLDecoder(
                               new BufferedInputStream(
                                   new FileInputStream(Nombrearchivo)), this);
            ServidorSMTP s = (ServidorSMTP)d.readObject();
            d.close();
            this.setContrasena(s.getContrasena());
            this.setCorreoRemitente(s.getCorreoRemitente());
            this.setHost(s.getHost());
            this.setPuerto(s.getPuerto());
            this.setSSL_FACTORY(s.getSSL_FACTORY());
            this.setUsarSSL(s.isUsarSSL());
        }
        catch(FileNotFoundException e) {
            System.err.println(e);
            return false;
        }

         return true;

    }

    public static void main(String[] args) {
        ServidorSMTP serv = new ServidorSMTP();
        serv.setContrasena( ("AngelaJorgeElias".toCharArray()) );
        serv.setCorreoRemitente("secm.prueba@gmail.com");
        serv.setHost("smtp.gmail.com");
        serv.setPuerto(465);
        serv.setUsarSSL(true);

        System.out.println(serv.guardar());

        ServidorSMTP serv2 = new ServidorSMTP();

        int i=1;
        String nombreArchivo="./config"+i+".xml";
        File archivo = new File(nombreArchivo);
        while(archivo.exists()){
            serv2.abrir(nombreArchivo);
            System.out.println(serv2.getCorreoRemitente());
            
            i++;
            nombreArchivo="./config"+i+".xml";
            archivo = new File(nombreArchivo);
        }
    }

}
