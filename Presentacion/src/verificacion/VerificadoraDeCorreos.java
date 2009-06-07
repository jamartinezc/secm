
package verificacion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextArea;

/**
 *
 * @author Elias
 */
public class VerificadoraDeCorreos extends InputVerifier{

    /**
     * Verifica la lista de correos pasada, separados por coma.
     * @param correos los correos a verificar
     * @return true si la verificación es correcta, false de lo contrario.
     */
    public static boolean verificarCorreos(String correos){
        String[] listaCorreos = correos.split("[,;]");

        for (int i = 0; i < listaCorreos.length; i++) {
            boolean correcto = VerificadoraDeCorreo.verificarCorreo(listaCorreos[i]);
            if( ! correcto ){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean verify(JComponent input) {
        JTextArea textArea = (JTextArea) input;
        return verificarCorreos(textArea.getText());
    }
}
