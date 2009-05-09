/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package negocio.entidades;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *
 * @author Jaguar
 */
public class test {
   public static void main(String[] args) throws FileNotFoundException {
        System.out.println("empezó");

        ServidorSMTP serv = new ServidorSMTP();
        serv.setContrasena( ("AngelaJorgeElias".toCharArray()) );
        serv.setCorreoRemitente("secm.prueba@gmail.com");
        serv.setHost("smtp.gmail.com");
        serv.setPuerto(465);
        serv.setUsarSSL(true);

        XMLEncoder e;
        FileOutputStream fos = new FileOutputStream("./test.xml");
        e = new XMLEncoder(new BufferedOutputStream(fos));
//        e = new XMLEncoder(System.out);
        e.writeObject(serv);
//        e.writeObject(new JButton("Hello, world"));
        e.close();
        System.out.println("acabó");
    }
}
