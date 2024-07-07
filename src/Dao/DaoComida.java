/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.CartaComida;


public class DaoComida {
            Connection con;
    conexion cn=new conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public void Agregar(CartaComida C){
        String sql="insert into menucomida (IdComida,Nombre,pedidos) values (?,?,?)";
        try {
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            ps.setInt(1, (C.getIdComida()));
            ps.setString(2, (C.getNombre()));
            ps.setString(3, C.getPedidos());
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            
        }finally {
            try {
                ps.close();
            } catch (SQLException ex) {
            }
        }
    }
   
    public DefaultTableModel listar(){
        DefaultTableModel modelo;
        
        String [] titulos={"ID","Nombre","Peidos"};
        
        String [] registros=new String[3];
        modelo=new DefaultTableModel(null,titulos);
        
        String sql="SELECT * FROM menucomida";
        
        try{
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                registros[0]=rs.getString("ID");
                registros[1]=rs.getString("Nombre");
                registros[2]=rs.getString("Pedidos");

                modelo.addRow(registros);
            }
            return modelo;
        }catch(Exception e){
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
    }
 
     
     public boolean editar(CartaComida C){
       String sql="update menubebidas set Nombre=?,pedidos=? where IdBebidas=?";
       try{
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            ps.setString(1, C.getNombre());
            ps.setString(2, C.getPedidos());
            ps.setInt(3, C.getIdComida());
            int n=ps.executeUpdate();
            if(n!=0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
   }
}
