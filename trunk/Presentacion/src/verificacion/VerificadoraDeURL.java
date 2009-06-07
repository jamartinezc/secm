
package verificacion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Elias
 */
public class VerificadoraDeURL {
    public static boolean verificarURL(String url){
        Pattern pattern = Pattern.compile("((([a-zA-Z0-9])+)([((\\.)([a-zA-Z0-9])+)[((\\-)([a-zA-Z0-9])+)]])*)");

        Matcher matcher = pattern.matcher(url.trim());

        return matcher.matches();
    }
}
