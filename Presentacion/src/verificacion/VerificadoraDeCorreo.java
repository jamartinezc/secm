
package verificacion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Elias
 */
public class VerificadoraDeCorreo extends InputVerifier{

    @Override
    public boolean verify(JComponent input) {

        JTextField textField = (JTextField) input;
        String correo = textField.getText();
        return verificarCorreo(correo);
    }

        /**
     * Verifica un solo correo
     * @param correo correo a verificar
     * @return true si la string contiene un correo, false de lo contrario.
     */
    public static boolean verificarCorreo(String correo){
        Pattern pattern = Pattern.compile("((\\w+)([(\\.\\w+)[(\\-\\w+)]])*)@((([a-zA-Z0-9])+)([((\\.)([a-zA-Z0-9])+)[((\\-)([a-zA-Z0-9])+)]])*)");
//        Pattern pattern = Pattern.compile("(((\\.)((a-z)|(A-Z)|(0-9))+)|((\\-)((a-z)|(A-Z)|(0-9))+))*");

        Matcher matcher = pattern.matcher(correo.trim());

        return matcher.matches();
    }

}
