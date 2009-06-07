
package presentacion;

import java.util.LinkedList;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import negocio.control.ServiciosDeCorreo;
import negocio.entidades.ListaDeCorreos;

/**
 *
 * @author Elias
 */
public class IGListaDeCorreos extends javax.swing.JFrame {

    /** Creates new form IGListaDeCorreos */
    public IGListaDeCorreos() {
        inicializarLista();
        initComponents();
    }

    private void inicializarLista(){
        listasDeCorreos = ServiciosDeCorreo.consultarListasDeCorreo();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        nueva = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listas = new javax.swing.JTable();
        eliminar = new javax.swing.JButton();
        elimReg = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel2.setText("Listas de Correos");

        nueva.setFont(new java.awt.Font("Tahoma", 0, 12));
        nueva.setText("Nueva lista");
        nueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaActionPerformed(evt);
            }
        });

        listas.setModel(new ModeloTabla());
        jScrollPane1.setViewportView(listas);

        eliminar.setFont(new java.awt.Font("Tahoma", 0, 12));
        eliminar.setText("Eliminar lista");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        elimReg.setFont(new java.awt.Font("Tahoma", 0, 12));
        elimReg.setText("NoReceptores");
        elimReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elimRegActionPerformed(evt);
            }
        });

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(44, 44, 44)
                            .addComponent(jLabel2))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(nueva, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(41, 41, 41)
                            .addComponent(eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                            .addComponent(elimReg, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(214, 214, 214)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nueva, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(elimReg, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaActionPerformed
        NuevaLista dialogo = new NuevaLista(this, true);
        dialogo.setVisible(true);
}//GEN-LAST:event_nuevaActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        int seleccionada = listas.getSelectedRow();
        if(seleccionada >= 0){
            String nombre = listasDeCorreos.get(seleccionada).getNombre();
            ServiciosDeCorreo.eliminarLista(nombre);
            inicializarLista();
            this.repaint();
        }else{
            JOptionPane.showMessageDialog(this,
                                    "Debe seleccionar una lista antes de realizar esta acción.",
                                    "Atención",
                                    JOptionPane.INFORMATION_MESSAGE);
        }
}//GEN-LAST:event_eliminarActionPerformed

    private void elimRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elimRegActionPerformed
        int seleccionada = listas.getSelectedColumn();
        if(seleccionada >= 0){
            EliminarRegistros dialogo = new EliminarRegistros(this,true);
            dialogo.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this,
                                    "Debe seleccionar una lista antes de realizar esta acción.",
                                    "Atención",
                                    JOptionPane.INFORMATION_MESSAGE);
        }
}//GEN-LAST:event_elimRegActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed


    private class ModeloTabla extends AbstractTableModel{

        @Override
        public int getRowCount() {
            return listasDeCorreos.size();
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public String getColumnName(int column) {
            switch(column){
                case 0:{
                    return "Nombre";
                }
                default:{
                    return "--";
                }
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch(columnIndex){
                case 0:{
                    return listasDeCorreos.get(rowIndex).getNombre();
                }
                default:{
                    return "--";
                }
            }
        }

    }

    //Metodos de dialogos
    //TODO obtener las cosas que faltan de forma facil, crearListaDeCorreos, guardarLista
    protected void setNombreNuevaLista(String nombre){
        nombreNuevaLista = nombre;
    }

//    protected void setOringenDeDatos(negocio.entidades.OrigenDeDatos origen){
//        if(nuevaLista != null){
//            nuevaLista.setOrigenDeDatos(origen);
////            ServiciosDeCorreo.guardarLista(nuevaLista, columnas, asunto, mensaje, adjuntos);
//        }
//    }

    protected void eliminarRegistros(String[] correosAEliminar){
        int seleccionada = listas.getSelectedColumn();
        ListaDeCorreos lista;
        if(seleccionada >= 0){
            lista = listasDeCorreos.get(seleccionada);
            ServiciosDeCorreo.eliminarRegistrosdeLista(lista, correosAEliminar);
        }
    }

    protected void crearLista(int origen, Properties datos){
        ListaDeCorreos lista = ServiciosDeCorreo.crearListaDeCorreos(nombreNuevaLista, origen, datos);

        //TODO seleccionar los siguientes datos desde la interfáz
        ServiciosDeCorreo.columnasDisponibles(lista.getOrigenDeDatos().getComportamientoOrigen());
        Properties columnas = new Properties();
        columnas.setProperty("#destinatariosTO#", "correo");
        columnas.setProperty("#nombre#", "nombre");
        String[] archivos = new String[0];

        //TODO mover esta llamada a la interfáz de ingreso de los datos anteriores
        ServiciosDeCorreo.guardarLista(lista, columnas,"pruebas GUI", "<h1>Mensaje desde IGListaDeCorreos</h1><br>Su nombre es:#nombre#", archivos);
        inicializarLista();
        this.repaint();
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {

        try{
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception ex){
            //Sin comentarios
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IGListaDeCorreos().setVisible(true);
            }
        });
    }

    //Variables de vista:
    private LinkedList<ListaDeCorreos> listasDeCorreos;
    private String nombreNuevaLista;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton elimReg;
    private javax.swing.JButton eliminar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable listas;
    private javax.swing.JButton nueva;
    // End of variables declaration//GEN-END:variables

}
