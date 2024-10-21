/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;
import Clases.Cliente;
import Clases.Orden;
import Clases.Proveedor;
import Logica.LogicaClientes;
import Logica.LogicaOrden;
import Logica.LogicaProveedor;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class FrmOrdenes extends javax.swing.JFrame {
    int maximoClientes = 5;
    DefaultTableModel modelTable = new DefaultTableModel();
    private LogicaClientes LCliente = new LogicaClientes(maximoClientes);
    private LogicaProveedor LProveedor = new LogicaProveedor();
    private LogicaOrden LOrden = new LogicaOrden();
    
    public static Cliente[] ListaClientes_1; 
    
    public FrmOrdenes() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        /*LOrden.add("874e4",new Orden("ELMER","ADIDAS",1900));
        LOrden.add("4535",new Orden("MARY","ALBUL",300));
        LOrden.add("6573",new Orden("POLO","RASC",110));*/
        ListarTablaOrden();
        ListarComboClientes();   
        ListarComboProveedor();
    }
    
    private void ListarComboClientes(){
        FrmClientes viewClientes = new FrmClientes();
        Cliente[] data = viewClientes.ListaClientes;
        
        for(int i=0; i<data.length; i++){
            System.out.println(""+data[i]);
            if (data[i] != null){
                cb_clientes.addItem(data[i].getNombre());
            }            
        }
    }
    
    private void ListarComboProveedor(){
        FrmProveedores viewProveedor = new FrmProveedores();
        ArrayList<Proveedor> ProveedorLista = viewProveedor.ListaProveedor;        
        for(int i=0; i<ProveedorLista.size(); i++){
            if (ProveedorLista.get(i).getNombre()!=null){
                cb_proveedores.addItem(ProveedorLista.get(i).getNombre());
            }            
        }
    }
    
    private void ListarTablaOrden(){
        String columnas[] = {"Codigo","Cliente","Proveedor","Total"};
        modelTable.setColumnIdentifiers(columnas);
        modelTable.setRowCount(0);
        tbTablaOrdenes.setModel(modelTable); 
        
        Map<String,Orden> dataMap = (HashMap<String,Orden>) LOrden.getList();
        for (Map.Entry<String, Orden> orden : dataMap.entrySet()) {
            String codigo = orden.getKey();
            Orden ordenValue = orden.getValue();            
            String cliente = ordenValue.getCliente();
            String proveedor = ordenValue.getProveedor();
            float total = ordenValue.getTotal();
            modelTable.addRow(new Object[]{codigo,cliente,proveedor,total});
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
    
    private void BuscarOrden(String valor){
        modelTable.setRowCount(0);
        tbTablaOrdenes.setModel(modelTable);    
        Map<String,Orden> OrdenList = LOrden.getList();

        for(Entry<String,Orden> orden: OrdenList.entrySet()){
            String codigo = orden.getKey();
            String cliente = orden.getValue().getCliente().toUpperCase();
            String proveedor = orden.getValue().getProveedor().toUpperCase();
            float total = orden.getValue().getTotal();
            if (codigo.startsWith(valor) || cliente.startsWith(valor) 
                || proveedor.startsWith(valor)){
                
                modelTable.addRow(new Object[]{codigo,cliente,proveedor,total});
            }
        }
        
    }

    public static boolean esNumero(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public void exportarDatos(){
        Map<String,Orden> ordenes = (HashMap<String,Orden>) LOrden.getList();
        if (ordenes.size() == 0){
            JOptionPane.showMessageDialog(null, "No hay datos que exportar");
            return;
        }
        
        String ruta = selectorRuta("save");
        if (ruta == null){
            return;
        }
        int cantidad = 0;
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta))) {
            for(Map.Entry<String,Orden> orden: ordenes.entrySet()){           
                String codigo = orden.getKey();
                String cliente = orden.getValue().getCliente();
                String proveedor = orden.getValue().getProveedor();
                float total = orden.getValue().getTotal();
                String cadena_registro = codigo +" -> "+ cliente +" -> "+ proveedor +" -> "+ total;
                escritor.write( cadena_registro +"\n");
                cantidad++;
            }             
            JOptionPane.showMessageDialog(null,"Exportacion Finalizada. "+cantidad+" ordenes exportadas");
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
            LOrden.cleanList();
            int cantidad = 0;
            while((linea = lector.readLine()) != null){
                System.out.println("Read: "+linea);
                String[] data = linea.split(" -> ");
                if (data.length == 4){
                    LOrden.add(data[0],new Orden(data[1],data[2],Float.parseFloat(data[3])));
                    cantidad++;
                }
            }
            ListarTablaOrden();
            JOptionPane.showMessageDialog(null, "Importacion finalizada. "+cantidad+" ordenes agregados.");
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
        fileChooser.setSelectedFile(new File("ordenes.txt"));
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

        OrdenPanel = new javax.swing.JTabbedPane();
        TabPanelLista = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtBusquedaValor = new javax.swing.JTextField();
        BtnBuscar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTablaOrdenes = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        lblListaAlert = new javax.swing.JLabel();
        BtnNuevoRegistro1 = new javax.swing.JButton();
        BtnReload = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        TabPanelRegistro = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Btn_registrar = new javax.swing.JButton();
        txt_total = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblRegistroAlert = new javax.swing.JLabel();
        cb_proveedores = new javax.swing.JComboBox<>();
        cb_clientes = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.OverlayLayout(getContentPane()));

        OrdenPanel.setBackground(new java.awt.Color(255, 255, 255));
        OrdenPanel.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        OrdenPanel.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                OrdenPanelStateChanged(evt);
            }
        });

        TabPanelLista.setBackground(new java.awt.Color(255, 255, 255));
        TabPanelLista.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Buscar:");
        TabPanelLista.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        txtBusquedaValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaValorActionPerformed(evt);
            }
        });
        TabPanelLista.add(txtBusquedaValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 130, -1));

        BtnBuscar.setText("Buscar");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });
        TabPanelLista.add(BtnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, -1, -1));

        BtnEliminar.setText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });
        TabPanelLista.add(BtnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, -1, -1));
        TabPanelLista.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 820, 10));

        tbTablaOrdenes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbTablaOrdenes);

        TabPanelLista.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 700, 240));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Lista de Ordenes de Compra");
        TabPanelLista.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblListaAlert.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TabPanelLista.add(lblListaAlert, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 200, 20));

        BtnNuevoRegistro1.setText("Registrar");
        BtnNuevoRegistro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoRegistro1ActionPerformed(evt);
            }
        });
        TabPanelLista.add(BtnNuevoRegistro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, -1, -1));

        BtnReload.setText("Recargar");
        BtnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnReloadActionPerformed(evt);
            }
        });
        TabPanelLista.add(BtnReload, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, -1, -1));

        jButton1.setText("PROVEEDORES");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        TabPanelLista.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 140, 40));

        jButton2.setText("CLIENTES");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        TabPanelLista.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 140, 40));

        btnExport.setText("Exportar");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        TabPanelLista.add(btnExport, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, -1, -1));

        jButton3.setText("Importar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        TabPanelLista.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 70, -1, -1));

        OrdenPanel.addTab("Lista", TabPanelLista);

        TabPanelRegistro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TabPanelRegistroFocusGained(evt);
            }
        });
        TabPanelRegistro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Nueva Orden de Compra");
        TabPanelRegistro.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel4.setText("Cliente:");
        TabPanelRegistro.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, -1, -1));

        jLabel5.setText("Proveedor:");
        TabPanelRegistro.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        Btn_registrar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Btn_registrar.setText("Registrar");
        Btn_registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_registrarActionPerformed(evt);
            }
        });
        TabPanelRegistro.add(Btn_registrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 200, 40));

        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });
        TabPanelRegistro.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 200, 30));

        jLabel7.setText("Codigo:");
        TabPanelRegistro.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        lblRegistroAlert.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRegistroAlert.setForeground(new java.awt.Color(255, 51, 51));
        TabPanelRegistro.add(lblRegistroAlert, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 330, 20));

        cb_proveedores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        TabPanelRegistro.add(cb_proveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 200, 30));

        cb_clientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        TabPanelRegistro.add(cb_clientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, 200, 30));

        jLabel8.setText("Total:");
        TabPanelRegistro.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, -1, -1));

        txt_codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_codigoActionPerformed(evt);
            }
        });
        TabPanelRegistro.add(txt_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 200, 30));

        OrdenPanel.addTab("Registro", TabPanelRegistro);

        getContentPane().add(OrdenPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBusquedaValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBusquedaValorActionPerformed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        // TODO add your handling code here:     
        lblListaAlert.setText("");
        String valor_busqueda = txtBusquedaValor.getText();
        if (valor_busqueda.length()==0){
            lblListaAlert.setText("Valor de busqueda no ingresado");
            return;
        }
        BuscarOrden(valor_busqueda);
        
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void Btn_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_registrarActionPerformed
        lblRegistroAlert.setText("");        
        if (txt_codigo.getText().isEmpty()){
           lblRegistroAlert.setText("Codigo no especificado.");
           return;
        }
        if (cb_clientes.getSelectedItem().equals("")){
           lblRegistroAlert.setText("Monto Total invalido.");
           return;
        }
        if (cb_proveedores.getSelectedItem().equals("")){
           lblRegistroAlert.setText("Monto Total invalido.");
           return;
        }
        if (txt_total.getText().isEmpty() || !esNumero(txt_total.getText())){
           lblRegistroAlert.setText("Monto Total invalido.");
           return;
        }
        
        String codigo = txt_codigo.getText();
        String cliente = (String) cb_clientes.getSelectedItem();
        String proveedor = (String) cb_proveedores.getSelectedItem();
        float total = Float.parseFloat(txt_total.getText());
        
        LOrden.add(codigo, new Orden(cliente,proveedor,total));
        txt_codigo.setText("");
        txt_total.setText("");
        cb_proveedores.setSelectedIndex(0);
        cb_clientes.setSelectedIndex(0);
        
        ListarTablaOrden();
        OrdenPanel.setSelectedComponent(TabPanelLista);
        JOptionPane.showMessageDialog(this,"Orden Registrada!!");
        
    }//GEN-LAST:event_Btn_registrarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        // TODO add your handling code here:
        int row = tbTablaOrdenes.getSelectedRow();        
        if (row < 0){
            JOptionPane.showMessageDialog(null, "Orden no seleccionada");
            return;
        }
        String codigoRemover = (String) modelTable.getValueAt(row, 0);
        int confirma =JOptionPane.showConfirmDialog(null,
            "¿Eliminar Orden "+codigoRemover+"?","", JOptionPane.YES_NO_OPTION);
        if (confirma == 0){
            LOrden.remove(codigoRemover);
            ListarTablaOrden();
        }
        
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnNuevoRegistro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoRegistro1ActionPerformed
        // TODO add your handling code here:
        OrdenPanel.setSelectedComponent(TabPanelRegistro);        
    }//GEN-LAST:event_BtnNuevoRegistro1ActionPerformed

    private void TabPanelRegistroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TabPanelRegistroFocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_TabPanelRegistroFocusGained

    private void OrdenPanelStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_OrdenPanelStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_OrdenPanelStateChanged

    private void txt_codigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_codigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_codigoActionPerformed

    private void BtnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnReloadActionPerformed
        // TODO add your handling code here:
        ListarTablaOrden();
    }//GEN-LAST:event_BtnReloadActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        FrmClientes viewClientes = new FrmClientes();
        viewClientes.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        FrmProveedores viewProveedor = new FrmProveedores();
        viewProveedor.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        exportarDatos();
        
    }//GEN-LAST:event_btnExportActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        importarDatos();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(FrmOrdenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmOrdenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmOrdenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmOrdenes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new FrmOrdenes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnNuevoRegistro1;
    private javax.swing.JButton BtnReload;
    private javax.swing.JButton Btn_registrar;
    private javax.swing.JTabbedPane OrdenPanel;
    private javax.swing.JPanel TabPanelLista;
    private javax.swing.JPanel TabPanelRegistro;
    private javax.swing.JButton btnExport;
    private javax.swing.JComboBox<String> cb_clientes;
    private javax.swing.JComboBox<String> cb_proveedores;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblListaAlert;
    private javax.swing.JLabel lblRegistroAlert;
    private javax.swing.JTable tbTablaOrdenes;
    private javax.swing.JTextField txtBusquedaValor;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables
}
