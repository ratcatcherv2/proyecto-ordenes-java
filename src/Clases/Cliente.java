/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.Objects;

public class Cliente {
    private int codigo;
    private String nombre;
    private String tipoZona;
    
    public Cliente() {
    }
    
    public Cliente(int codigo, String nombre, String tipoZona) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipoZona = tipoZona;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoZona() {
        return tipoZona;
    }

    public void setTipoZona(String tipoZona) {
        this.tipoZona = tipoZona;
    }

   
     
    
    
}
