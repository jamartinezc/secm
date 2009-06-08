/*
 * Interfaz.java
 *
 * Created on 10 de mayo de 2009, 12:35 AM
 */

package presentacion;
import javax.swing.JOptionPane;

/**
 *
 * @author  Administrador
 */
public class IGConfiguracionServidorSMTP extends javax.swing.JFrame {
    
    
    private String host;
    private int port;
    private boolean usarSSL;
    private String remitente;
    private char[] contraseña;
    
    /** Creates new form Interfaz */
    public IGConfiguracionServidorSMTP() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        thost = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ssl = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        tremitente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        clave = new javax.swing.JPasswordField();
        guardar = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        puerto = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        uImap = new javax.swing.JRadioButton();
        uPop = new javax.swing.JRadioButton();
        jCheckBox1 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel1.setText("SERVIDOR SMTP");

        jLabel2.setText("En esta pestaña usted podrá configurar las principales opciones para el envío de correos: ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel3.setText("Ingrese los datos:");

        jLabel4.setText("Host:");

        thost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thostActionPerformed(evt);
            }
        });

        jLabel5.setText("Puerto:");

        ssl.setText("Usar SSL");

        jLabel6.setText("Correo Remitente:");

        jLabel7.setText("Contraseña:");

        guardar.setText("Guardar");
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });

        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });

        puerto.setModel(new javax.swing.SpinnerNumberModel(1, 1, 65535, 1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Selección Servidor Correo Entrante"));

        buttonGroup1.add(uImap);
        uImap.setText("Usar IMAP");
        uImap.setEnabled(false);

        buttonGroup1.add(uPop);
        uPop.setSelected(true);
        uPop.setText("Usar POP3");
        uPop.setEnabled(false);
        uPop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uPopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(uPop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(uImap)
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(uPop)
                    .addComponent(uImap))
                .addGap(57, 57, 57))
        );

        jCheckBox1.setText("Deseo permitir que los usuarios se puedan eliminar de la lista de correos");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(62, 62, 62)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(puerto)
                            .addComponent(thost, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(ssl)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(clave)
                            .addComponent(tremitente, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(guardar)
                            .addGap(18, 18, 18)
                            .addComponent(cancelar))
                        .addComponent(jLabel2))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jCheckBox1))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(thost, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(puerto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ssl)
                .addGap(5, 5, 5)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tremitente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(clave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelar)
                    .addComponent(guardar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        this.dispose();
}//GEN-LAST:event_cancelarActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed

        int port;

        host = thost.getText();
        port = (Integer) puerto.getValue();
        System.out.println(port);

        if(ssl.isSelected())
            usarSSL = true;
        else
            usarSSL = false;

        remitente = tremitente.getText();
        contraseña = clave.getPassword();

        //verifcar campos
        boolean datosIncorrectos = false;
        if( ! verificacion.VerificadoraDeURL.verificarURL(host) ){
            JOptionPane.showMessageDialog(this,
                    "la dirección de host ingresada no es correcta,\npor favor verifiquela e intente de nuevo.",
                    "Error al adicionar Servidor",
                    JOptionPane.ERROR_MESSAGE);
            datosIncorrectos = true;
        }

        if( ! verificacion.VerificadoraDeCorreo.verificarCorreo(remitente) ){
            JOptionPane.showMessageDialog(this,
                    "la dirección de correo ingresada no es correcta,\npor favor verifiquela e intente de nuevo.",
                    "Error al adicionar Servidor",
                    JOptionPane.ERROR_MESSAGE);
            datosIncorrectos = true;
        }

        if( ! datosIncorrectos ){
            boolean resultado = negocio.control.ServiciosDeCorreo.GuardarSMTP(host.trim(), port, usarSSL, remitente.trim(), contraseña);
            if( ! resultado ){
                JOptionPane.showMessageDialog(this,
                        "Hubo un error al adicionar el servidor SMTP,\npor favor intente mas tarde.",
                        "Error al adicionar Servidor",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
}//GEN-LAST:event_guardarActionPerformed

    private void thostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thostActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_thostActionPerformed

    private void uPopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uPopActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_uPopActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        
        if (uPop.isEnabled())
        {
            uPop.setEnabled(false);
            uImap.setEnabled(false);
        }
        else
        {
            uPop.setEnabled(true);
            uImap.setEnabled(true);
        }



    }//GEN-LAST:event_jCheckBox1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IGConfiguracionServidorSMTP().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelar;
    private javax.swing.JPasswordField clave;
    private javax.swing.JButton guardar;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner puerto;
    private javax.swing.JCheckBox ssl;
    private javax.swing.JTextField thost;
    private javax.swing.JTextField tremitente;
    private javax.swing.JRadioButton uImap;
    private javax.swing.JRadioButton uPop;
    // End of variables declaration//GEN-END:variables

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isUsarSSL() {
        return usarSSL;
    }

    public void setUsarSSL(boolean usarSSL) {
        this.usarSSL = usarSSL;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public char[] getContraseña() {
        return contraseña;
    }

    public void setContraseña(char[] contraseña) {
        this.contraseña = contraseña;
    }
    
}
