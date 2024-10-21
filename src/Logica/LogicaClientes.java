/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;
import Clases.Cliente;
import Clases.Proveedor;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LogicaClientes {
    private int indice;
    private Cliente[] Lista;

    public LogicaClientes(int tamaño){
        indice = 0;
        Lista = new Cliente[tamaño];
    }
    
    public int getIndice() {
        return indice;
    }
    public void setIndice(int indice) {
        this.indice = indice;
    }    
    
    public Cliente[] getLista() {
        return Lista;
    }
    public void setLista(Cliente[] Lista) {
        this.Lista = Lista;
    }
    public Cliente getCliente(int posicion) {
        return Lista[posicion];
    }
   
    public void add(Cliente cliente){
        Lista[indice] = cliente;
        indice++;
    }
    public void edit(Cliente cliente_edit){
        int codigo = cliente_edit.getCodigo();
        int indice = codigo - 1;
        String newNombre = cliente_edit.getNombre();
        String newTipoZona = cliente_edit.getTipoZona();
        Lista[indice].setNombre(newNombre);
        Lista[indice].setTipoZona(newTipoZona);
    }
    
    public void delete(int codigo, int tamaño){       
       Cliente[] newArray = new Cliente[tamaño];       
       int newIndex = 0;       
       for(int i=0; i<Lista.length; i++){
           if (Lista[i]!=null && Lista[i].getCodigo() != codigo){
               Lista[i].setCodigo(newIndex+1);
               newArray[newIndex] = Lista[i];
               newIndex++;
           }
       }
       Lista = newArray;
       setIndice(newIndex);
       System.out.println("Lista final: " + Arrays.toString(Lista));
    }
    
    public void cleanList(){
        Arrays.fill(Lista,null);
    }
    
    public int getCountValid(){
        int valid = 0;
        for(Cliente cliente: Lista){
            if (cliente != null){
                valid++;
            }
        }
        return valid;
    }
    
    public int getSize(){
        return Lista.length;
    }
    
    public void setCliente(int index, Cliente cliente){
        Lista[index] = cliente;
    }
}


