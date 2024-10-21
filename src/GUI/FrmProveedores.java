/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;
import Clases.Cliente;
import Clases.Orden;
import Clases.Proveedor;
import Logica.LogicaProveedor;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class FrmProveedores extends javax.swing.JFrame {

    DefaultTableModel modelTable = new DefaultTableModel();
    private LogicaProveedor LProveedor = new LogicaProveedor();
    private String FormularioAccion = "REGISTRO";
    public static ArrayList<Proveedor> ListaProveedor;
    public FrmProveedores() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Datos de Prueba
        LProveedor.add(new Proveedor(1,"ADIDOS","984054"));
        LProveedor.add(new Proveedor(2,"SODIMARC","659847"));
        LProveedor.add(new Proveedor(3,"LARAH SAC","804561"));
        ListarTablaProveedor();
    }
    
    private void ListarTablaProveedor(){
        String columnas[] = {"Codigo","Nombre","Telefono"};
        modelTable.setColumnIdentifiers(columnas);
        modelTable.setRowCount(0);
        tbTablaProveedores.setModel(modelTable); 
        
        ArrayList<Proveedor> data = LProveedor.getLista();
        ListaProveedor = data;
        for(int i=0; i<data.size(); i++){
            int codigo = data.get(i).getCodigo();
            String nombre = data.get(i).getNombre();
            String telefono = data.get(i).getTelefono();
            modelTable.addRow(new Object[]{codigo,nombre,telefono});
        }
    }
    
    private Boolean CodigoDuplicado(int codigo){
        Boolean esDuplicado = false;
        for (int i=0; i<LProveedor.getLista().size(); i++) {
            int eval_codigo = LProveedor.getLista().get(i).getCodigo();
            if (eval_codigo == codigo) {
                esDuplicado = true;
            }
        }
        return esDuplicado;
    }

    private void BuscarProveedor(String valor){
            modelTable.setRowCount(0);
            tbTablaProveedores.setModel(modelTable); 
            for (Proveedor proveedor : LProveedor.getLista()) {
                String dataNombre = proveedor.getNombre().toUpperCase();
                String dataCodigo = String.valueOf(proveedor.getCodigo());
                String dataTelefono = String.valueOf(proveedor.getTelefono());
                String dataValor = valor.toUpperCase();
                if (dataNombre.startsWith(dataValor) || dataCodigo.equals(dataValor) 
                        || dataTelefono.startsWith(dataValor)) {
                    int codigo = proveedor.getCodigo();
                    String nombre = proveedor.getNombre();
                    String telefono = proveedor.getTelefono();
                    modelTable.addRow(new Object[]{codigo,nombre,telefono});
                }
            }
    }
   
    public void exportarDatos(){       
        ArrayList<Proveedor> proveedores = LProveedor.getLista();
        if (proveedores.size() == 0){
            JOptionPane.showMessageDialog(null,"No hay datos que exportar");
            return;
        }
        String ruta = selectorRuta("save");
        if (ruta == null){
            return;
        }
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta))) {
            int cantidad = 0;            
            for(Proveedor proveedor: proveedores){           
                if (proveedor != null){
                    String nombre = proveedor.getNombre();
                    String telefono = proveedor.getTelefono();
                    String cadena_registro = proveedor.getCodigo() +" -> "+ nombre +" -> "+ telefono;
                    escritor.write( cadena_registro +"\n");
                    cantidad++;
                }
            }             
            JOptionPane.showMessageDialog(null,"Exportacion Finalizada. "+cantidad+" proveedores exportados.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    public void importarDatos(){
        String ruta = selectorRuta("open");
        if (ruta == null){
            return;
        }
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            String linea;
            LProveedor.cleanList();
            int cantidad = 0;
            while((linea = lector.readLine()) != null){
                String[] data = linea.split(" -> ");
                if (data.length == 3){
                    int codigo = Integer.parseInt(data[0].replace(" ",""));
                    String nombre = String.valueOf(data[1]);
                    String telefono = data[2];
                    LProveedor.add(new Proveedor(codigo, nombre, telefono));
                    cantidad++;
                }
            }
            ListarTablaProveedor();
            JOptionPane.showMessageDialog(null, "Importacion finalizada. "+cantidad+" proveedores agregados.");
        } catch (IOException e) {
            System.out.println("err");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public String selectorRuta(String tipo){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setPreferredSize(new Dimension(600, 450));
        fileChooser.setDialogTitle("Seleccione directorio");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (.txt)","txt");
        fileChooser.setFileFilter(filter);
        fileChooser.setSelectedFile(new File("proveedores.txt"));
        int userSeleccion = 0;
        switch(tipo){
            case "save":
                userSeleccion = fileChooser.showSaveDialog(null);
                break;
            case "open":
                userSeleccion = fileChooser.showOpenDialog(null);
                break;
        }
        
        if (userSeleccion != JFileChooser.APPROVE_OPTION){
            JOptionPane.showMessageDialog(null, "ruta no seleccionada");
            return null;
        }
        String ruta = fileChooser.getSelectedFile().getAbsolutePath();
        if (!ruta.endsWith(".txt")) ruta += ".txt";  
        return ruta;
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ProveedorPanel = new javax.swing.JTabbedPane();
        TabPanelLista = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtBusquedaValor = new javax.swing.JTextField();
        BtnBuscar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnRecargar = new javax.swing.JButton();
        BtnModificar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTablaProveedores = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        lblListaAlert = new javax.swing.JLabel();
        BtnNuevoRegistro1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnImportar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        TabPanelRegistro = new javax.swing.JPanel();
        LabelRegistroTitle = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        BtnRegistrar = new javax.swing.JButton();
        txtCodigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblRegistroAlert = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        ProveedorPanel.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        ProveedorPanel.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ProveedorPanelStateChanged(evt);
            }
        });

        TabPanelLista.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Buscar");
        TabPanelLista.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        txtBusquedaValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaValorActionPerformed(evt);
            }
        });
        TabPanelLista.add(txtBusquedaValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 150, -1));

        BtnBuscar.setText("Buscar");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });
        TabPanelLista.add(BtnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, -1, -1));

        BtnEliminar.setText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });
        TabPanelLista.add(BtnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, -1, -1));

        BtnRecargar.setText("Recargar");
        BtnRecargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRecargarActionPerformed(evt);
            }
        });
        TabPanelLista.add(BtnRecargar, new org.netbeans.lib.awtextra.AbsoluteConstraints(576, 70, 90, -1));

        BtnModificar.setText("Modificar");
        BtnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnModificarActionPerformed(evt);
            }
        });
        TabPanelLista.add(BtnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 70, -1, -1));
        TabPanelLista.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 820, 10));

        tbTablaProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbTablaProveedores);

        TabPanelLista.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 700, 240));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Lista de Proveedores");
        TabPanelLista.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblListaAlert.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TabPanelLista.add(lblListaAlert, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 310, 20));

        BtnNuevoRegistro1.setText("Registrar");
        BtnNuevoRegistro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoRegistro1ActionPerformed(evt);
            }
        });
        TabPanelLista.add(BtnNuevoRegistro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, -1, -1));

        jButton1.setText("Orden de Compras");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        TabPanelLista.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 180, 40));

        btnImportar.setText("Importar");
        btnImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarActionPerformed(evt);
            }
        });
        TabPanelLista.add(btnImportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, -1, -1));

        btnExportar.setText("Exportar");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });
        TabPanelLista.add(btnExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 20, -1, -1));

        ProveedorPanel.addTab("Lista", TabPanelLista);

        TabPanelRegistro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TabPanelRegistroFocusGained(evt);
            }
        });
        TabPanelRegistro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LabelRegistroTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelRegistroTitle.setText("Nuevo Proveedor");
        TabPanelRegistro.add(LabelRegistroTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel4.setText("Nombres:");
        TabPanelRegistro.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });
        TabPanelRegistro.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 200, -1));

        jLabel5.setText("Telefono:");
        TabPanelRegistro.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        BtnRegistrar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        BtnRegistrar.setText("Guardar");
        BtnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRegistrarActionPerformed(evt);
            }
        });
        TabPanelRegistro.add(BtnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 200, 40));

        txtCodigo.setEditable(false);
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        TabPanelRegistro.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 200, -1));

        jLabel7.setText("Codigo:");
        TabPanelRegistro.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        lblRegistroAlert.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRegistroAlert.setForeground(new java.awt.Color(255, 51, 51));
        TabPanelRegistro.add(lblRegistroAlert, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 330, 10));

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        TabPanelRegistro.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 200, -1));

        ProveedorPanel.addTab("Registro", TabPanelRegistro);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ProveedorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ProveedorPanel)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBusquedaValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBusquedaValorActionPerformed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        // TODO add your handling code here:
        String valor_busqueda = txtBusquedaValor.getText();
        lblListaAlert.setText("");
        if (valor_busqueda.length() == 0){
            lblListaAlert.setText("Ingresa el valor de la busqueda");
            return;
        }
        BuscarProveedor(valor_busqueda);
        
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void BtnRecargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRecargarActionPerformed
        // TODO add your handling code here:
        ListarTablaProveedor();
    }//GEN-LAST:event_BtnRecargarActionPerformed

    private void BtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModificarActionPerformed
        // TODO add your handling code here:        
        int row = tbTablaProveedores.getSelectedRow();        
        if (row < 0){
            JOptionPane.showMessageDialog(null, "Proveedor no seleccionado");
            return;
        }
        int codigoModificar = (int) modelTable.getValueAt(row, 0);
        Proveedor proveedorEdit = LProveedor.getProveedor(codigoModificar-1);
        
        LabelRegistroTitle.setText("Modificar Proveedor");
        FormularioAccion = "MODIFICAR";
        txtCodigo.setText(String.valueOf(proveedorEdit.getCodigo()));
        txtNombre.setText(proveedorEdit.getNombre());
        txtTelefono.setText(proveedorEdit.getTelefono());
        ProveedorPanel.setSelectedComponent(TabPanelRegistro);
        
    }//GEN-LAST:event_BtnModificarActionPerformed

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void BtnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegistrarActionPerformed
        // TODO add your handling code here:
        
        lblRegistroAlert.setText("");
        int codigo = Integer.valueOf(txtCodigo.getText());
        String nombre = String.valueOf(txtNombre.getText());
        String telefono = String.valueOf(txtTelefono.getText());        
        /*if (CodigoDuplicado(codigo)){
            lblRegistroAlert.setText("Ya existe un cliente con el codigo ingresado");
            return;
        }*/
        
        if (nombre.length() <=0 || telefono.length() <= 0){
            lblRegistroAlert.setText("Campos incompletos o erroneos");
            return;
        }
        
        String mensaje = "---";        
        if (FormularioAccion.equals("MODIFICAR")){
            LProveedor.edit(new Proveedor(codigo,nombre,telefono)); 
            mensaje = "Proveedor Modificado!";
        }else {
            LProveedor.add(new Proveedor(codigo,nombre,telefono));
            mensaje = "Proveedor Registrado!";
        }
               
        txtCodigo.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");   
        FormularioAccion = "REGISTRO";
        LabelRegistroTitle.setText("Nuevo Proveedor");
        
        ListarTablaProveedor();
        ProveedorPanel.setSelectedComponent(TabPanelLista);
        JOptionPane.showMessageDialog(this,mensaje);
        
    }//GEN-LAST:event_BtnRegistrarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        // TODO add your handling code here:
        int row = tbTablaProveedores.getSelectedRow();        
        if (row < 0){
            JOptionPane.showMessageDialog(null, "Proveedor no seleccionado");
            return;
        }
        int codigoRemover = (int) modelTable.getValueAt(row, 0);
        int confirma =JOptionPane.showConfirmDialog(null,
            "Eliminar Proveedor "+codigoRemover,"¿Cuidado?", JOptionPane.YES_NO_OPTION);
        if (confirma == 0){
            LProveedor.remove(codigoRemover);
            ListarTablaProveedor();
        }
        
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnNuevoRegistro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoRegistro1ActionPerformed
        txtNombre.requestFocus();
        ProveedorPanel.setSelectedComponent(TabPanelRegistro);
        txtCodigo.setText(String.valueOf(LProveedor.getSize()+1));
    }//GEN-LAST:event_BtnNuevoRegistro1ActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void TabPanelRegistroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TabPanelRegistroFocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_TabPanelRegistroFocusGained

    private void ProveedorPanelStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ProveedorPanelStateChanged
        txtNombre.requestFocus();
        if (FormularioAccion == "REGISTRO"){
            txtCodigo.setText(String.valueOf(LProveedor.getSize()+1));
        } 
    }//GEN-LAST:event_ProveedorPanelStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        FrmOrdenes viewOrden = new FrmOrdenes();
        viewOrden.setVisible(true);
        viewOrden.setLocationRelativeTo(null);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        exportarDatos();
    }//GEN-LAST:event_btnExportarActionPerformed

    private void btnImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarActionPerformed
        importarDatos();
    }//GEN-LAST:event_btnImportarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmProveedores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnModificar;
    private javax.swing.JButton BtnNuevoRegistro1;
    private javax.swing.JButton BtnRecargar;
    private javax.swing.JButton BtnRegistrar;
    private javax.swing.JLabel LabelRegistroTitle;
    private javax.swing.JTabbedPane ProveedorPanel;
    private javax.swing.JPanel TabPanelLista;
    private javax.swing.JPanel TabPanelRegistro;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnImportar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblListaAlert;
    private javax.swing.JLabel lblRegistroAlert;
    private javax.swing.JTable tbTablaProveedores;
    private javax.swing.JTextField txtBusquedaValor;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
