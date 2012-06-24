
package accesodatos.frontera;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JORGE
 */
public class DriverBD {


    public static Connection conectar(String url,String usuario,String contrasena,String driver, String protocoloDBMS) throws ClassNotFoundException, SQLException{
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException(driver+" Driver not found.");
        }

        System.out.println("jdbc:"+protocoloDBMS+url);

        Connection con = DriverManager.getConnection("jdbc:"+protocoloDBMS+url,usuario,contrasena);
        return con;
    }

    public static String[] consultarTablas(Connection con) throws SQLException {

        ArrayList<String> tablesNames = new ArrayList(100);
        ResultSet result;
        result = con.getMetaData().getTables(con.getCatalog(),null,"%",null);

        while(result.next()) {
            tablesNames.add(result.getString(3));
        }

        return tablesNames.toArray(new String[0]);
    }

    /**
     * Consulta en la base de datos por que columnas tiene la tabla indicada
     * @param Con conexion con la base de datos
     * @param TableName tabla de la cual se quiere saber sus columnas
     * @return Arreglo con las columnas que tiene la tabla indicada
     * @throws java.sql.SQLException Si la tabla no existe o se perdió la conexión.
     */
    public static String[] consultarColumnas(Connection con, String tableName) throws SQLException {

        ResultSet result;
        result = con.createStatement().executeQuery("SELECT * FROM " + tableName);

        int size = result.getMetaData().getColumnCount();
        ResultSetMetaData data = result.getMetaData();
        ArrayList<String> names = new ArrayList<String>(size);
        for (int i = 0; i < size; i++) {
            names.add( data.getColumnName(i + 1) );
        }

        return names.toArray(new String[0]);
    }

    public static void seleccionarBD(Connection con, String dataBaseName) throws SQLException{

        if ( (dataBaseName != null && !dataBaseName.equals("") ) ) {
            con.setCatalog(dataBaseName);
        }

    }

    public static String[][] consultar(Connection con, String SQL){
        ResultSet resultados;
        LinkedList<String[]> consulta = new LinkedList<String[]>();
        try {
            System.out.println("Ejecuntando: "+SQL);
            resultados = con.createStatement().executeQuery(SQL);
            int nCol = resultados.getMetaData().getColumnCount();
            while(resultados.next()){
                String[] fila = new String[nCol];
                for (int i = 0; i < nCol; i++) {
                    fila[i] = resultados.getString(i+1);
                }
                consulta.add(fila);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DriverBD.class.getName()).log(Level.SEVERE, null, ex);
            return new String[0][0];
        }


        return consulta.toArray(new String[0][0]);
    }
}
