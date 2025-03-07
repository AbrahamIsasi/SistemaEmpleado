/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;
import Dao.DaoArea;
import Dao.DaoAsistencia;
import Dao.DaoCargo;
import Dao.DaoDatos;
import Dao.DaoEmpleado;
import Dao.DaoNomina;
import Dao.DaoPago;
import Dao.DaoBebidas;
import Dao.DaoComida;
import Dao.DaoUsuarios;
import Dao.conexion;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import modelo.Datos;
import modelo.RenderImagen;
import modelo.areas;
import modelo.asistencias;
import modelo.cargo;
import modelo.empleado;
import modelo.nomina;
import modelo.CartaBebidas;
import modelo.CartaComida;
import modelo.pago;
import modelo.usuarios;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author HELIO
 */
public class MenuPrincipal extends javax.swing.JFrame {
    
    cargo cr=new cargo();
    DaoCargo daoC=new DaoCargo();
    DefaultTableModel modeloCargo=new DefaultTableModel();
    
    areas ar=new areas();
    DaoArea daoA=new DaoArea();
    DefaultTableModel modeloArea=new DefaultTableModel();
    
    empleado em=new empleado();
    DaoEmpleado daoE=new DaoEmpleado();
    DefaultTableModel modeloEmpleado=new DefaultTableModel();
    
    CartaBebidas be=new CartaBebidas ();
    DaoBebidas daoB= new DaoBebidas();
    DefaultTableModel modeloBebidas = new DefaultTableModel();
    
    CartaComida ca= new CartaComida();
    DaoComida daoCo= new DaoComida();
    DefaultTableModel modeloComida = new DefaultTableModel();
    
    nomina no=new nomina();
    DaoNomina daoN=new DaoNomina();
    DefaultTableModel modeloNomina=new DefaultTableModel();
    
    pago pa=new pago();
    DaoPago daoP=new DaoPago();
    DefaultTableModel modeloPago=new DefaultTableModel();
    
    Datos da=new Datos();
    DaoDatos daoD=new DaoDatos();
    DefaultTableModel modeloDatos=new DefaultTableModel();
    
    usuarios usu=new usuarios();
    DaoUsuarios daoU=new DaoUsuarios();
    DefaultTableModel modeloUsuario=new DefaultTableModel();
    
    asistencias asis=new asistencias();
    DaoAsistencia daoAs=new DaoAsistencia();
    DefaultTableModel modeloAsistencias=new DefaultTableModel();
    
    
    String Ruta = "";
    String valor="1";
    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
        listar();
        listarArea();
        listarEmpleado();
        listarNomina();
        listarPagos();
        listarBebidas();
        //listarUsuarios();
        listarUsuarios2();
        cantEmpleados();
        cantCarogs();
        cantAreas();
        cantNominaPendientes();
        cantNominaPagadas();
        cantNominasT();
        cantPagos();
        canUsuarios();
        listarAsistencia();
        modeloDatos.addColumn("ID");
        modeloDatos.addColumn("Empresa");
        modeloDatos.addColumn("RUC");
        modeloDatos.addColumn("Razon Social");
        modeloDatos.addColumn("Telefono");
        modeloDatos.addColumn("Direccion");
        modeloDatos.addColumn("Correo");
        modeloDatos.addColumn("Imagen");
        ListarDatos();
        btnEnviarArea.setEnabled(false);
        btnEnviarCargo.setEnabled(false);
    }
    
    private void cantEmpleados(){
        int cantidad;
        cantidad=daoE.CantEmpleado();
        txtcantEmpleados.setText(cantidad+"");
    }
    private void cantCarogs(){
        int cantidad;
        cantidad=daoC.CantCargos();
        txtcantCargos.setText(cantidad+"");
    }
    private void cantAreas(){
        int cantidad;
        cantidad=daoA.CantArea();
        txtcantAreas.setText(cantidad+"");
    }
    private void cantNominaPendientes(){
        int cantidad;
        cantidad=daoN.CantNominaP("pendiente");
        txtcantNP.setText(cantidad+"");
    }
    private void cantNominaPagadas(){
        int cantidad;
        cantidad=daoN.CantNominaP("pagado");
        txtcantNPa.setText(cantidad+"");
    }
     private void cantNominasT(){
        int cantidad;
        cantidad=daoN.CantNominaT();
        txtcantNT.setText(cantidad+"");
    }
     private void cantPagos(){
        int cantidad;
        cantidad=daoP.CantPagos();
        txtcantpagos.setText(cantidad+"");
    }
     private void canUsuarios(){
        int cantidad;
        cantidad=daoU.CantUsuarios();
        txtcantusuarios.setText(cantidad+"");
    }
    private void ListarDatos(){
        tabladatos.setDefaultRenderer(Object.class, new RenderImagen());
        
        ArrayList datos;
        Object Datos[] =new Object[8];
        datos=daoD.Listar();
        if(datos!=null){
            for(int i=0;i<datos.size();i++){
                da = (Datos) datos.get(i);
                Datos[0]=String.valueOf(da.getId());
                Datos[1]=da.getNombre();
                Datos[2]=da.getRUC();
                Datos[3]=da.getRasonS();
                Datos[4]=da.getTelefono();
                Datos[5]=da.getDireccion();
                Datos[6]=da.getCorreo();
                try{
                    byte[] imagen =da.getImagen();
                    BufferedImage buffer=null;
                    InputStream inputstream=new ByteArrayInputStream(imagen);
                    buffer=ImageIO.read(inputstream);
                    ImageIcon incono=new ImageIcon(buffer.getScaledInstance(60, 60, 0));
                    Datos[7]=new JLabel(incono);
                }catch (Exception e){
                    Datos[7]=new JLabel("SIN IMAGEN");  
                }
                modeloDatos.addRow(Datos);
            }
            tabladatos.setModel(modeloDatos);
            tabladatos.setRowHeight(60);
            tabladatos.getColumnModel().getColumn(0).setPreferredWidth(60);
            tabladatos.getColumnModel().getColumn(1).setPreferredWidth(60);
            tabladatos.getColumnModel().getColumn(2).setPreferredWidth(60);
            tabladatos.getColumnModel().getColumn(3).setPreferredWidth(60);
            tabladatos.getColumnModel().getColumn(4).setPreferredWidth(60);
            tabladatos.getColumnModel().getColumn(5).setPreferredWidth(60);
            tabladatos.getColumnModel().getColumn(6).setPreferredWidth(60);
            tabladatos.getColumnModel().getColumn(7).setPreferredWidth(60);
        }
    }

    private void listar(){
        List<cargo> lista=daoC.Listar();
        modeloCargo=(DefaultTableModel) tablacargos.getModel();
        Object[] ob=new Object[3];
        for(int i=0;i<lista.size();i++){
            ob[0]=lista.get(i).getId();
            ob[1]=lista.get(i).getNombre();
            ob[2]=lista.get(i).getPrecio();
            modeloCargo.addRow(ob);
        }
        tablacargos.setModel(modeloCargo);
    }
    private void listarArea(){
        List<areas> lista=daoA.Listar();
        modeloArea=(DefaultTableModel) tablaArea.getModel();
        Object[] ob=new Object[2];
        for(int i=0;i<lista.size();i++){
            ob[0]=lista.get(i).getIdarea();
            ob[1]=lista.get(i).getNomArea();
            modeloArea.addRow(ob);
        }
        tablaArea.setModel(modeloArea);
    }
    private void listarUsuarios(){
        List<usuarios> lista=daoU.Listar();
        modeloUsuario=(DefaultTableModel) usuarios.getModel();
        Object[] ob=new Object[5];
        for(int i=0;i<lista.size();i++){
            ob[0]=lista.get(i).getIdUser();
            ob[1]=lista.get(i).getIdempleado();
            ob[2]=lista.get(i).getUsuario();
            ob[3]=lista.get(i).getPassword();
            ob[4]=lista.get(i).getTipo();
            modeloUsuario.addRow(ob);
        }
        usuarios.setModel(modeloUsuario);
    }
    private void listarBebidas (){
        try{
            DefaultTableModel modelo;
            modelo=daoB.listarBebidas();
            tablaBebidas1.setModel(modelo);
        }catch(Exception e){
            
        }
    }
    private void listarEmpleado(){
        try{
            DefaultTableModel modelo;
            modelo=daoE.listar();
            tablaempleado.setModel(modelo);
        }catch(Exception e){
            
        }
    }
   private void listarUsuarios2(){
        try{
            DefaultTableModel modelo;
            modelo=daoU.listar();
            usuarios.setModel(modelo);
        }catch(Exception e){
            
        }
    }
     private void listarAsistencia(){
        try{
            DefaultTableModel modelo;
            modelo=daoAs.listar();
            TablaAsistencias.setModel(modelo);
        }catch(Exception e){
            
        }
    }
    private void listarNomina(){
        try{
            DefaultTableModel modelo;
            modelo=daoN.listar();
            tablanomina.setModel(modelo);
        }catch(Exception e){
            
        }
    }
    private void listarPagos(){
        try{
            DefaultTableModel modelo;
            modelo=daoP.listar();
            tablapago.setModel(modelo);
        }catch(Exception e){
            
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnUsuarios = new javax.swing.JButton();
        btnEmpleados = new javax.swing.JButton();
        btnArea = new javax.swing.JButton();
        btnCargo = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnPnomina = new javax.swing.JButton();
        btnPpagos = new javax.swing.JButton();
        btnPdatos = new javax.swing.JButton();
        btnPhome = new javax.swing.JButton();
        btnPhome1 = new javax.swing.JButton();
        jLabel62 = new javax.swing.JLabel();
        PMenuBebidas = new javax.swing.JButton();
        panel = new javax.swing.JTabbedPane();
        phome = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        txtcantEmpleados = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        txtcantCargos = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        txtcantAreas = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        txtcantNT = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        txtcantNP = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        txtcantNPa = new javax.swing.JLabel();
        Usuarios = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        txtcantusuarios = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        txtcantpagos = new javax.swing.JLabel();
        pempleados = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtidempleado = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtnomempleado = new javax.swing.JTextField();
        txtapeempleado = new javax.swing.JTextField();
        txtdoc = new javax.swing.JTextField();
        cbotipodoc = new javax.swing.JComboBox<>();
        txttelefono = new javax.swing.JTextField();
        txtcorreo = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtidareaempleado = new javax.swing.JTextField();
        txtareaempleado = new javax.swing.JTextField();
        btnBuscaArea = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtidcargoempleado = new javax.swing.JTextField();
        txtcargoempleado = new javax.swing.JTextField();
        btnBuscaCarho = new javax.swing.JButton();
        btnregistrarempleado = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaempleado = new javax.swing.JTable();
        btnModificarEmpleado = new javax.swing.JButton();
        btnBuscarEmpleado = new javax.swing.JButton();
        btnEliminarEmpleado = new javax.swing.JButton();
        pcargo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtidcargo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtcargo = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablacargos = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnEnviarCargo = new javax.swing.JButton();
        txtprecioCargo = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        parea = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtidarea = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtarea = new javax.swing.JTextField();
        btnregistrarArea = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaArea = new javax.swing.JTable();
        btnModificarArea = new javax.swing.JButton();
        btnEliminarArea = new javax.swing.JButton();
        btnBuscarArea = new javax.swing.JButton();
        btnEnviarArea = new javax.swing.JButton();
        Pnomina = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtdocemopleadoN = new javax.swing.JTextField();
        btnBuscarEN = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtidempleadoN = new javax.swing.JTextField();
        txtnomEmpleadoN = new javax.swing.JTextField();
        txtapeempleadoN = new javax.swing.JTextField();
        txtidcargoN = new javax.swing.JTextField();
        txtcargoN = new javax.swing.JTextField();
        txtprecioN = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txttotal = new javax.swing.JTextField();
        dateFecha = new com.toedter.calendar.JDateChooser();
        txtcantTrabajo = new javax.swing.JTextField();
        btnCalcular = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        txtidnomina = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        btnRegistrarNomina = new javax.swing.JButton();
        btnEditarNomina = new javax.swing.JButton();
        btnBuscarNomina = new javax.swing.JButton();
        btnEliminarNomina = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablanomina = new javax.swing.JTable();
        Ppagos = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtidpago = new javax.swing.JTextField();
        txttotalPago = new javax.swing.JTextField();
        btnCalcularPago = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtidempleadop = new javax.swing.JTextField();
        txtempleadop = new javax.swing.JTextField();
        txtapellidop = new javax.swing.JTextField();
        txtidcargop = new javax.swing.JTextField();
        txtcargop = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtdocumentop = new javax.swing.JTextField();
        btnbuscarep = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        fecha1 = new com.toedter.calendar.JDateChooser();
        jLabel37 = new javax.swing.JLabel();
        fecha2 = new com.toedter.calendar.JDateChooser();
        jPanel13 = new javax.swing.JPanel();
        btnregistrarPago = new javax.swing.JButton();
        btnEditarPago = new javax.swing.JButton();
        btnEliminarPago = new javax.swing.JButton();
        btnNuevoPago = new javax.swing.JButton();
        btnGenerarPDFPAGO = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablapago = new javax.swing.JTable();
        Pdatos = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
        txtempresa = new javax.swing.JTextField();
        txtruc = new javax.swing.JTextField();
        txtRs = new javax.swing.JTextField();
        txttelefonoE = new javax.swing.JTextField();
        txtdireccionE = new javax.swing.JTextField();
        txtcorreoE = new javax.swing.JTextField();
        btnCargarImagen = new javax.swing.JButton();
        txtIdDato = new javax.swing.JLabel();
        btnEditardatos = new javax.swing.JButton();
        btnRegistrarE = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabladatos = new javax.swing.JTable();
        pusuarios = new javax.swing.JPanel();
        txtidusuario = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        txtnomusuario = new javax.swing.JTextField();
        txtusuario = new javax.swing.JTextField();
        cbotipousuario = new javax.swing.JComboBox<>();
        btnGuardarUsuario = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        usuarios = new javax.swing.JTable();
        txtpass = new javax.swing.JPasswordField();
        btnEditarusuario = new javax.swing.JButton();
        btnElimarUsuario = new javax.swing.JButton();
        btnBuscarUsuario = new javax.swing.JButton();
        jLabel60 = new javax.swing.JLabel();
        txtdocEmpleadoU = new javax.swing.JTextField();
        btnBuscarEU = new javax.swing.JButton();
        jLabel61 = new javax.swing.JLabel();
        txtidempleadoU = new javax.swing.JTextField();
        Pasistencia = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        txtidAsistencia = new javax.swing.JTextField();
        txtidempleadoA = new javax.swing.JTextField();
        txtnomempleadoA = new javax.swing.JTextField();
        txttipoAsistencia = new javax.swing.JComboBox<>();
        btnregistrarAsistencia = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        TablaAsistencias = new javax.swing.JTable();
        btnPDFAsistencia = new javax.swing.JButton();
        MenuBebidas = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablaBebidas1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBackground(new java.awt.Color(217, 163, 21));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnUsuarios.setText("Usuarios");
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });

        btnEmpleados.setText("Empleados");
        btnEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpleadosActionPerformed(evt);
            }
        });

        btnArea.setText("Area");
        btnArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAreaActionPerformed(evt);
            }
        });

        btnCargo.setText("Cargo");
        btnCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargoActionPerformed(evt);
            }
        });

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnPnomina.setText("Nomina");
        btnPnomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPnominaActionPerformed(evt);
            }
        });

        btnPpagos.setText("Pagos");
        btnPpagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPpagosActionPerformed(evt);
            }
        });

        btnPdatos.setText("Datos");
        btnPdatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdatosActionPerformed(evt);
            }
        });

        btnPhome.setText("Home");
        btnPhome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhomeActionPerformed(evt);
            }
        });

        btnPhome1.setText("Asistencia");
        btnPhome1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhome1ActionPerformed(evt);
            }
        });

        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/casino2.png.jpeg"))); // NOI18N

        PMenuBebidas.setText("MenuBebidas");
        PMenuBebidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PMenuBebidasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel62))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEmpleados)
                            .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnArea, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPnomina, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPpagos, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPdatos, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPhome, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PMenuBebidas, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPhome1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel62)
                .addGap(54, 54, 54)
                .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnArea, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPnomina, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPpagos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(btnPdatos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPhome, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PMenuBebidas, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPhome1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );

        panel.setEnabled(false);

        phome.setBackground(new java.awt.Color(255, 255, 255));

        jLabel49.setText("Empleados");

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtcantEmpleados.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtcantEmpleados.setForeground(new java.awt.Color(0, 153, 204));
        txtcantEmpleados.setText("10");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(txtcantEmpleados)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(txtcantEmpleados)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel50.setText("Cargos");

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtcantCargos.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtcantCargos.setForeground(new java.awt.Color(0, 153, 204));
        txtcantCargos.setText("10");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(txtcantCargos)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(txtcantCargos)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel51.setText("Areas");

        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtcantAreas.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtcantAreas.setForeground(new java.awt.Color(0, 153, 204));
        txtcantAreas.setText("10");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(txtcantAreas)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(txtcantAreas)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel52.setText("Nominas Totales");

        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtcantNT.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtcantNT.setForeground(new java.awt.Color(0, 153, 204));
        txtcantNT.setText("10");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(txtcantNT)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(txtcantNT)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel53.setText("Nominas Pendientes");

        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtcantNP.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtcantNP.setForeground(new java.awt.Color(0, 153, 204));
        txtcantNP.setText("10");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(txtcantNP)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(txtcantNP)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel54.setText("Nominas Pagadas");

        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtcantNPa.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtcantNPa.setForeground(new java.awt.Color(0, 153, 204));
        txtcantNPa.setText("10");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(txtcantNPa)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(txtcantNPa)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        Usuarios.setText("Usuarios");

        jPanel20.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtcantusuarios.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtcantusuarios.setForeground(new java.awt.Color(0, 153, 204));
        txtcantusuarios.setText("10");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(txtcantusuarios)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(txtcantusuarios)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel56.setText("Pagos");

        jPanel21.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtcantpagos.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtcantpagos.setForeground(new java.awt.Color(0, 153, 204));
        txtcantpagos.setText("10");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(txtcantpagos)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(txtcantpagos)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout phomeLayout = new javax.swing.GroupLayout(phome);
        phome.setLayout(phomeLayout);
        phomeLayout.setHorizontalGroup(
            phomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(phomeLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(phomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, phomeLayout.createSequentialGroup()
                        .addGroup(phomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(phomeLayout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel49))
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(phomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(phomeLayout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jLabel50)))
                        .addGap(59, 59, 59)
                        .addGroup(phomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(phomeLayout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jLabel51))))
                    .addGroup(phomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, phomeLayout.createSequentialGroup()
                            .addComponent(jLabel53)
                            .addGap(109, 109, 109)
                            .addComponent(jLabel54)
                            .addGap(124, 124, 124)
                            .addComponent(jLabel52)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, phomeLayout.createSequentialGroup()
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(56, 56, 56)
                            .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(59, 59, 59)
                            .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(phomeLayout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(phomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, phomeLayout.createSequentialGroup()
                                .addComponent(jLabel56)
                                .addGap(182, 182, 182)
                                .addComponent(Usuarios)
                                .addGap(62, 62, 62))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, phomeLayout.createSequentialGroup()
                                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(94, 94, 94))
        );
        phomeLayout.setVerticalGroup(
            phomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(phomeLayout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(phomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(phomeLayout.createSequentialGroup()
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(phomeLayout.createSequentialGroup()
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(phomeLayout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(74, 74, 74)
                .addGroup(phomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(phomeLayout.createSequentialGroup()
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(phomeLayout.createSequentialGroup()
                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(phomeLayout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(61, 61, 61)
                .addGroup(phomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(phomeLayout.createSequentialGroup()
                        .addComponent(Usuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(phomeLayout.createSequentialGroup()
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(219, Short.MAX_VALUE))
        );

        panel.addTab("Home", phome);

        pempleados.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Empleado"));

        jLabel5.setText("ID");

        jLabel6.setText("Nombre:");

        jLabel7.setText("Apellido:");

        jLabel8.setText("Tipo Doc:");

        jLabel9.setText("Documento:");

        jLabel10.setText("Telefono:");

        jLabel11.setText("Correo:");

        cbotipodoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI", "PASAPORTE", "CARNET" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtapeempleado))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtdoc))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(22, 22, 22)
                        .addComponent(cbotipodoc, 0, 163, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(23, 23, 23)
                        .addComponent(txttelefono))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcorreo))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtnomempleado)
                            .addComponent(txtidempleado))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtidempleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtnomempleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtapeempleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(cbotipodoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtdoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txttelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtcorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Area"));

        jLabel12.setText("ID:");

        jLabel13.setText("Area:");

        btnBuscaArea.setText("...");
        btnBuscaArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaAreaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(28, 28, 28)
                        .addComponent(txtidareaempleado))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtareaempleado, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnBuscaArea, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtidareaempleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtareaempleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscaArea)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Cargo"));

        jLabel14.setText("ID:");

        jLabel15.setText("Cargo:");

        btnBuscaCarho.setText("...");
        btnBuscaCarho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaCarhoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(13, 13, 13)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnBuscaCarho, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtcargoempleado, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(txtidcargoempleado))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtidcargoempleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtcargoempleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnBuscaCarho)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnregistrarempleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        btnregistrarempleado.setText("Registrar");
        btnregistrarempleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregistrarempleadoActionPerformed(evt);
            }
        });

        tablaempleado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaempleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaempleadoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaempleado);

        btnModificarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar (1).png"))); // NOI18N
        btnModificarEmpleado.setText("Modificar");
        btnModificarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarEmpleadoActionPerformed(evt);
            }
        });

        btnBuscarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        btnBuscarEmpleado.setText("Buscar");
        btnBuscarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarEmpleadoActionPerformed(evt);
            }
        });

        btnEliminarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        btnEliminarEmpleado.setText("Eliminar");
        btnEliminarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pempleadosLayout = new javax.swing.GroupLayout(pempleados);
        pempleados.setLayout(pempleadosLayout);
        pempleadosLayout.setHorizontalGroup(
            pempleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pempleadosLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pempleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(pempleadosLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pempleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pempleadosLayout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))
                            .addGroup(pempleadosLayout.createSequentialGroup()
                                .addGroup(pempleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnEliminarEmpleado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnregistrarempleado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnModificarEmpleado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscarEmpleado)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        pempleadosLayout.setVerticalGroup(
            pempleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pempleadosLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pempleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pempleadosLayout.createSequentialGroup()
                        .addGroup(pempleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pempleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnregistrarempleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pempleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnModificarEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBuscarEmpleado)))
                        .addGap(4, 4, 4)
                        .addComponent(btnEliminarEmpleado))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(208, Short.MAX_VALUE))
        );

        panel.addTab("Empleados", pempleados);

        pcargo.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("ID:");

        jLabel2.setText("Nombre:");

        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        tablacargos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre Cargo", "Precio"
            }
        ));
        tablacargos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablacargosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablacargos);

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar (1).png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnEnviarCargo.setText("Enviar");
        btnEnviarCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarCargoActionPerformed(evt);
            }
        });

        jLabel16.setText("Precio:");

        javax.swing.GroupLayout pcargoLayout = new javax.swing.GroupLayout(pcargo);
        pcargo.setLayout(pcargoLayout);
        pcargoLayout.setHorizontalGroup(
            pcargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pcargoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pcargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pcargoLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(164, 164, 164))
                    .addGroup(pcargoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30)
                        .addComponent(txtidcargo, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pcargoLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pcargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtprecioCargo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnEnviarCargo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRegistrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addComponent(txtcargo, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 201, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        pcargoLayout.setVerticalGroup(
            pcargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pcargoLayout.createSequentialGroup()
                .addGroup(pcargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pcargoLayout.createSequentialGroup()
                        .addGroup(pcargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pcargoLayout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(jLabel1)
                                .addGap(34, 34, 34))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pcargoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(txtidcargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtcargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addGroup(pcargoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtprecioCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addComponent(btnRegistrar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEnviarCargo))
                    .addGroup(pcargoLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(295, Short.MAX_VALUE))
        );

        panel.addTab("Cargo", pcargo);

        parea.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("ID:");

        jLabel4.setText("Nombre:");

        btnregistrarArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        btnregistrarArea.setText("Registrar");
        btnregistrarArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregistrarAreaActionPerformed(evt);
            }
        });

        tablaArea.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Area"
            }
        ));
        tablaArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaAreaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaArea);

        btnModificarArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar (1).png"))); // NOI18N
        btnModificarArea.setText("Modificar");
        btnModificarArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarAreaActionPerformed(evt);
            }
        });

        btnEliminarArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        btnEliminarArea.setText("Eliminar");
        btnEliminarArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarAreaActionPerformed(evt);
            }
        });

        btnBuscarArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        btnBuscarArea.setText("Buscar");
        btnBuscarArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarAreaActionPerformed(evt);
            }
        });

        btnEnviarArea.setText("Enviar");
        btnEnviarArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarAreaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pareaLayout = new javax.swing.GroupLayout(parea);
        parea.setLayout(pareaLayout);
        pareaLayout.setHorizontalGroup(
            pareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pareaLayout.createSequentialGroup()
                .addGroup(pareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pareaLayout.createSequentialGroup()
                            .addGap(76, 76, 76)
                            .addComponent(txtidarea, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pareaLayout.createSequentialGroup()
                            .addGap(31, 31, 31)
                            .addGroup(pareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3))))
                    .addGroup(pareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnEnviarArea, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuscarArea, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminarArea, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnModificarArea, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnregistrarArea, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                        .addComponent(txtarea, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 179, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        pareaLayout.setVerticalGroup(
            pareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pareaLayout.createSequentialGroup()
                .addGroup(pareaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pareaLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtidarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnregistrarArea)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificarArea)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarArea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscarArea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEnviarArea))
                    .addGroup(pareaLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(304, Short.MAX_VALUE))
        );

        panel.addTab("Area", parea);

        Pnomina.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Empleado"));

        jLabel17.setText("Doc:");

        btnBuscarEN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        btnBuscarEN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarENActionPerformed(evt);
            }
        });

        jLabel18.setText("Id:");

        jLabel19.setText("Nombre:");

        jLabel20.setText("Apellido:");

        jLabel21.setText("IdCargo:");

        jLabel22.setText("Cargo:");

        jLabel23.setText("Precio Cargo:");

        txtapeempleadoN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtapeempleadoNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18))
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtdocemopleadoN, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(txtidempleadoN, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtnomEmpleadoN)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(txtapeempleadoN, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(btnBuscarEN, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtprecioN, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel22))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtidcargoN)
                                    .addComponent(txtcargoN, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtdocemopleadoN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)))
                    .addComponent(btnBuscarEN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtidempleadoN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtnomEmpleadoN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtapeempleadoN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtidcargoN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtcargoN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtprecioN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Nomina"));

        jLabel24.setText("fecha:");

        jLabel25.setText("Cant. Trabajo:");

        jLabel26.setText("Total Pagar:");

        btnCalcular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/calcular.png"))); // NOI18N
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        jLabel27.setText("ID");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcantTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(btnCalcular, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtidnomina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24)
                            .addComponent(dateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txtcantTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnCalcular, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtidnomina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Acciones"));

        btnRegistrarNomina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        btnRegistrarNomina.setText("Registrar");
        btnRegistrarNomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarNominaActionPerformed(evt);
            }
        });

        btnEditarNomina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar (1).png"))); // NOI18N
        btnEditarNomina.setText("Editar");
        btnEditarNomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarNominaActionPerformed(evt);
            }
        });

        btnBuscarNomina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        btnBuscarNomina.setText("Buscar");
        btnBuscarNomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarNominaActionPerformed(evt);
            }
        });

        btnEliminarNomina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        btnEliminarNomina.setText("Eliminar");
        btnEliminarNomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarNominaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBuscarNomina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRegistrarNomina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarNomina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(btnEliminarNomina)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrarNomina)
                    .addComponent(btnEditarNomina))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarNomina)
                    .addComponent(btnEliminarNomina))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablanomina.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablanomina.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablanominaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablanomina);

        javax.swing.GroupLayout PnominaLayout = new javax.swing.GroupLayout(Pnomina);
        Pnomina.setLayout(PnominaLayout);
        PnominaLayout.setHorizontalGroup(
            PnominaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnominaLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(PnominaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PnominaLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addGroup(PnominaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 166, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PnominaLayout.setVerticalGroup(
            PnominaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnominaLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(PnominaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PnominaLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(154, Short.MAX_VALUE))
        );

        panel.addTab("Nomina", Pnomina);

        Ppagos.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Nomina"));

        jLabel29.setText("Total:");

        jLabel30.setText("IDPago:");

        btnCalcularPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/calcular.png"))); // NOI18N
        btnCalcularPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularPagoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(18, 18, 18)
                        .addComponent(txttotalPago, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtidpago, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCalcularPago)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtidpago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txttotalPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(btnCalcularPago)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Empleado"));

        jLabel31.setText("ID:");

        jLabel32.setText("Nombre:");

        jLabel33.setText("Apellido:");

        jLabel34.setText("IdCargo:");

        jLabel35.setText("Cargo:");

        txtcargop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcargopActionPerformed(evt);
            }
        });

        jLabel38.setText("Doc:");

        btnbuscarep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        btnbuscarep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(18, 18, 18)
                        .addComponent(txtidcargop))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(28, 28, 28)
                        .addComponent(txtcargop))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32)
                            .addComponent(jLabel33))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtapellidop, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtempleadop, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                    .addComponent(txtidempleadop)
                                    .addComponent(txtdocumentop, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnbuscarep, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(txtdocumentop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnbuscarep, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(txtidempleadop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txtempleadop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtapellidop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtidcargop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtcargop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel36.setText("Fecha Inicio:");

        jLabel37.setText("Fecha Fin:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(104, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnregistrarPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        btnregistrarPago.setText("Registrar");
        btnregistrarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregistrarPagoActionPerformed(evt);
            }
        });

        btnEditarPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar (1).png"))); // NOI18N
        btnEditarPago.setText("Editar");
        btnEditarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarPagoActionPerformed(evt);
            }
        });

        btnEliminarPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        btnEliminarPago.setText("Eliminar");
        btnEliminarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPagoActionPerformed(evt);
            }
        });

        btnNuevoPago.setText("Nuevo");
        btnNuevoPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoPagoActionPerformed(evt);
            }
        });

        btnGenerarPDFPAGO.setText("PDF");
        btnGenerarPDFPAGO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarPDFPAGOActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnNuevoPago)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnregistrarPago)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarPago)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarPago)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGenerarPDFPAGO)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNuevoPago)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEliminarPago)
                        .addComponent(btnregistrarPago)
                        .addComponent(btnEditarPago)
                        .addComponent(btnGenerarPDFPAGO)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablapago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablapago.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablapagoMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablapago);

        javax.swing.GroupLayout PpagosLayout = new javax.swing.GroupLayout(Ppagos);
        Ppagos.setLayout(PpagosLayout);
        PpagosLayout.setHorizontalGroup(
            PpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PpagosLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(PpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PpagosLayout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );
        PpagosLayout.setVerticalGroup(
            PpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PpagosLayout.createSequentialGroup()
                .addGroup(PpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PpagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(PpagosLayout.createSequentialGroup()
                            .addGap(23, 23, 23)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(40, 40, 40)))
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
        );

        panel.addTab("Pagos", Ppagos);

        Pdatos.setBackground(new java.awt.Color(255, 255, 255));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel28.setText("RUC:");

        jLabel39.setText("Razon Social:");

        jLabel40.setText("Telefono:");

        jLabel41.setText("Direccion:");

        jLabel42.setText("Correo:");

        jLabel43.setText("Nombre:");

        lblImagen.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnCargarImagen.setText("Cargar Imagen");
        btnCargarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarImagenActionPerformed(evt);
            }
        });

        txtIdDato.setText("2");

        btnEditardatos.setText("Editar");
        btnEditardatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditardatosActionPerformed(evt);
            }
        });

        btnRegistrarE.setText("Registrar");
        btnRegistrarE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarEActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel15Layout.createSequentialGroup()
                            .addComponent(jLabel43)
                            .addGap(40, 40, 40)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtempresa, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel15Layout.createSequentialGroup()
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel39)
                                .addComponent(jLabel40)
                                .addComponent(jLabel41)
                                .addComponent(jLabel42))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtRs)
                                .addComponent(txttelefonoE)
                                .addComponent(txtdireccionE)
                                .addComponent(txtcorreoE))))
                    .addComponent(jLabel28))
                .addGap(18, 18, 18)
                .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtIdDato, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRegistrarE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditardatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCargarImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel43)
                            .addComponent(txtempresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(txtRs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(txttelefonoE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(txtdireccionE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(txtcorreoE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnCargarImagen)
                                    .addComponent(txtIdDato, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRegistrarE)
                                .addGap(12, 12, 12)
                                .addComponent(btnEditardatos)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lblImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabladatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Empresa", "RUC", "Razon S.", "Telefono", "Direccion", "Correo", "Imagen"
            }
        ));
        tabladatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabladatosMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tabladatos);

        javax.swing.GroupLayout PdatosLayout = new javax.swing.GroupLayout(Pdatos);
        Pdatos.setLayout(PdatosLayout);
        PdatosLayout.setHorizontalGroup(
            PdatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PdatosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(PdatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        PdatosLayout.setVerticalGroup(
            PdatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PdatosLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
        );

        panel.addTab("Datos", Pdatos);

        pusuarios.setBackground(new java.awt.Color(255, 255, 255));

        jLabel44.setText("ID");

        jLabel45.setText("Nombre");

        jLabel46.setText("Usuario");

        jLabel47.setText("Password");

        jLabel48.setText("Tipo");

        cbotipousuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Jefe", "Empleado" }));

        btnGuardarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        btnGuardarUsuario.setText("Registrar");
        btnGuardarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarUsuarioActionPerformed(evt);
            }
        });

        usuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        usuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usuariosMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(usuarios);

        btnEditarusuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar (1).png"))); // NOI18N
        btnEditarusuario.setText("Modificar");
        btnEditarusuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarusuarioActionPerformed(evt);
            }
        });

        btnElimarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        btnElimarUsuario.setText("Eliminar");
        btnElimarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElimarUsuarioActionPerformed(evt);
            }
        });

        btnBuscarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buscar.png"))); // NOI18N
        btnBuscarUsuario.setText("Buscar");
        btnBuscarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarUsuarioActionPerformed(evt);
            }
        });

        jLabel60.setText("Doc_empleado");

        btnBuscarEU.setText("...");
        btnBuscarEU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarEUActionPerformed(evt);
            }
        });

        jLabel61.setText("Id empleado");

        javax.swing.GroupLayout pusuariosLayout = new javax.swing.GroupLayout(pusuarios);
        pusuarios.setLayout(pusuariosLayout);
        pusuariosLayout.setHorizontalGroup(
            pusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pusuariosLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addComponent(jLabel46)
                    .addComponent(jLabel47)
                    .addComponent(jLabel48))
                .addGap(24, 24, 24)
                .addGroup(pusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtidusuario)
                    .addComponent(txtusuario)
                    .addComponent(cbotipousuario, 0, 178, Short.MAX_VALUE)
                    .addComponent(txtpass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pusuariosLayout.createSequentialGroup()
                        .addComponent(btnBuscarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnElimarUsuario))
                    .addGroup(pusuariosLayout.createSequentialGroup()
                        .addComponent(btnEditarusuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pusuariosLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(pusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel60)
                            .addComponent(jLabel45)
                            .addComponent(jLabel61))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtnomusuario, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(txtdocEmpleadoU)
                            .addComponent(txtidempleadoU))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarEU, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(176, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pusuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );
        pusuariosLayout.setVerticalGroup(
            pusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pusuariosLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(pusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txtidusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnElimarUsuario)
                    .addComponent(btnBuscarUsuario))
                .addGap(12, 12, 12)
                .addGroup(pusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditarusuario)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)
                    .addComponent(btnGuardarUsuario))
                .addGap(18, 18, 18)
                .addGroup(pusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pusuariosLayout.createSequentialGroup()
                        .addGroup(pusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47)
                            .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pusuariosLayout.createSequentialGroup()
                        .addGroup(pusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60)
                            .addComponent(txtdocEmpleadoU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscarEU))
                        .addGap(18, 18, 18)))
                .addGroup(pusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbotipousuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(txtnomusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addGap(18, 18, 18)
                .addGroup(pusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(txtidempleadoU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(192, Short.MAX_VALUE))
        );

        panel.addTab("Usuarios", pusuarios);

        Pasistencia.setBackground(new java.awt.Color(255, 255, 255));

        jLabel55.setText("Id:");

        jLabel57.setText("Id empelado:");

        jLabel58.setText("Nombre Empleado:");

        jLabel59.setText("Tipo:");

        txttipoAsistencia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entrada", "Salida" }));

        btnregistrarAsistencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        btnregistrarAsistencia.setText("Registrar");
        btnregistrarAsistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnregistrarAsistenciaActionPerformed(evt);
            }
        });

        TablaAsistencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane8.setViewportView(TablaAsistencias);

        btnPDFAsistencia.setText("PDF");
        btnPDFAsistencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFAsistenciaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PasistenciaLayout = new javax.swing.GroupLayout(Pasistencia);
        Pasistencia.setLayout(PasistenciaLayout);
        PasistenciaLayout.setHorizontalGroup(
            PasistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PasistenciaLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(PasistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PasistenciaLayout.createSequentialGroup()
                        .addGroup(PasistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(jLabel57)
                            .addComponent(jLabel58)
                            .addComponent(jLabel59))
                        .addGap(37, 37, 37)
                        .addGroup(PasistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtidAsistencia)
                            .addComponent(txtidempleadoA)
                            .addComponent(txtnomempleadoA)
                            .addComponent(txttipoAsistencia, 0, 224, Short.MAX_VALUE))
                        .addGap(42, 42, 42)
                        .addGroup(PasistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnregistrarAsistencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPDFAsistencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        PasistenciaLayout.setVerticalGroup(
            PasistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PasistenciaLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(PasistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(txtidAsistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnregistrarAsistencia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PasistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(txtidempleadoA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPDFAsistencia))
                .addGap(18, 18, 18)
                .addGroup(PasistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(txtnomempleadoA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PasistenciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(txttipoAsistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(203, Short.MAX_VALUE))
        );

        panel.addTab("Asistencia", Pasistencia);

        tablaBebidas1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(tablaBebidas1);

        javax.swing.GroupLayout MenuBebidasLayout = new javax.swing.GroupLayout(MenuBebidas);
        MenuBebidas.setLayout(MenuBebidasLayout);
        MenuBebidasLayout.setHorizontalGroup(
            MenuBebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuBebidasLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
        );
        MenuBebidasLayout.setVerticalGroup(
            MenuBebidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuBebidasLayout.createSequentialGroup()
                .addContainerGap(221, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139))
        );

        panel.addTab("MenuBebidas", MenuBebidas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        Login m=new Login();
        m.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        // TODO add your handling code here:
        panel.setSelectedComponent(pusuarios);
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
        // TODO add your handling code here:
        panel.setSelectedComponent(pempleados);
    }//GEN-LAST:event_btnEmpleadosActionPerformed

    private void btnAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAreaActionPerformed
        // TODO add your handling code here:
        panel.setSelectedComponent(parea);
    }//GEN-LAST:event_btnAreaActionPerformed

    private void btnCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargoActionPerformed
        // TODO add your handling code here:
        panel.setSelectedComponent(pcargo);
    }//GEN-LAST:event_btnCargoActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // TODO add your handling code here:
        cr.setNombre(txtcargo.getText());
        cr.setPrecio(Double.parseDouble(txtprecioCargo.getText()));
        if(daoC.insertar(cr)){
            JOptionPane.showMessageDialog(null, "Cargo registrado con exito");
            limpiarDatosCargo();
            limpiarTablaCargo();
            listar();
            cantCarogs();
        }else{
            JOptionPane.showMessageDialog(null, "Error Al Registrar el Cargo");
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void tablacargosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablacargosMouseClicked
        // TODO add your handling code here:
        int fila=tablacargos.getSelectedRow();
        txtidcargo.setText(tablacargos.getValueAt(fila, 0).toString());
        txtcargo.setText(tablacargos.getValueAt(fila, 1).toString());
        txtprecioCargo.setText(tablacargos.getValueAt(fila, 2).toString());
    }//GEN-LAST:event_tablacargosMouseClicked

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        int fila=tablacargos.getSelectedRow();
        if(fila==-1){
            JOptionPane.showMessageDialog(null, "Seleccione un cargo");
        }else{
            cr.setId(Integer.parseInt(txtidcargo.getText()));
            cr.setNombre(txtcargo.getText());
            cr.setPrecio(Double.parseDouble(txtprecioCargo.getText()));
            if(daoC.editar(cr)){
                JOptionPane.showMessageDialog(null, "se modifico con exito");
                limpiarDatosCargo();
                limpiarTablaCargo();
                listar();
            }else{
                JOptionPane.showMessageDialog(null, "Error al modificar");
            }
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        if(!txtidcargo.getText().isEmpty()){
            int confirmacion=JOptionPane.showConfirmDialog(rootPane, "¿Seguro de eliminar el cargo?","Confirmar",2);
            if(confirmacion==0){
                cr.setId(Integer.parseInt(txtidcargo.getText()));
                daoC.elimiar(cr);
                limpiarDatosCargo();
                limpiarTablaCargo();
                listar();
                cantCarogs();
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        cr.setId(Integer.parseInt(txtidcargo.getText()));
        if(daoC.Buscar(cr)){
            txtidcargo.setText(cr.getId()+"");
            txtcargo.setText(cr.getNombre());
            txtprecioCargo.setText(cr.getPrecio()+"");
        }else{
            JOptionPane.showMessageDialog(null, "El Cargo No Existe");
            txtidcargo.setText("");
            txtcargo.setText("");
            txtprecioCargo.setText("");
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnregistrarAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregistrarAreaActionPerformed
        // TODO add your handling code here:
        ar.setNomArea(txtarea.getText());
        if(daoA.insertar(ar)){
            JOptionPane.showMessageDialog(null, "Area registrado con exito");
            limpiarDatosArea();
            limpiarTablaArea();
            listarArea();
            cantAreas();
        }else{
            JOptionPane.showMessageDialog(null, "Error Al Registrar el Area");
        }
    }//GEN-LAST:event_btnregistrarAreaActionPerformed

    private void btnModificarAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarAreaActionPerformed
        // TODO add your handling code here:
        int fila=tablaArea.getSelectedRow();
        if(fila==-1){
            JOptionPane.showMessageDialog(null, "Seleccione un Area");
        }else{
            ar.setIdarea(Integer.parseInt(txtidarea.getText()));
            ar.setNomArea(txtarea.getText());
            if(daoA.editar(ar)){
                JOptionPane.showMessageDialog(null, "se modifico con exito");
                limpiarDatosArea();
                limpiarTablaArea();
                listarArea();
            }else{
                JOptionPane.showMessageDialog(null, "Error al modificar");
            }
        }
    }//GEN-LAST:event_btnModificarAreaActionPerformed

    private void tablaAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaAreaMouseClicked
        // TODO add your handling code here:
        int fila=tablaArea.getSelectedRow();
        txtidarea.setText(tablaArea.getValueAt(fila, 0).toString());
        txtarea.setText(tablaArea.getValueAt(fila, 1).toString());
    }//GEN-LAST:event_tablaAreaMouseClicked

    private void btnEliminarAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarAreaActionPerformed
        // TODO add your handling code here:
        if(!txtidarea.getText().isEmpty()){
            int confirmacion=JOptionPane.showConfirmDialog(rootPane, "¿Seguro de eliminar el area?","Confirmar",2);
            if(confirmacion==0){
                ar.setIdarea(Integer.parseInt(txtidarea.getText()));
                daoA.elimiar(ar);
                limpiarDatosArea();
                limpiarTablaArea();
                listarArea();
                cantAreas();
            }
        }
    }//GEN-LAST:event_btnEliminarAreaActionPerformed

    private void btnBuscarAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarAreaActionPerformed
        // TODO add your handling code here:
        ar.setIdarea(Integer.parseInt(txtidarea.getText()));
        if(daoA.Buscar(ar)){
            txtidarea.setText(ar.getIdarea()+"");
            txtarea.setText(ar.getNomArea());
        }else{
            JOptionPane.showMessageDialog(null, "El Area No Existe");
            txtidarea.setText("");
            txtarea.setText("");
        }
    }//GEN-LAST:event_btnBuscarAreaActionPerformed

    private void btnEnviarAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarAreaActionPerformed
        // TODO add your handling code here:
        txtidareaempleado.setText(txtidarea.getText());
        txtareaempleado.setText(txtarea.getText());
        btnEnviarArea.setEnabled(false);
        panel.setSelectedComponent(pempleados);
    }//GEN-LAST:event_btnEnviarAreaActionPerformed

    private void btnregistrarempleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregistrarempleadoActionPerformed
        // TODO add your handling code here:
        em.setNombre(txtnomempleado.getText());
        em.setApellido(txtapeempleado.getText());
        em.setTipodoc(cbotipodoc.getSelectedItem().toString());
        em.setDoc(txtdoc.getText());
        em.setIdArea(Integer.parseInt(txtidareaempleado.getText()));
        em.setIdcargo(Integer.parseInt(txtidcargoempleado.getText()));
        em.setTelefono(txttelefono.getText());
        em.setCorreo(txtcorreo.getText());
        if(daoE.insertar(em)){
            JOptionPane.showMessageDialog(null, "Empleado registrado con exito");
            limpiarDatosEmpleado();
            limpiarTablaEmpleado();
            listarEmpleado();
            cantEmpleados();
        }else{
            JOptionPane.showMessageDialog(null, "Error Al Registrar el Empleado");
        }
    }//GEN-LAST:event_btnregistrarempleadoActionPerformed

    private void btnBuscaAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaAreaActionPerformed
        // TODO add your handling code here:
        btnEnviarArea.setEnabled(true);
        panel.setSelectedComponent(parea);
        
    }//GEN-LAST:event_btnBuscaAreaActionPerformed

    private void btnEnviarCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarCargoActionPerformed
        // TODO add your handling code here:
        txtidcargoempleado.setText(txtidcargo.getText());
        txtcargoempleado.setText(txtcargo.getText());
        btnEnviarCargo.setEnabled(false);
        panel.setSelectedComponent(pempleados);
    }//GEN-LAST:event_btnEnviarCargoActionPerformed

    private void btnBuscaCarhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaCarhoActionPerformed
        // TODO add your handling code here:
        panel.setSelectedComponent(pcargo);
        btnEnviarCargo.setEnabled(true);
    }//GEN-LAST:event_btnBuscaCarhoActionPerformed

    private void tablaempleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaempleadoMouseClicked
        // TODO add your handling code here:
        int fila=tablaempleado.getSelectedRow();
        txtidempleado.setText(tablaempleado.getValueAt(fila, 0).toString());
        txtnomempleado.setText(tablaempleado.getValueAt(fila, 1).toString());
        txtapeempleado.setText(tablaempleado.getValueAt(fila, 2).toString());
        cbotipodoc.setSelectedItem(tablaempleado.getValueAt(fila, 3).toString());
        txtdoc.setText(tablaempleado.getValueAt(fila, 4).toString());
        txtidareaempleado.setText(tablaempleado.getValueAt(fila, 5).toString());
        txtareaempleado.setText(tablaempleado.getValueAt(fila, 6).toString());
        txtidcargoempleado.setText(tablaempleado.getValueAt(fila, 7).toString());
        txtcargoempleado.setText(tablaempleado.getValueAt(fila, 8).toString());
        txttelefono.setText(tablaempleado.getValueAt(fila, 9).toString());
        txtcorreo.setText(tablaempleado.getValueAt(fila, 10).toString());
    }//GEN-LAST:event_tablaempleadoMouseClicked

    private void btnModificarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEmpleadoActionPerformed
        // TODO add your handling code here:
         int fila=tablaempleado.getSelectedRow();
        if(fila==-1){
            JOptionPane.showMessageDialog(null, "Seleccione un empleado");
        }else{
            em.setId(Integer.parseInt(txtidempleado.getText()));
            em.setNombre(txtnomempleado.getText());
            em.setApellido(txtapeempleado.getText());
            em.setTipodoc(cbotipodoc.getSelectedItem().toString());
            em.setDoc(txtdoc.getText());
            em.setIdArea(Integer.parseInt(txtidareaempleado.getText()));
            em.setIdcargo(Integer.parseInt(txtidcargoempleado.getText()));
            em.setTelefono(txttelefono.getText());
            em.setCorreo(txtcorreo.getText());
            if(daoE.editar(em)){
                JOptionPane.showMessageDialog(null, "se modifico con exito");
                limpiarDatosEmpleado();
                limpiarTablaEmpleado();
                listarEmpleado();
            }else{
                JOptionPane.showMessageDialog(null, "Error al modificar");
            }
        }
    }//GEN-LAST:event_btnModificarEmpleadoActionPerformed

    private void btnBuscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarEmpleadoActionPerformed
        // TODO add your handling code here:
        em.setId(Integer.parseInt(txtidempleado.getText()));
        if(daoE.Buscar(em)){
            txtidempleado.setText(em.getId()+"");
            txtnomempleado.setText(em.getNombre());
            txtapeempleado.setText(em.getApellido());
            cbotipodoc.setSelectedItem(em.getTipodoc().toString());
            txtdoc.setText(em.getDoc());
            txttelefono.setText(em.getTelefono());
            txtcorreo.setText(em.getCorreo());
            txtidareaempleado.setText(em.getIdArea()+""); 
            txtidcargoempleado.setText(em.getIdcargo()+"");
            ar.setIdarea(Integer.parseInt(txtidareaempleado.getText()));
            daoA.Buscar(ar);
            txtareaempleado.setText(ar.getNomArea());
            cr.setId(Integer.parseInt(txtidcargoempleado.getText()));
            daoC.Buscar(cr);
            txtcargoempleado.setText(cr.getNombre());
        }else{
            JOptionPane.showMessageDialog(null, "El Empleado No Existe");
            limpiarDatosEmpleado();
        }
    }//GEN-LAST:event_btnBuscarEmpleadoActionPerformed

    private void btnEliminarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEmpleadoActionPerformed
        // TODO add your handling code here:
         if(!txtidempleado.getText().isEmpty()){
            int confirmacion=JOptionPane.showConfirmDialog(rootPane, "¿Seguro de eliminar el empleado?","Confirmar",2);
            if(confirmacion==0){
                em.setId(Integer.parseInt(txtidempleado.getText()));
                daoE.elimiar(em);
                limpiarDatosEmpleado();
                limpiarDatosEmpleado();
                listarEmpleado();
                cantEmpleados();
            }
        }
    }//GEN-LAST:event_btnEliminarEmpleadoActionPerformed

    private void btnRegistrarNominaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarNominaActionPerformed
        // TODO add your handling code here:
        Calendar cal;
        int d,m,a;
        cal=dateFecha.getCalendar();
        d=cal.get(Calendar.DAY_OF_MONTH);
        m=cal.get(Calendar.MONTH);
        a=cal.get(Calendar.YEAR)-1900;
        no.setFecha(new Date(a,m,d));
        
        no.setCtrabajo(Integer.parseInt(txtcantTrabajo.getText()));
        no.setTotal(Double.parseDouble(txttotal.getText()));
        no.setIdempleado(Integer.parseInt(txtidempleadoN.getText()));
        no.setIdcargo(Integer.parseInt(txtidcargoN.getText()));
        no.setEstado("pendiente");
        if(daoN.insertar(no)){
            JOptionPane.showMessageDialog(null, "Nomina registrada con exito");
            limpiarDatosNomina();
            limpiarTablaNomina();
             listarNomina();
             cantNominaPendientes();
             cantNominasT();
        }else{
            JOptionPane.showMessageDialog(null, "Error Al Registrar la Nomina");
        }
    }//GEN-LAST:event_btnRegistrarNominaActionPerformed

    private void btnBuscarENActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarENActionPerformed
        // TODO add your handling code here:
        em.setDoc(txtdocemopleadoN.getText());
        if(daoE.BuscarEmpleadoN(em)){
            txtidempleadoN.setText(em.getId()+"");
            txtnomEmpleadoN.setText(em.getNombre());
            txtapeempleadoN.setText(em.getApellido());
            txtidcargoN.setText(em.getIdcargo()+"");
            cr.setId(Integer.parseInt(txtidcargoN.getText()));
            daoC.Buscar(cr);
            txtcargoN.setText(cr.getNombre());
            txtprecioN.setText(cr.getPrecio()+"");
        }else{
            JOptionPane.showMessageDialog(null, "El Empleado No Existe");
            //limpiarDatosEmpleado();
        }
    }//GEN-LAST:event_btnBuscarENActionPerformed

    private void txtapeempleadoNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtapeempleadoNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtapeempleadoNActionPerformed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        // TODO add your handling code here:
        double cantidad,precio,total;
        cantidad=Double.parseDouble(txtcantTrabajo.getText());
        precio=Double.parseDouble(txtprecioN.getText());
        total=cantidad*precio;
        txttotal.setText(total+"");
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void tablanominaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablanominaMouseClicked
        // TODO add your handling code here:
         int fila=tablanomina.getSelectedRow();
        txtidnomina.setText(tablanomina.getValueAt(fila, 0).toString());
        dateFecha.setDate(Date.valueOf(tablanomina.getValueAt(fila, 1).toString()));
        txtidempleadoN.setText(tablanomina.getValueAt(fila, 2).toString());
        txtnomEmpleadoN.setText(tablanomina.getValueAt(fila, 3).toString());
        txtapeempleadoN.setText(tablanomina.getValueAt(fila, 4).toString());
        txtdocemopleadoN.setText(tablanomina.getValueAt(fila, 5).toString());
        txtidcargoN.setText(tablanomina.getValueAt(fila, 6).toString());
        txtcargoN.setText(tablanomina.getValueAt(fila, 7).toString());
        txtprecioN.setText(tablanomina.getValueAt(fila, 8).toString());
        txtcantTrabajo.setText(tablanomina.getValueAt(fila, 9).toString());
        txttotal.setText(tablanomina.getValueAt(fila, 10).toString());
    }//GEN-LAST:event_tablanominaMouseClicked

    private void btnEditarNominaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarNominaActionPerformed
        // TODO add your handling code here:
        int fila=tablanomina.getSelectedRow();
        if(fila==-1){
            JOptionPane.showMessageDialog(null, "Seleccione una nomina");
        }else{
            Calendar cal;
            int d,m,a;
            cal=dateFecha.getCalendar();
            d=cal.get(Calendar.DAY_OF_MONTH);
            m=cal.get(Calendar.MONTH);
            a=cal.get(Calendar.YEAR)-1900;
            no.setFecha(new Date(a,m,d));
            no.setCtrabajo(Integer.parseInt(txtcantTrabajo.getText()));
            no.setTotal(Double.parseDouble(txttotal.getText()));
            no.setIdempleado(Integer.parseInt(txtidempleadoN.getText()));
            no.setIdcargo(Integer.parseInt(txtidcargoN.getText()));
            no.setId(Integer.parseInt(txtidnomina.getText()));
            if(daoN.editar(no)){
                JOptionPane.showMessageDialog(null, "se modifico con exito");
                 limpiarDatosNomina();
                limpiarTablaNomina();
                listarNomina();
            }else{
                JOptionPane.showMessageDialog(null, "Error al modificar");
            }
        }
    }//GEN-LAST:event_btnEditarNominaActionPerformed

    private void btnBuscarNominaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarNominaActionPerformed
        // TODO add your handling code here:
        no.setId(Integer.parseInt(txtidnomina.getText()));
        if(daoN.Buscar(no)){
            txtidnomina.setText(no.getId()+"");
            dateFecha.setDate(Date.valueOf(no.getFecha().toString()));
            txtcantTrabajo.setText(no.getCtrabajo()+"");
            txttotal.setText(no.getTotal()+"");
            txtidempleadoN.setText(no.getIdempleado()+"");
            txtidcargoN.setText(no.getIdcargo()+"");
            em.setId(Integer.parseInt(txtidempleadoN.getText()));
            daoE.Buscar(em);
            txtdocemopleadoN.setText(em.getDoc()); 
            txtnomEmpleadoN.setText(em.getNombre());
            txtapeempleadoN.setText(em.getApellido());
            cr.setId(Integer.parseInt(txtidcargoN.getText()));
            daoC.Buscar(cr);
            txtcargoN.setText(cr.getNombre());
            txtprecioN.setText(cr.getPrecio()+"");
        }else{
            JOptionPane.showMessageDialog(null, "La nomina No Existe");
            //limpiarDatosNomina();
        }
    }//GEN-LAST:event_btnBuscarNominaActionPerformed

    private void btnEliminarNominaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarNominaActionPerformed
        // TODO add your handling code here:
         if(!txtidnomina.getText().isEmpty()){
            int confirmacion=JOptionPane.showConfirmDialog(rootPane, "¿Seguro de eliminar la nomina?","Confirmar",2);
            if(confirmacion==0){
                no.setId(Integer.parseInt(txtidnomina.getText()));
                daoN.elimiar(no);
                limpiarDatosNomina();
                listarNomina();
                cantNominaPendientes();
                cantNominasT();
            }
        }
    }//GEN-LAST:event_btnEliminarNominaActionPerformed

    private void btnregistrarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregistrarPagoActionPerformed
        // TODO add your handling code here:
        Calendar cal;
        int d,m,a;
        cal=fecha1.getCalendar();
        d=cal.get(Calendar.DAY_OF_MONTH);
        m=cal.get(Calendar.MONTH);
        a=cal.get(Calendar.YEAR)-1900;
        pa.setFecha1(new Date(a,m,d));
        
        Calendar cal1;
        int d1,m1,a1;
        cal1=fecha2.getCalendar();
        d1=cal1.get(Calendar.DAY_OF_MONTH);
        m1=cal1.get(Calendar.MONTH);
        a1=cal1.get(Calendar.YEAR)-1900;
        pa.setFecha2(new Date(a1,m1,d1));
        
        pa.setIdempleado(Integer.parseInt(txtidempleadop.getText()));
        pa.setTotal(Double.parseDouble(txttotalPago.getText()));
        
        if(daoP.insertar(pa)){
            JOptionPane.showMessageDialog(null, "Pago registrado con exito");
            
        }else{
            JOptionPane.showMessageDialog(null, "Error Al Registrar el pago");
        }
        int ide;
        String f1,f2;
        
        ide=Integer.parseInt(txtidempleadop.getText());
        f1=new SimpleDateFormat("yyyy-MM-dd").format(fecha1.getDate());
        f2=new SimpleDateFormat("yyyy-MM-dd").format(fecha2.getDate());
        
        if(daoP.editarN(ide, f1, f2,"pagado")){
            GenerarPDFpagos(ide, f1, f2);
            limpiarTablaPago();
            listarPagos();
            cantNominaPendientes();
            cantNominaPagadas();
            cantPagos();
        }else{
            JOptionPane.showMessageDialog(null, "Error editar de pendiente a pagado");
        }
    }//GEN-LAST:event_btnregistrarPagoActionPerformed

    private void btnbuscarepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarepActionPerformed
        // TODO add your handling code here:
        em.setDoc(txtdocumentop.getText());
        if(daoE.BuscarEmpleadoN(em)){
            txtidempleadop.setText(em.getId()+"");
            txtempleadop.setText(em.getNombre());
            txtapellidop.setText(em.getApellido());
            txtidcargop.setText(em.getIdcargo()+"");
            cr.setId(Integer.parseInt(txtidcargop.getText()));
            daoC.Buscar(cr);
            txtcargop.setText(cr.getNombre());
        }else{
            JOptionPane.showMessageDialog(null, "El Empleado No Existe");
            //limpiarDatosEmpleado();
        }
    }//GEN-LAST:event_btnbuscarepActionPerformed

    private void txtcargopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcargopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcargopActionPerformed

    private void btnCalcularPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularPagoActionPerformed
        // TODO add your handling code here:
        int ide;
        String f1,f2;
        
        ide=Integer.parseInt(txtidempleadop.getText());
        f1=new SimpleDateFormat("yyyy-MM-dd").format(fecha1.getDate());
        f2=new SimpleDateFormat("yyyy-MM-dd").format(fecha2.getDate()); 
        
        pa=daoP.Total(ide, f1, f2);
        if(pa.getTotal()!=0.0){
            txttotalPago.setText(pa.getTotal()+"");
        }else{
            JOptionPane.showMessageDialog(null, "Error al Calcular");
        }
    }//GEN-LAST:event_btnCalcularPagoActionPerformed

    private void tablapagoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablapagoMouseClicked
        // TODO add your handling code here:
         int fila=tablapago.getSelectedRow();
        txtidpago.setText(tablapago.getValueAt(fila, 0).toString());
        txtidempleadop.setText(tablapago.getValueAt(fila, 1).toString());
        txtempleadop.setText(tablapago.getValueAt(fila, 2).toString());
        txtapellidop.setText(tablapago.getValueAt(fila, 3).toString());
        txtdocumentop.setText(tablapago.getValueAt(fila, 4).toString());
        txtidcargop.setText(tablapago.getValueAt(fila, 5).toString());
        txtcargop.setText(tablapago.getValueAt(fila, 6).toString());
        fecha1.setDate(Date.valueOf(tablapago.getValueAt(fila, 7).toString()));
        fecha2.setDate(Date.valueOf(tablapago.getValueAt(fila, 8).toString()));
        txttotalPago.setText(tablapago.getValueAt(fila, 9).toString());
    }//GEN-LAST:event_tablapagoMouseClicked

    private void btnEditarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarPagoActionPerformed
        // TODO add your handling code here:
        int fila=tablapago.getSelectedRow();
        if(fila==-1){
            JOptionPane.showMessageDialog(null, "Seleccione un Pago");
        }else{
            pa.setIdempleado(Integer.parseInt(txtidempleadop.getText()));
            pa.setTotal(Double.parseDouble(txttotalPago.getText()));
            Calendar cal;
            int d,m,a;
            cal=fecha1.getCalendar();
            d=cal.get(Calendar.DAY_OF_MONTH);
            m=cal.get(Calendar.MONTH);
            a=cal.get(Calendar.YEAR)-1900;
            pa.setFecha1(new Date(a,m,d));
            Calendar cal1;
            int d1,m1,a1;
            cal1=fecha2.getCalendar();
            d1=cal1.get(Calendar.DAY_OF_MONTH);
            m1=cal1.get(Calendar.MONTH);
            a1=cal1.get(Calendar.YEAR)-1900;
            pa.setFecha2(new Date(a1,m1,d1));
            pa.setId(Integer.parseInt(txtidpago.getText()));
            if(daoP.editar(pa)){
                JOptionPane.showMessageDialog(null, "se modifico con exito");
                limpiarDatosPago();
                limpiarTablaPago();
                listarPagos();
                cantNominaPendientes();
            }else{
                JOptionPane.showMessageDialog(null, "Error al modificar");
            }
        }
    }//GEN-LAST:event_btnEditarPagoActionPerformed

    private void btnEliminarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPagoActionPerformed
        // TODO add your handling code here:
         if(!txtidpago.getText().isEmpty()){
            int confirmacion=JOptionPane.showConfirmDialog(rootPane, "¿Seguro de eliminar el Pago?","Confirmar",2);
            if(confirmacion==0){
                pa.setId(Integer.parseInt(txtidpago.getText()));
                daoP.elimiar(pa);
                int ide;
                String f1,f2;
                ide=Integer.parseInt(txtidempleadop.getText());
                f1=new SimpleDateFormat("yyyy-MM-dd").format(fecha1.getDate());
                f2=new SimpleDateFormat("yyyy-MM-dd").format(fecha2.getDate()); 
                if(daoP.editarN(ide, f1, f2,"pendiente")){
                limpiarDatosPago();
                limpiarTablaPago();
                listarPagos();
                cantNominaPagadas();
                cantNominaPendientes();
                cantPagos();
        }else{
            JOptionPane.showMessageDialog(null, "Error editar de pendiente a pagado");
        }
            }
        }
    }//GEN-LAST:event_btnEliminarPagoActionPerformed

    private void btnCargarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarImagenActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png", "gif");
        fileChooser.setFileFilter(extensionFilter);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            Ruta = fileChooser.getSelectedFile().getAbsolutePath();
            Image mImagen = new ImageIcon(Ruta).getImage();
            ImageIcon mIcono = new ImageIcon(mImagen.getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), 0));
            lblImagen.setIcon(mIcono);
        }
        valor="2";
    }//GEN-LAST:event_btnCargarImagenActionPerformed

    private void btnRegistrarEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarEActionPerformed
        // TODO add your handling code here:
            da.setNombre(txtempresa.getText());
            da.setRUC(txtruc.getText());
            da.setRasonS(txtRs.getText());
            da.setTelefono(txttelefonoE.getText());
            da.setDireccion(txtdireccionE.getText());
            da.setCorreo(txtcorreoE.getText());
            da.setImagen(getImagen(Ruta));
            daoD.Agregar(da);
            limpiarDatos();
            limpiarTablaDAtos();
            ListarDatos();
    }//GEN-LAST:event_btnRegistrarEActionPerformed

    private void tabladatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabladatosMouseClicked
         int fila=tabladatos.getSelectedRow();
        txtIdDato.setText(tabladatos.getValueAt(fila, 0).toString());
        txtempresa.setText(tabladatos.getValueAt(fila, 1).toString());
        txtruc.setText(tabladatos.getValueAt(fila, 2).toString());
        txtRs.setText(tabladatos.getValueAt(fila, 3).toString());
        txttelefonoE.setText(tabladatos.getValueAt(fila, 4).toString());
        txtdireccionE.setText(tabladatos.getValueAt(fila, 5).toString());
        txtcorreoE.setText(tabladatos.getValueAt(fila, 6).toString());
        da.setId(Integer.parseInt(txtIdDato.getText()));
        if(daoD.BuscarImagen(da)){
            try{
                byte[] imagen =da.getImagen();
                BufferedImage buffer=null;
                InputStream inputstream=new ByteArrayInputStream(imagen);
                buffer=ImageIO.read(inputstream);
                ImageIcon incono=new ImageIcon(buffer.getScaledInstance(180, 180, 0));
                lblImagen.setIcon(incono);
                valor="1";
            }catch (Exception e){
                lblImagen.setText("Error");
            }
        }
    }//GEN-LAST:event_tabladatosMouseClicked

    private void btnEditardatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditardatosActionPerformed
        // TODO add your handling code here:
        int fila=tabladatos.getSelectedRow();
        if(fila==-1){
            JOptionPane.showMessageDialog(null, "Seleccione los datos");
        }else{
            da.setNombre(txtempresa.getText());
            da.setRUC(txtruc.getText());
            da.setRasonS(txtRs.getText());
            da.setTelefono(txttelefonoE.getText());
            da.setDireccion(txtdireccionE.getText());
            da.setCorreo(txtcorreoE.getText());
            if(valor.equals("1")){
                da.setImagen(getImagenEditar());
            }else{
                da.setImagen(getImagen(Ruta));
            }
            da.setId(Integer.parseInt(txtIdDato.getText()));
            if(daoD.editar(da)){
                JOptionPane.showMessageDialog(null, "se modifico con exito");
                limpiarDatos();
                limpiarTablaDAtos();
                ListarDatos();
            }else{
                JOptionPane.showMessageDialog(null, "Error al modificar");
            }
        }
    }//GEN-LAST:event_btnEditardatosActionPerformed

    private void btnNuevoPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoPagoActionPerformed
        // TODO add your handling code here:
        limpiarDatosPago();
    }//GEN-LAST:event_btnNuevoPagoActionPerformed

    private void btnGenerarPDFPAGOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarPDFPAGOActionPerformed
        // TODO add your handling code here:
        int ide;
        String f1,f2;
        
        ide=Integer.parseInt(txtidempleadop.getText());
        f1=new SimpleDateFormat("yyyy-MM-dd").format(fecha1.getDate());
        f2=new SimpleDateFormat("yyyy-MM-dd").format(fecha2.getDate());
        GenerarPDFpagos(ide, f1, f2);
    }//GEN-LAST:event_btnGenerarPDFPAGOActionPerformed

    private void btnGuardarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarUsuarioActionPerformed
        // TODO add your handling code here:
        usu.setIdempleado(Integer.parseInt(txtidempleadoU.getText()));
        usu.setUsuario(txtusuario.getText());
        usu.setPassword(txtpass.getText());
        usu.setTipo(cbotipousuario.getSelectedItem().toString());
        if(daoU.insertar(usu.getIdempleado(), usu.getUsuario(), usu.getPassword(), usu.getTipo())){
            JOptionPane.showMessageDialog(null, "Usuario registrado con exito");
            limpiarDatosUsuario();
            limpiarTablaUsuario();
            //listarUsuarios();
            listarUsuarios2();
            canUsuarios();
        }else{
            JOptionPane.showMessageDialog(null, "Error Al Registrar el Usuario");
        }
    }//GEN-LAST:event_btnGuardarUsuarioActionPerformed

    private void usuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usuariosMouseClicked
        // TODO add your handling code here:
        int fila=usuarios.getSelectedRow();
        txtidusuario.setText(usuarios.getValueAt(fila, 0).toString());
        txtidempleadoU.setText(usuarios.getValueAt(fila, 1).toString());
        txtnomusuario.setText(usuarios.getValueAt(fila, 2).toString());
        txtdocEmpleadoU.setText(usuarios.getValueAt(fila, 3).toString());
        cbotipousuario.setSelectedItem(usuarios.getValueAt(fila, 6).toString());
        usu.setIdUser(Integer.parseInt(txtidusuario.getText()));
        daoU.Buscar(usu);
        txtpass.setText(usu.getPassword());
        txtusuario.setText(usu.getUsuario());
    }//GEN-LAST:event_usuariosMouseClicked

    private void btnEditarusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarusuarioActionPerformed
        // TODO add your handling code here:
        int fila=usuarios.getSelectedRow();
        if(fila==-1){
            JOptionPane.showMessageDialog(null, "Seleccione un Area");
        }else{
            usu.setIdUser(Integer.parseInt(txtidusuario.getText()));
            usu.setIdempleado(Integer.parseInt(txtidempleadoU.getText()));
            usu.setUsuario(txtusuario.getText());
            usu.setPassword(txtpass.getText());
            usu.setTipo(cbotipousuario.getSelectedItem().toString());
            if(daoU.editar(usu.getIdUser(), usu.getIdempleado(), usu.getUsuario(), usu.getPassword(), usu.getTipo())){
                JOptionPane.showMessageDialog(null, "se modifico con exito");
                limpiarDatosUsuario();
                limpiarTablaUsuario();
                //listarUsuarios();
                listarUsuarios2();
            }else{
                JOptionPane.showMessageDialog(null, "Error al modificar");
            }
        }
    }//GEN-LAST:event_btnEditarusuarioActionPerformed

    private void btnElimarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElimarUsuarioActionPerformed
        // TODO add your handling code here:
        if(!txtidusuario.getText().isEmpty()){
            int confirmacion=JOptionPane.showConfirmDialog(rootPane, "¿Seguro de eliminar el usuario?","Confirmar",2);
            if(confirmacion==0){
                usu.setIdUser(Integer.parseInt(txtidusuario.getText()));
                daoU.elimiar(usu);
                limpiarDatosUsuario();
                limpiarTablaUsuario();
                //listarUsuarios();
                listarUsuarios2();
                canUsuarios();
            }
        }
    }//GEN-LAST:event_btnElimarUsuarioActionPerformed

    private void btnBuscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarUsuarioActionPerformed
        // TODO add your handling code here:
        usu.setIdUser(Integer.parseInt(txtidusuario.getText()));
        if(daoU.Buscar(usu)){
            //txtnomusuario.setText(usu.getNombre());
            txtusuario.setText(usu.getUsuario());
            txtpass.setText(usu.getPassword());
            cbotipousuario.setSelectedItem(usu.getTipo().toString());
            
        }else{
            JOptionPane.showMessageDialog(null, "El Usuario No Existe");
            txtnomusuario.setText("");
            txtusuario.setText("");
            txtpass.setText("");
            txtidusuario.setText("");
        }
    }//GEN-LAST:event_btnBuscarUsuarioActionPerformed

    private void btnPnominaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPnominaActionPerformed
        // TODO add your handling code here:
        panel.setSelectedComponent(Pnomina);
    }//GEN-LAST:event_btnPnominaActionPerformed

    private void btnPpagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPpagosActionPerformed
        // TODO add your handling code here:
        panel.setSelectedComponent(Ppagos);
    }//GEN-LAST:event_btnPpagosActionPerformed

    private void btnPdatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdatosActionPerformed
        // TODO add your handling code here:
         panel.setSelectedComponent(Pdatos);
    }//GEN-LAST:event_btnPdatosActionPerformed

    private void btnPhomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhomeActionPerformed
        // TODO add your handling code here:
        panel.setSelectedComponent(phome);
    }//GEN-LAST:event_btnPhomeActionPerformed

    private void btnregistrarAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnregistrarAsistenciaActionPerformed
        // TODO add your handling code here:
        asis.setIdempleado(Integer.parseInt(txtidempleadoA.getText()));
        asis.setTipo(txttipoAsistencia.getSelectedItem().toString());
        String estado;
        if(daoAs.insertar(asis)){
            int idMax;
            idMax=daoAs.obtenerMaxID();
            String hora;
            hora=daoAs.obtenerHora(idMax);
            String horaEntrada="08:15:00";
            String horaSalida="17:00:00";
            if(txttipoAsistencia.getSelectedItem().equals("Entrada")){
                if(hora.compareTo(horaEntrada)<0){
                    estado="temprano";
                }else{
                    estado="tarde";
                }
            }else{
                if(hora.compareTo(horaSalida)<0){
                    estado="Normal";
                }else{
                    estado="tarde";
                }
            }
            asistencias as1=new asistencias();
            as1.setEstado(estado);
            as1.setId(idMax);
            daoAs.editarEstado(as1);
            JOptionPane.showMessageDialog(null, "Asistencia registrado con exito");
            limpiarTablaAsistencia();
            listarAsistencia();
        }else{
            JOptionPane.showMessageDialog(null, "Error Al Registrar");
        }
    }//GEN-LAST:event_btnregistrarAsistenciaActionPerformed

    private void btnPhome1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhome1ActionPerformed
        // TODO add your handling code here:
         panel.setSelectedComponent(Pasistencia);
    }//GEN-LAST:event_btnPhome1ActionPerformed

    private void btnBuscarEUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarEUActionPerformed
        // TODO add your handling code here:
        em.setDoc(txtdocEmpleadoU.getText());
        if(daoE.BuscarEmpleadoN(em)){
            txtidempleadoU.setText(em.getId()+"");
            txtnomusuario.setText(em.getNombre());
        }else{
            JOptionPane.showMessageDialog(null, "El Empleado No Existe");
        }
    }//GEN-LAST:event_btnBuscarEUActionPerformed

    private void btnPDFAsistenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFAsistenciaActionPerformed
        // TODO add your handling code here:
        Map p=new HashMap();
        p.put("idEmpleado", Integer.parseInt(txtidempleadoA.getText()));
        JasperReport report;
        JasperPrint print;
        
        try{
            report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+
                    "/src/reportes/reporteAsistencia.jrxml");
            print =JasperFillManager.fillReport(report, p,conection);
            JasperViewer view=new JasperViewer(print,false);
            view.setTitle("Reporte de asistencia");
            view.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnPDFAsistenciaActionPerformed

    private void PMenuBebidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PMenuBebidasActionPerformed
        panel.setSelectedComponent(MenuBebidas);
    }//GEN-LAST:event_PMenuBebidasActionPerformed
    
    private byte[] getImagenEditar() {
        byte[] imagen =da.getImagen();
        try{
            BufferedImage buffer=null;
            InputStream inputstream=new ByteArrayInputStream(imagen);
            buffer=ImageIO.read(inputstream);
        }catch (Exception e){
            
        }
        return imagen;
    }
    
    private byte[] getImagen(String Ruta) {
        File imagen = new File(Ruta);
        try {
            byte[] icono = new byte[(int) imagen.length()];
            InputStream input = new FileInputStream(imagen);
            input.read(icono);
            return icono;
        } catch (Exception ex) {
            return null;
        }
    }
    void limpiarDatos(){
        txtempresa.setText("");
         txtruc.setText("");
         txtRs.setText("");
         txttelefonoE.setText("");
        txtdireccionE.setText("");
         txtcorreoE.setText("");
         lblImagen.setText("");
         lblImagen.setIcon(null);
    }
    
    void limpiarDatosCargo(){
        txtidcargo.setText("");
        txtcargo.setText("");
        txtprecioCargo.setText("");
    }
    void limpiarTablaCargo(){
        for(int i=0;i<modeloCargo.getRowCount();i++){
            modeloCargo.removeRow(i);
            i=0-1;
        }
    }
    
    void limpiarDatosArea(){
        txtidarea.setText("");
        txtarea.setText("");
    }
    void limpiarTablaArea(){
        for(int i=0;i<modeloArea.getRowCount();i++){
            modeloArea.removeRow(i);
            i=0-1;
        }
    }
     void limpiarTablaUsuario(){
        for(int i=0;i<modeloUsuario.getRowCount();i++){
            modeloUsuario.removeRow(i);
            i=0-1;
        }
    }
      void limpiarTablaAsistencia(){
        for(int i=0;i<modeloAsistencias.getRowCount();i++){
            modeloAsistencias.removeRow(i);
            i=0-1;
        }
    }
    void limpiarDatosEmpleado(){
        txtidempleado.setText("");
        txtnomempleado.setText("");
        txtapeempleado.setText("");
        txtdoc.setText("");
        txttelefono.setText("");
        txtcorreo.setText("");
        txtidareaempleado.setText("");
        txtareaempleado.setText("");
        txtidcargoempleado.setText("");
        txtcargoempleado.setText("");
    }
     void limpiarDatosUsuario(){
        txtidusuario.setText("");
        txtnomusuario.setText("");
        txtusuario.setText("");
        txtpass.setText("");
    }
    void limpiarDatosNomina(){
            txtidnomina.setText("");
            txtcantTrabajo.setText("");
            txttotal.setText("");
            txtidempleadoN.setText("");
            txtidcargoN.setText("");
            txtdocemopleadoN.setText(""); 
            txtnomEmpleadoN.setText("");
            txtapeempleadoN.setText("");
            txtcargoN.setText("");
            txtprecioN.setText("");
    }
    void limpiarDatosPago(){
            txtidpago.setText("");
            txttotalPago.setText("");
            txtdocumentop.setText("");
            txtidempleadop.setText("");
            txtempleadop.setText("");
            txtapellidop.setText(""); 
            txtidcargop.setText("");
            txtcargop.setText("");
    }
    void limpiarTablaEmpleado(){
        for(int i=0;i<modeloEmpleado.getRowCount();i++){
            modeloEmpleado.removeRow(i);
            i=0-1;
        }
    }
    void limpiarTablaNomina(){
        for(int i=0;i<modeloNomina.getRowCount();i++){
            modeloNomina.removeRow(i);
            i=0-1;
        }
    }
    void limpiarTablaPago(){
        for(int i=0;i<modeloPago.getRowCount();i++){
            modeloPago.removeRow(i);
            i=0-1;
        }
    }
     void limpiarTablaDAtos(){
        for(int i=0;i<modeloDatos.getRowCount();i++){
            modeloDatos.removeRow(i);
            i=0-1;
        }
    }
     
     private Connection conection=new conexion().conectar();
     
    void GenerarPDFpagos(int id,String f1,String f2){
        Map p=new HashMap();
        p.put("idempleado", id);
        p.put("fecha1", f1);
        p.put("fecha2", f2);
        JasperReport report;
        JasperPrint print;
        
        try{
            report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+
                    "/src/reportes/pagosEmpleado.jrxml");
            print =JasperFillManager.fillReport(report, p,conection);
            JasperViewer view=new JasperViewer(print,false);
            view.setTitle("Comprobante de pago");
            view.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
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
            FlatArcOrangeIJTheme.setup();
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MenuBebidas;
    private javax.swing.JButton PMenuBebidas;
    private javax.swing.JPanel Pasistencia;
    private javax.swing.JPanel Pdatos;
    private javax.swing.JPanel Pnomina;
    private javax.swing.JPanel Ppagos;
    private javax.swing.JTable TablaAsistencias;
    private javax.swing.JLabel Usuarios;
    public static javax.swing.JButton btnArea;
    private javax.swing.JButton btnBuscaArea;
    private javax.swing.JButton btnBuscaCarho;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarArea;
    private javax.swing.JButton btnBuscarEN;
    private javax.swing.JButton btnBuscarEU;
    private javax.swing.JButton btnBuscarEmpleado;
    private javax.swing.JButton btnBuscarNomina;
    private javax.swing.JButton btnBuscarUsuario;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnCalcularPago;
    private javax.swing.JButton btnCargarImagen;
    public static javax.swing.JButton btnCargo;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEditarNomina;
    private javax.swing.JButton btnEditarPago;
    private javax.swing.JButton btnEditardatos;
    private javax.swing.JButton btnEditarusuario;
    private javax.swing.JButton btnElimarUsuario;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarArea;
    private javax.swing.JButton btnEliminarEmpleado;
    private javax.swing.JButton btnEliminarNomina;
    private javax.swing.JButton btnEliminarPago;
    public static javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnEnviarArea;
    private javax.swing.JButton btnEnviarCargo;
    private javax.swing.JButton btnGenerarPDFPAGO;
    private javax.swing.JButton btnGuardarUsuario;
    private javax.swing.JButton btnModificarArea;
    private javax.swing.JButton btnModificarEmpleado;
    private javax.swing.JButton btnNuevoPago;
    private javax.swing.JButton btnPDFAsistencia;
    public static javax.swing.JButton btnPdatos;
    public static javax.swing.JButton btnPhome;
    public static javax.swing.JButton btnPhome1;
    public static javax.swing.JButton btnPnomina;
    public static javax.swing.JButton btnPpagos;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnRegistrarE;
    private javax.swing.JButton btnRegistrarNomina;
    public static javax.swing.JButton btnUsuarios;
    private javax.swing.JButton btnbuscarep;
    private javax.swing.JButton btnregistrarArea;
    private javax.swing.JButton btnregistrarAsistencia;
    private javax.swing.JButton btnregistrarPago;
    private javax.swing.JButton btnregistrarempleado;
    private javax.swing.JComboBox<String> cbotipodoc;
    private javax.swing.JComboBox<String> cbotipousuario;
    private com.toedter.calendar.JDateChooser dateFecha;
    private com.toedter.calendar.JDateChooser fecha1;
    private com.toedter.calendar.JDateChooser fecha2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JTabbedPane panel;
    private javax.swing.JPanel parea;
    private javax.swing.JPanel pcargo;
    private javax.swing.JPanel pempleados;
    private javax.swing.JPanel phome;
    private javax.swing.JPanel pusuarios;
    private javax.swing.JTable tablaArea;
    private javax.swing.JTable tablaBebidas1;
    private javax.swing.JTable tablacargos;
    private javax.swing.JTable tabladatos;
    private javax.swing.JTable tablaempleado;
    private javax.swing.JTable tablanomina;
    private javax.swing.JTable tablapago;
    private javax.swing.JLabel txtIdDato;
    private javax.swing.JTextField txtRs;
    private javax.swing.JTextField txtapeempleado;
    private javax.swing.JTextField txtapeempleadoN;
    private javax.swing.JTextField txtapellidop;
    private javax.swing.JTextField txtarea;
    private javax.swing.JTextField txtareaempleado;
    private javax.swing.JLabel txtcantAreas;
    private javax.swing.JLabel txtcantCargos;
    private javax.swing.JLabel txtcantEmpleados;
    private javax.swing.JLabel txtcantNP;
    private javax.swing.JLabel txtcantNPa;
    private javax.swing.JLabel txtcantNT;
    private javax.swing.JTextField txtcantTrabajo;
    private javax.swing.JLabel txtcantpagos;
    private javax.swing.JLabel txtcantusuarios;
    private javax.swing.JTextField txtcargo;
    private javax.swing.JTextField txtcargoN;
    private javax.swing.JTextField txtcargoempleado;
    private javax.swing.JTextField txtcargop;
    private javax.swing.JTextField txtcorreo;
    private javax.swing.JTextField txtcorreoE;
    private javax.swing.JTextField txtdireccionE;
    private javax.swing.JTextField txtdoc;
    private javax.swing.JTextField txtdocEmpleadoU;
    private javax.swing.JTextField txtdocemopleadoN;
    private javax.swing.JTextField txtdocumentop;
    private javax.swing.JTextField txtempleadop;
    private javax.swing.JTextField txtempresa;
    private javax.swing.JTextField txtidAsistencia;
    private javax.swing.JTextField txtidarea;
    private javax.swing.JTextField txtidareaempleado;
    private javax.swing.JTextField txtidcargo;
    private javax.swing.JTextField txtidcargoN;
    private javax.swing.JTextField txtidcargoempleado;
    private javax.swing.JTextField txtidcargop;
    private javax.swing.JTextField txtidempleado;
    public static javax.swing.JTextField txtidempleadoA;
    private javax.swing.JTextField txtidempleadoN;
    private javax.swing.JTextField txtidempleadoU;
    private javax.swing.JTextField txtidempleadop;
    private javax.swing.JTextField txtidnomina;
    private javax.swing.JTextField txtidpago;
    private javax.swing.JTextField txtidusuario;
    private javax.swing.JTextField txtnomEmpleadoN;
    private javax.swing.JTextField txtnomempleado;
    public static javax.swing.JTextField txtnomempleadoA;
    private javax.swing.JTextField txtnomusuario;
    private javax.swing.JPasswordField txtpass;
    private javax.swing.JTextField txtprecioCargo;
    private javax.swing.JTextField txtprecioN;
    private javax.swing.JTextField txtruc;
    private javax.swing.JTextField txttelefono;
    private javax.swing.JTextField txttelefonoE;
    private javax.swing.JComboBox<String> txttipoAsistencia;
    private javax.swing.JTextField txttotal;
    private javax.swing.JTextField txttotalPago;
    private javax.swing.JTextField txtusuario;
    private javax.swing.JTable usuarios;
    // End of variables declaration//GEN-END:variables
}
