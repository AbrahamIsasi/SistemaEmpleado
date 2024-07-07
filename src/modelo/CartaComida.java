/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ander
 */
public class CartaComida {

    public static void setModel(DefaultTableModel modelo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    int IdComida;
    String Nombre;
    String pedidos;
    
    public CartaComida(){
        
    }
    public CartaComida (int IdComida, String Nombre, String pedidos){
        this.IdComida = IdComida;
        this.Nombre = Nombre;
        this.pedidos = pedidos;
    }

    public String getPedidos() {
        return pedidos;
    }

    public void setPedidos(String pedidos) {
        this.pedidos = pedidos;
    }

    public int getIdComida() {
        return IdComida;
    }

    public void setIdComida(int IdComida) {
        this.IdComida = IdComida;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
}
