
package presentacion;

import java.text.DateFormat;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import negocio.control.AdministradoraListasDeCorreos;
import negocio.control.ServiciosDeCorreo;
import negocio.entidades.CorreoCalendarizado;
import negocio.entidades.ServidorSMTP;

/**
 *
 * @author Elias
 */
public class IGEnvioDeCorreo extends javax.swing.JFrame {

    /** Creates new form IGEnvioDeCorreo */
    public IGEnvioDeCorreo() {
        listaCorreos = negocio.control.ServiciosDeCorreo.consultarCorreosCalendarizados();
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
        tablaCorreo = new javax.swing.JTable();
        enviar = new javax.swing.JButton();
        editar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel1.setText("Correos Programados");

        tablaCorreo.setModel(new ModeloTabla());
        tablaCorreo.setColumnSelectionAllowed(true);
        tablaCorreo.getTableHeader().setReorderingAllowed(false);
        jScrollPane.setViewportView(tablaCorreo);
        tablaCorreo.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        enviar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        enviar.setText("Enviar un correo");
        enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarActionPerformed(evt);
            }
        });

        editar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        editar.setText("Editar correo");
        editar.setFocusCycleRoot(true);
        editar.setMaximumSize(new java.awt.Dimension(121, 23));
        editar.setMinimumSize(new java.awt.Dimension(121, 23));
        editar.setPreferredSize(new java.awt.Dimension(121, 23));
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(enviar, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 232, Short.MAX_VALUE)
                        .addComponent(editar, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(editar, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(enviar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void enviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarActionPerformed
        EnviarCorreo dialogo = new EnviarCorreo();
        dialogo.setVisible(true);
}//GEN-LAST:event_enviarActionPerformed

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed

         if(tablaCorreo.getSelectedRow() >= 0){
            CorreoCalendarizado calendarizado = listaCorreos.get(tablaCorreo.getSelectedRow());
            String lista = calendarizado.getNombreDeLista();
            String servidor = ServiciosDeCorreo.buscarListaDeCorreos(lista).getServidorSMTP().getCorreoRemitente();
            EnviarCorreo dialogo = new EnviarCorreo(this, lista, servidor);
            dialogo.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this,
                                    "Debe seleccionar una lista antes de realizar esta acción.",
                                    "Atención",
                                    JOptionPane.INFORMATION_MESSAGE);
        }

}//GEN-LAST:event_editarActionPerformed


    private class ModeloTabla extends AbstractTableModel{

        @Override
        public int getRowCount() {
            return listaCorreos.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int column) {
            switch(column){
                case 0:{
                    return "Nombre";
                }
                case 1:{
                    return "Siguiente envio";
                }
                case 2:{
                    return "Dias entre envío";
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
                    return listaCorreos.get(rowIndex).getNombreDeLista();
                }
                case 1:{
                    DateFormat df = DateFormat.getInstance();
                    return df.format(listaCorreos.get(rowIndex).getFechaEnvio());
                }
                case 2:{
                    return (listaCorreos.get(rowIndex).getDiasEntreEnvios()*1000*60*60*24);
                }
                default:{
                    return "--";
                }
            }
        }
    }

    protected void enviarCorreo(ServidorSMTP servidor){
//        ServiciosDeCorreo.enviarLista(listaCorreos.get(WIDTH), servidor);
        
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        try
        {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception ex)
        {
            //Sin comentarios
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                IGEnvioDeCorreo hola = new IGEnvioDeCorreo();
                hola.setVisible(true);
            }
        });
    }

    //Variables de vista:
    private LinkedList<CorreoCalendarizado> listaCorreos;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton editar;
    private javax.swing.JButton enviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable tablaCorreo;
    // End of variables declaration//GEN-END:variables

}
