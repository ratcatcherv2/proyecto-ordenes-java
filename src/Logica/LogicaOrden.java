/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;
import Clases.Orden;
import java.util.HashMap;
import java.util.Map;

public class LogicaOrden {
    private Map<String,Orden> Lista = new HashMap<String,Orden>();
    
    public Map getList(){
        return Lista;
    }
    
    public void add(String codigo,Orden orden){
        Lista.put(codigo,orden);
    }
    
    public void remove(String codigo){
        Lista.remove(codigo);
    }
    
    public Orden getOrden(String codigo){
        return Lista.get(codigo);
    }
    
    public void edit(String codigo, Orden orden){
        Lista.put(codigo,orden);
    }
    
    public void cleanList(){
        Lista.clear();
    }
}
