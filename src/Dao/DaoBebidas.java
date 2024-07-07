/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.CartaBebidas;
import modelo.usuarios;

public class DaoBebidas {
        Connection con;
    conexion cn=new conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public void Agregar(CartaBebidas C){
        String sql="insert into menubebidas (IdBebidas,Nombre,pedidos) values (?,?,?)";
        try {
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            ps.setInt(1, (C.getIdBebidas()));
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
   
    public DefaultTableModel listarBebidas(){
        
        
        String [] nombresColumnas={"ID","Nombre","Pedidos"};
        String [] bebidas=new String[3];
        DefaultTableModel modelo=new DefaultTableModel(null,nombresColumnas);
        
        String sql="SELECT * FROM menubebidas";
        
        try{
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                bebidas[0]=rs.getString("ID");
                bebidas[1]=rs.getString("Nombre");
                bebidas[2]=rs.getString("Pedidos");
                modelo.addRow(bebidas);
            }
            return modelo;
        }catch(Exception e){
            JOptionPane.showConfirmDialog(null, e);
            return modelo;
        }
    }
 
     
     public boolean editar(CartaBebidas C){
       String sql="update menubebidas set Nombre=?,pedidos=? where IdBebidas=?";
       try{
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            ps.setString(1, C.getNombre());
            ps.setString(2, C.getPedidos());
            ps.setInt(3, C.getIdBebidas());
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
