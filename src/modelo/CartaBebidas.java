/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ander
 */
public class CartaBebidas {


    int IdBebidas;
    String Nombre;
    String pedidos;
    
    public CartaBebidas(){
        
    }
    public CartaBebidas (int IdBebidas, String Nombre, String pedidos){
        this.IdBebidas = IdBebidas;
        this.Nombre = Nombre;
        this.pedidos = Nombre;
    }
    
       public int getIdBebidas() {
        return IdBebidas;
    }

    public void setIdBebidas(int IdBebidas) {
        this.IdBebidas = IdBebidas;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getPedidos() {
        return pedidos;
    }

    public void setPedidos(String pedidos) {
        this.pedidos = pedidos;
    }
    
}
