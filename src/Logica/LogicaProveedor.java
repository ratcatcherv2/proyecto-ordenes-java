/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Clases.Cliente;
import Clases.Proveedor;
import java.util.ArrayList;

/**
 *
 * @author Frank-Hz
 */
public class LogicaProveedor {
    ArrayList<Proveedor> Lista = new ArrayList<Proveedor>();
    
    
    public void add(Proveedor proveedor){
        Lista.add(proveedor);
    }
    
    public void edit(Proveedor proveedorEdit){
        //Lista.add(proveedor);
        /*for(Proveedor proveedor: Lista){
            if (proveedor.getCodigo() == proveedorEdit.getCodigo()){
                proveedor.setNombre(proveedorEdit.getNombre());
                proveedor.setTelefono(proveedorEdit.getTelefono());
            }
        }*/
        int indice = proveedorEdit.getCodigo()-1;
        Lista.set(indice, proveedorEdit);
    }
    
    public ArrayList<Proveedor> getLista() {
        return Lista;
    }
    
    public int getSize(){
        return Lista.size();
    }
    
    public Proveedor getProveedor(int posicion) {
        return Lista.get(posicion);
    }
    
    public Boolean remove(int codigo){
        Boolean isDelete = false;
        for(int i=0; i<Lista.size(); i++){
            if (Lista.get(i).getCodigo() == codigo){
                Lista.remove(i);
                isDelete = true;
            }
        }
        // reiniciar codigos
        for(int i=0; i<Lista.size(); i++){
            Lista.get(i).setCodigo(i+1);
        } 
        return isDelete;
    }
    
    public void cleanList(){
        Lista.clear();
    }
    
}
