/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;
import Clases.Cliente;
import Clases.Orden;
import Logica.LogicaClientes;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class FrmClientes extends javax.swing.JFrame {
    private DefaultTableModel modelTable = new DefaultTableModel();
    private int LimiteMaximo = 10;
    private LogicaClientes LClientes = new LogicaClientes(LimiteMaximo);  
    private String FormularioAccion = "REGISTRO";
    public static Cliente[] ListaClientes;
    
    public FrmClientes() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Datos de Prueba
        /*LClientes.add(new Cliente(1,"Anna","Comercial"));
        LClientes.add(new Cliente(2,"Jose","Residencial"));
        LClientes.add(new Cliente(3,"Peter","Urbana"));*/
        ListarTablaClientes();
    }
    
    private void ListarTablaClientes(){
        try {
            String columnas[] = {"Codigo","Nombre","Tipo Zona"};
            modelTable.setColumnIdentifiers(columnas);
            modelTable.setRowCount(0);
            tbTablaClientes.setModel(modelTable); 

            Cliente[] dataClientes = LClientes.getLista();
            ListaClientes = dataClientes;
            for (int i=0; i<dataClientes.length; i++) {
                int codigo = dataClientes[i].getCodigo();
                String nombre = dataClientes[i].getNombre();
                String tipozona = dataClientes[i].getTipoZona();
                modelTable.addRow(new Object[]{codigo,nombre,tipozona});
            }
        } catch (Exception e) {
        }
    }
   
    private void BuscarClientes(String valor){
        try {
            modelTable.setRowCount(0);
            tbTablaClientes.setModel(modelTable); 
            for (Cliente cliente : LClientes.getLista()) {
                String dataNombre = cliente.getNombre().toUpperCase();
                String dataCodigo = String.valueOf(cliente.getCodigo());
                String dataValor = valor.toUpperCase();
                if (dataNombre.startsWith(dataValor) || dataCodigo.equals(dataValor)) {
                    int codigo = cliente.getCodigo();
                    String nombre = cliente.getNombre();
                    String tipozona = cliente.getTipoZona();
                    modelTable.addRow(new Object[]{codigo,nombre,tipozona});
                }
            }
        } catch (Exception e) {
        }
     
    }
    
    public int indiceAutoIncremento(){
        if(LClientes.getIndice()==0){
            return LClientes.getIndice()+1;
        }        
        else{
            int indiceSearch = LClientes.getIndice()-1;
            return LClientes.getCliente(indiceSearch).getCodigo()+1;
        }
             
    }
    
    public void importarDatos_v1(){
        try (BufferedReader lector = new BufferedReader(new FileReader("clientes.txt"))) {
            String linea;
            LClientes.cleanList();
            int cantidad = 0;
            int index = 0;
            while((linea = lector.readLine()) != null && cantidad < LimiteMaximo){
                String[] data = linea.split(" -> ");
                if (data.length == 3){
                    int codigo = Integer.parseInt(data[0].replace(" ",""));
                    String nombre = String.valueOf(data[1]);
                    String tipozona = data[2];
                    LClientes.setCliente(index, new Cliente(codigo, nombre, tipozona));
                    cantidad++;
                }
                index++;
            }
            ListarTablaClientes();
            JOptionPane.showMessageDialog(null, "Importacion finalizada. "+cantidad+" clientes agregados.");
        } catch (IOException e) {
            System.out.println("err");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void importarDatos(){
        String ruta = selectorRuta("open");
        if (ruta == null){
            return;
        }
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            String linea;
            LClientes.cleanList();
            int cantidad = 0;
            int index = 0;
            while((linea = lector.readLine()) != null && cantidad < LimiteMaximo){
                String[] data = linea.split(" -> ");
                if (data.length == 3){
                    int codigo = Integer.parseInt(data[0].replace(" ",""));
                    String nombre = String.valueOf(data[1]);
                    String tipozona = data[2];
                    LClientes.setCliente(index, new Cliente(codigo, nombre, tipozona));
                    cantidad++;
                }
                index++;
            }
            ListarTablaClientes();
            JOptionPane.showMessageDialog(null, "Importacion finalizada. "+cantidad+" clientes agregados.");
        } catch (IOException e) {
            System.out.println("err");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void exportarDatos(){
        String archivo_nombre = "clientes.txt";        
        Cliente[] clientes = LClientes.getLista();
        if (LClientes.getCountValid() == 0){
            JOptionPane.showMessageDialog(null,"No hay datos que exportar");
            return;
        }
        int cantidad = 0;
        String ruta = selectorRuta("save");
        if (ruta == null){
            return;
        }
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta))) {
            for(Cliente cliente: clientes){
                if (cliente != null){
                    System.out.println(""+cliente.getNombre());
                    String nombre = cliente.getNombre();
                    String tipozona = cliente.getTipoZona();
                    String cadena_registro = cliente.getCodigo() +" -> "+ nombre +" -> "+ tipozona;
                    escritor.write( cadena_registro +"\n");
                    cantidad++;
                }
            }             
            JOptionPane.showMessageDialog(null,"Exportacion Finalizada. "+cantidad+" clientes exportados.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void exportarDatos_v1(){        
        String archivo_nombre = "clientes.txt";        
        Cliente[] clientes = LClientes.getLista();
        if (LClientes.getCountValid() == 0){
            JOptionPane.showMessageDialog(null,"No hay datos que exportar");
            return;
        }
        int cantidad = 0;
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo_nombre))) {
            for(Cliente cliente: clientes){
                if (cliente != null){
                    System.out.println(""+cliente.getNombre());
                    String nombre = cliente.getNombre();
                    String tipozona = cliente.getTipoZona();
                    String cadena_registro = cliente.getCodigo() +" -> "+ nombre +" -> "+ tipozona;
                    escritor.write( cadena_registro +"\n");
                    cantidad++;
                }
            }             
            JOptionPane.showMessageDialog(null,"Exportacion Finalizada. "+cantidad+" clientes exportados.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String selectorRuta(String tipo){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setPreferredSize(new Dimension(600, 450));
        fileChooser.setDialogTitle("Seleccione directorio");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (.txt)","txt");
        fileChooser.setFileFilter(filter);
        fileChooser.setSelectedFile(new File("clientes.txt"));
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

        ClienteForm = new javax.swing.JTabbedPane();
        TabPanelLista = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBusquedaValor = new javax.swing.JTextField();
        BtnBuscar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTablaClientes = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        lblListaAlert = new javax.swing.JLabel();
        BtnRecargar = new javax.swing.JButton();
        BtnNuevoRegistro1 = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnModificar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        TabPanelRegistro = new javax.swing.JPanel();
        LabelRegistroTitle = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtClienteNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        BtnClienteRegistrar = new javax.swing.JButton();
        txtClienteCodigo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbClienteTipoZona = new javax.swing.JComboBox<>();
        lblRegistroAlert = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        ClienteForm.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        ClienteForm.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ClienteFormStateChanged(evt);
            }
        });

        TabPanelLista.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Buscar:");
        TabPanelLista.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        txtBusquedaValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaValorActionPerformed(evt);
            }
        });
        TabPanelLista.add(txtBusquedaValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 170, -1));

        BtnBuscar.setText("Buscar");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });
        TabPanelLista.add(BtnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, -1, -1));
        TabPanelLista.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 820, 10));

        tbTablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbTablaClientes);

        TabPanelLista.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 720, 240));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Lista de Clientes");
        TabPanelLista.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblListaAlert.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TabPanelLista.add(lblListaAlert, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 360, 20));

        BtnRecargar.setText("Recargar");
        BtnRecargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRecargarActionPerformed(evt);
            }
        });
        TabPanelLista.add(BtnRecargar, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, -1, -1));

        BtnNuevoRegistro1.setText("Registrar");
        BtnNuevoRegistro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoRegistro1ActionPerformed(evt);
            }
        });
        TabPanelLista.add(BtnNuevoRegistro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, -1, -1));

        BtnEliminar.setText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });
        TabPanelLista.add(BtnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, -1, -1));

        BtnModificar.setText("Modificar");
        BtnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnModificarActionPerformed(evt);
            }
        });
        TabPanelLista.add(BtnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, -1, -1));

        jButton1.setText("Orden de Compras");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        TabPanelLista.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 150, 40));

        btnImport.setText("Importar");
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });
        TabPanelLista.add(btnImport, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, -1, -1));

        btnExport.setText("Exportar");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        TabPanelLista.add(btnExport, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, -1, -1));

        ClienteForm.addTab("Lista", TabPanelLista);

        TabPanelRegistro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TabPanelRegistroFocusGained(evt);
            }
        });
        TabPanelRegistro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LabelRegistroTitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelRegistroTitle.setText("Nuevo Cliente");
        TabPanelRegistro.add(LabelRegistroTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel4.setText("Nombres:");
        TabPanelRegistro.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        txtClienteNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteNombreActionPerformed(evt);
            }
        });
        TabPanelRegistro.add(txtClienteNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 200, -1));

        jLabel5.setText("Tipo Zona:");
        TabPanelRegistro.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        BtnClienteRegistrar.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        BtnClienteRegistrar.setText("Guardar");
        BtnClienteRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnClienteRegistrarActionPerformed(evt);
            }
        });
        TabPanelRegistro.add(BtnClienteRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 200, 40));

        txtClienteCodigo.setEditable(false);
        txtClienteCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteCodigoActionPerformed(evt);
            }
        });
        TabPanelRegistro.add(txtClienteCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 200, -1));

        jLabel7.setText("Codigo:");
        TabPanelRegistro.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        cbClienteTipoZona.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Comercial", "Rural", "Urbana", "Residencial" }));
        TabPanelRegistro.add(cbClienteTipoZona, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 200, -1));

        lblRegistroAlert.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRegistroAlert.setForeground(new java.awt.Color(255, 51, 51));
        TabPanelRegistro.add(lblRegistroAlert, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 330, 10));

        ClienteForm.addTab("Registro", TabPanelRegistro);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ClienteForm, javax.swing.GroupLayout.PREFERRED_SIZE, 712, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ClienteForm)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBusquedaValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBusquedaValorActionPerformed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed

        String valor_busqueda = String.valueOf(txtBusquedaValor.getText());
        lblListaAlert.setText("");
        if (valor_busqueda.length() == 0){
            lblListaAlert.setText("Ingresa el valor de la busqueda");
            return;
        }
        BuscarClientes(valor_busqueda);
        
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void txtClienteNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClienteNombreActionPerformed

    private void txtClienteCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClienteCodigoActionPerformed

    private void BtnClienteRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnClienteRegistrarActionPerformed
        lblRegistroAlert.setText("");      
        if (LClientes.getCountValid() == LimiteMaximo){
            JOptionPane.showMessageDialog(null, "Se ha llegado al maximo de registros ("+LimiteMaximo+")");
            return;
        }
        //int codigo = Integer.parseInt(txtClienteCodigo.getText());
        String nombre = String.valueOf(txtClienteNombre.getText());
        String tipozona = String.valueOf(cbClienteTipoZona.getSelectedItem());
        if (nombre.length() == 0){
            lblRegistroAlert.setText("Nombre no ingresado");
            return;
        }
        if (tipozona.length() == 0){
            lblRegistroAlert.setText("Tipo no seleccionado");
            return;
        }
        
        String mensaje = "---";
        Cliente clienteOBJ = new Cliente();     
        
        if (FormularioAccion.equals("MODIFICAR")){            
            int codigo = Integer.parseInt(txtClienteCodigo.getText());
            String codex = txtClienteCodigo.getText();
            clienteOBJ.setCodigo(codigo);
            clienteOBJ.setNombre(txtClienteNombre.getText());
            clienteOBJ.setTipoZona((String)cbClienteTipoZona.getSelectedItem());
            LClientes.edit(clienteOBJ);
            mensaje = "Cliente Modificado!";
            
        }else if(FormularioAccion.equals("REGISTRO")){
            clienteOBJ.setCodigo(indiceAutoIncremento());
            clienteOBJ.setNombre(nombre);
            clienteOBJ.setTipoZona(tipozona);
            LClientes.add(clienteOBJ);
            mensaje = "Cliente Registrado!";
        }
        txtClienteCodigo.setText("");
        txtClienteNombre.setText("");
        cbClienteTipoZona.setSelectedIndex(0);
        ClienteForm.setSelectedComponent(TabPanelLista);
        FormularioAccion = "REGISTRO";
        LabelRegistroTitle.setText("Nuevo Cliente");
        
        JOptionPane.showMessageDialog(this,mensaje);
        ListarTablaClientes();
        
    }//GEN-LAST:event_BtnClienteRegistrarActionPerformed

    private void ClienteFormStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ClienteFormStateChanged
        txtClienteNombre.requestFocus();
        if (FormularioAccion == "REGISTRO"){
            txtClienteCodigo.setText(String.valueOf(indiceAutoIncremento()));
        }        
    }//GEN-LAST:event_ClienteFormStateChanged

    private void BtnRecargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRecargarActionPerformed
        // TODO add your handling code here:
        ListarTablaClientes();
    }//GEN-LAST:event_BtnRecargarActionPerformed

    private void TabPanelRegistroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TabPanelRegistroFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_TabPanelRegistroFocusGained

    private void BtnNuevoRegistro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoRegistro1ActionPerformed
        // TODO add your handling code here:
        txtClienteCodigo.setText(String.valueOf(LClientes.getIndice()+1));
        ClienteForm.setSelectedComponent(TabPanelRegistro);
        txtClienteNombre.requestFocus();
    }//GEN-LAST:event_BtnNuevoRegistro1ActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        // TODO add your handling code here:
        int row = tbTablaClientes.getSelectedRow();        
        if (row < 0){
            JOptionPane.showMessageDialog(null, "Cliente no seleccionado");
            return;
        }
        int codigoRemover = (int) modelTable.getValueAt(row, 0);
        int confirma =JOptionPane.showConfirmDialog(null,
            "Eliminar Cliente "+codigoRemover,"¿Cuidado?", JOptionPane.YES_NO_OPTION);
        if (confirma == 0){
            LClientes.delete(codigoRemover, LimiteMaximo);
            ListarTablaClientes();
        }
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnModificarActionPerformed
        // TODO add your handling code here:
        int row = tbTablaClientes.getSelectedRow();        
        if (row < 0){
            JOptionPane.showMessageDialog(null, "Cliente no seleccionado");
            return;
        }
        int codigoModificar = (int) modelTable.getValueAt(row, 0);
        Cliente clienteEdit = LClientes.getCliente(codigoModificar-1);
        
        LabelRegistroTitle.setText("Modificar Cliente");
        FormularioAccion = "MODIFICAR";
        txtClienteCodigo.setText(String.valueOf(clienteEdit.getCodigo()));
        txtClienteNombre.setText(clienteEdit.getNombre());
        cbClienteTipoZona.setSelectedItem(clienteEdit.getTipoZona());
        ClienteForm.setSelectedComponent(TabPanelRegistro);
    }//GEN-LAST:event_BtnModificarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formWindowClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        FrmOrdenes viewOrden = new FrmOrdenes();
        viewOrden.setVisible(true);
        viewOrden.setLocationRelativeTo(null);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        exportarDatos();
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
        importarDatos();
    }//GEN-LAST:event_btnImportActionPerformed

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
            java.util.logging.Logger.getLogger(FrmClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnClienteRegistrar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnModificar;
    private javax.swing.JButton BtnNuevoRegistro1;
    private javax.swing.JButton BtnRecargar;
    private javax.swing.JTabbedPane ClienteForm;
    private javax.swing.JLabel LabelRegistroTitle;
    private javax.swing.JPanel TabPanelLista;
    private javax.swing.JPanel TabPanelRegistro;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnImport;
    private javax.swing.JComboBox<String> cbClienteTipoZona;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblListaAlert;
    private javax.swing.JLabel lblRegistroAlert;
    private javax.swing.JTable tbTablaClientes;
    private javax.swing.JTextField txtBusquedaValor;
    private javax.swing.JTextField txtClienteCodigo;
    private javax.swing.JTextField txtClienteNombre;
    // End of variables declaration//GEN-END:variables
}
