/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ventana;


import clases.Cliente;
import clases.Usuario;
import clases.File;
import clases.FileCCliente;
import clases.FilePieza;
import clases.FileReparacion;
import clases.FileVehiculo;
import clases.Pieza;
import clases.Reparacion;
import clases.Vehiculo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Principal extends javax.swing.JFrame {
    //Creacion de objetos
    private Usuario us;
    private Cliente cl;
    private Vehiculo car;
    private Reparacion rp;
    private Pieza pz;
    
    //instancia de clases
    File file;
    FileCCliente fileCliente;
    FileVehiculo fileVehiculo;
    FileReparacion fileReparacion;
    FilePieza filePieza;
    boolean error = false;
    boolean close=false; //permite bloquear paneles cuando ya se desbloqueo una vez
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    
    public Principal() {
        initComponents();
        setLocationRelativeTo(null);
        //incializacion de clases
        file=new File();
        fileCliente = new FileCCliente();
        fileVehiculo = new FileVehiculo();
        fileReparacion = new FileReparacion();
        filePieza =  new FilePieza();
        
        BloquearPaneles();
                
        botonSalvar.setEnabled(false);
        botonSalvarCliente.setEnabled(false);
        botonSalvarVehiculo.setEnabled(false);
        botonSalvarReparacion.setEnabled(false);
        botonSalvarPieza.setEnabled(false);
       
    }

    public void Habilitar(){
        cajaNombre.setEditable(true);
        cajaAP.setEditable(true);
        cajaAM.setEditable(true);
        cajaTelefono.setEditable(true);
        cajaUserName.setEnabled(true);
        comboPerfil.setEnabled(true);
        cajaDireccion.setEnabled(true);
        cajaPW.setEnabled(true);

    }
    
    public void DesHabilitar(){
        //deshabilita las cajas de registro
        cajaID.setEditable(false);
        cajaNombre.setEditable(false);
        cajaAP.setEditable(false);
        cajaAM.setEditable(false);
        cajaTelefono.setEditable(false);
        cajaUserName.setEnabled(false);
        comboPerfil.setEnabled(false);
        cajaDireccion.setEnabled(false);
        cajaPW.setEnabled(false);  
    }
     
    public void LimpiarCajas(){
        cajaBuscar.setText("");
        cajaID.setText("");
        cajaNombre.setText("");
        cajaAP.setText("");
        cajaAM.setText("");
        cajaTelefono.setText("");
        cajaUserName.setText("");
        comboPerfil.setSelectedItem(null);
        cajaDireccion.setText("");
        cajaPW.setText("");
    }
    
    public void LimpiarCajasClientes(){
        cajaBuscarCliente.setText("");
        comboPerfilClientes.setSelectedItem(null);
        cajaIDUsuario.setText("");
        cajaIDCliente.setText("");
        cajaNombreCliente.setText("");
        cajaAPCliente.setText("");
        cajaAMCliente.setText("");
    }
    
    public void HabilitarCajasClientes(){
        cajaNombreCliente.setEditable(true);
        cajaAPCliente.setEditable(true);
        cajaAMCliente.setEditable(true);
    }
    
    public void LimpiarCajasVehiculos(){
        cajaBuscarVehiculo.setText("");
        comboClientes.setSelectedItem(null);
        cajaIDVehiculo.setText("");
        cajaMatriculaVehiculo.setText("");
        cajaMarcaVehiculo.setText("");
        cajaModeloVehiculo.setText("");
        cajaIDClienteV.setText("");
        fechaVehiculo.setDate(null);
    }
    
    public void LimpiarCajasReparaciones(){
        cajaBuscarReparacion.setText("");
        cajaIDReparacion.setText("");
        fechaEntradaReparacion.setDate(null);
        fechaSalidaReparacion.setDate(null);
        cajaFallaReparacion.setText("");
        comboVehiculos.setSelectedItem(null);
        cajaIDVehiculos.setText("");
        
        comboPiezas.setSelectedItem(null);
        cajaIDReparacionPieza.setText("");
        SpinnerPiezasReparacion.setValue(0);
    }
    
    public void LimpiarCajasPiezas(){
        cajaBuscarPieza.setText("");
        cajaIDPieza.setText("");
        areaDescripcion.setText("");
        SpinnerStock.setValue(0);         
    }
    
    public void ObtenerUsuarios(){
        comboPerfilClientes.removeAllItems();
        try {
            ArrayList<Usuario> nombres = file.Recuperar();
            
            if (nombres.isEmpty()) {
                return;
            }
            for (Usuario usuario : nombres) {
                comboPerfilClientes.addItem(usuario.getUserName());  

            }
            nombres.clear();        
        } catch (IOException ex) {
        }
    }
    
    public void ObtenerClientes(){
        comboClientes.removeAllItems();
        try {
            ArrayList<Cliente> clientes = fileCliente.RecuperarClientes();
            
            if (clientes.isEmpty()) {
                return;
            }
            for (Cliente cliente : clientes) {  
                comboClientes.addItem(cliente.getNombre()); 

            }
            clientes.clear();        
        } catch (IOException ex) {
        }
    }
    
    public void ObtenerVehiculos(){
        comboVehiculos.removeAllItems();
        try {
            ArrayList<Vehiculo> vehiculos = fileVehiculo.RecuperarVehiculos();
            
            if (vehiculos.isEmpty()) {
                return;
            }
            for (Vehiculo vehiculo : vehiculos) {  
                comboVehiculos.addItem(vehiculo.getMatricula()); 

            }
            vehiculos.clear();        
        } catch (IOException ex) {
        }
    }
    
    public void ObtenerPiezas(){
        comboPiezas.removeAllItems();
        try {
            ArrayList<Pieza> piezas = filePieza.RecuperarPiezas();
            
            if (piezas.isEmpty()) {
                return;
            }
            for (Pieza pieza : piezas) {  
                comboPiezas.addItem(pieza.getDescripcion()); 
            }
            piezas.clear();        
        } catch (IOException ex) {
        }
    }

    public void ObtenerIDUsuario(){
       if(comboPerfilClientes.getSelectedItem()!=null){
           try {
               us =  new Usuario();
               String email = comboPerfilClientes.getSelectedItem().toString();
               //System.out.print("\n"+nombre); //retorna 2 veces el mismo valor
               us.setUserName(email);
               us = file.FileSearch(us);
               cajaIDUsuario.setText(String.valueOf(us.getID()));
           } catch (IOException ex) {
              
           }
       }
    }
    
    public void ObtenerIDCliente(){
        if(comboClientes.getSelectedItem()!=null){
            try {
                cl =  new Cliente();
                String nombre = comboClientes.getSelectedItem().toString();
                //System.out.print("\n"+nombre); //retorna 2 veces el mismo valor
                cl.setNombre(nombre);
                cl = fileCliente.FileSearchCliente(cl);
                cajaIDClienteV.setText(String.valueOf(cl.getID()));
            } catch (IOException ex) {
               
            }       
       }
    }
    
    public void ObtenerIDVehiculo(){
        if(comboVehiculos.getSelectedItem()!=null){
            try {
                ArrayList<Vehiculo> vehiculos = fileVehiculo.RecuperarVehiculos();
                
                for (Vehiculo vehiculo : vehiculos) {      
                    if(vehiculo.getMatricula().equals(comboVehiculos.getSelectedItem().toString())){  
                        cajaIDVehiculos.setText(String.valueOf(vehiculo.getID()));
                    }
                }
                
            } catch (IOException ex) {
            }  
        }
    }
    
    public void ObtenerIDPieza(){
        if(comboPiezas.getSelectedItem()!=null){
            try {
                ArrayList<Pieza> piezas = filePieza.RecuperarPiezas();
                
                for (Pieza pieza : piezas) {      
                    if(pieza.getDescripcion().equals(comboPiezas.getSelectedItem().toString())){  
                        cajaIDReparacionPieza.setText(String.valueOf(pieza.getID()));
                    }
                }
                
            } catch (IOException ex) {
            }
        }
    }
    
    public void LimpiarCombos(){
        //este metodo limpiar las los combo box y sus respectivos id cada vez que se clikea en un panel
        comboPerfilClientes.setSelectedItem(null);
        cajaIDUsuario.setText("");
        comboClientes.setSelectedItem(null);
        cajaIDClienteV.setText("");
        comboPerfil.setSelectedItem(null);
        comboVehiculos.setSelectedItem(null);
        comboPiezas.setSelectedItem(null);
        cajaIDVehiculos.setText("");
        cajaIDReparacionPieza.setText("");
    }
    
    public void LimpiarGrafico(){
        panelGraficos.removeAll();
        panelGraficos.revalidate();
        panelGraficos.repaint();
    }
    
    public void BloquearPaneles(){
        MenuVentanas.setEnabledAt(1, false);
        MenuVentanas.setEnabledAt(2, false);
        MenuVentanas.setEnabledAt(3, false);
        MenuVentanas.setEnabledAt(4, false);
        MenuVentanas.setEnabledAt(5, false);
    }
    
    public void GenerarGrafico(){
        LimpiarGrafico(); //se necesita para cada vez que se llame la funcion y se actualice
        try {
            ArrayList<Pieza> piezas = filePieza.RecuperarPiezas();
            
            DefaultCategoryDataset datos = new DefaultCategoryDataset();
            
            for (Pieza pieza : piezas) {
                datos.setValue(Integer.parseInt(pieza.getStock()), "Piezas", pieza.getDescripcion());
            }
            
            JFreeChart grafico = ChartFactory.createBarChart3D(
                    "Stock de piezas",    //nombre de grafico
                    "Piezas en stock",    //nombre de las barras de las columnas
                    "Cantidad de piezas",   //nombre de la numeracion
                    datos,    //datos del grafico
                    PlotOrientation.VERTICAL,    //orientacion
                    true,    //leyenda de barras individuales por color
                    true,    //herramientas
                    false    //url de grafico
            );
            
            ChartPanel panel = new ChartPanel(grafico);
            //panel.setMouseWheelEnabled(true);
            panel.setPreferredSize(new Dimension(400,400));
            
            panelGraficos.setLayout(new BorderLayout());
            panelGraficos.add(panel,BorderLayout.NORTH);
            
            pack();
            repaint();
        } catch (IOException ex) {
            //Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MenuVentanas = new javax.swing.JTabbedPane();
        panelLogin = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cajaUsuario = new javax.swing.JTextField();
        cajaContra = new javax.swing.JTextField();
        botonAutentificar = new javax.swing.JButton();
        etiquetaTipoPerfil = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        panelUsuarios = new javax.swing.JPanel();
        cajaBuscar = new javax.swing.JTextField();
        botonBuscar = new javax.swing.JButton();
        botonSalvar = new javax.swing.JButton();
        botonLimpiar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        botonNuevo = new javax.swing.JButton();
        botonEditar = new javax.swing.JButton();
        botonRemover = new javax.swing.JButton();
        cajaAM = new javax.swing.JTextField();
        cajaTelefono = new javax.swing.JTextField();
        cajaUserName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        comboPerfil = new javax.swing.JComboBox<>();
        cajaDireccion = new javax.swing.JTextField();
        cajaPW = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cajaID = new javax.swing.JTextField();
        cajaNombre = new javax.swing.JTextField();
        cajaAP = new javax.swing.JTextField();
        cajaEjemplo = new javax.swing.JLabel();
        panelClientes = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cajaIDCliente = new javax.swing.JTextField();
        cajaNombreCliente = new javax.swing.JTextField();
        cajaAPCliente = new javax.swing.JTextField();
        cajaAMCliente = new javax.swing.JTextField();
        botonNuevoCliente = new javax.swing.JButton();
        botonSalvarCliente = new javax.swing.JButton();
        botonCancelarCliente = new javax.swing.JButton();
        botonEditarCliente = new javax.swing.JButton();
        botonRemoverCliente = new javax.swing.JButton();
        cajaBuscarCliente = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        botonBuscarCliente = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        comboPerfilClientes = new javax.swing.JComboBox<>();
        cajaIDUsuario = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        panelVehiculos = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cajaIDVehiculo = new javax.swing.JTextField();
        cajaMatriculaVehiculo = new javax.swing.JTextField();
        cajaMarcaVehiculo = new javax.swing.JTextField();
        cajaModeloVehiculo = new javax.swing.JTextField();
        botonNuevoVehiculo = new javax.swing.JButton();
        botonSalvarVehiculo = new javax.swing.JButton();
        botonCancelarVehiculo = new javax.swing.JButton();
        botonEditarVehiculo = new javax.swing.JButton();
        botonRemoverVehiculo = new javax.swing.JButton();
        cajaBuscarVehiculo = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        botonBuscarVehiculo = new javax.swing.JButton();
        cajaIDClienteV = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        fechaVehiculo = new com.toedter.calendar.JDateChooser();
        jLabel39 = new javax.swing.JLabel();
        comboClientes = new javax.swing.JComboBox<>();
        panelReparaciones = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        botonRemoverReparacion = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        cajaBuscarReparacion = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        cajaIDReparacion = new javax.swing.JTextField();
        botonBuscarReparacion = new javax.swing.JButton();
        cajaIDReparacionPieza = new javax.swing.JTextField();
        cajaFallaReparacion = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        botonNuevoReparacion = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        botonSalvarReparacion = new javax.swing.JButton();
        botonCancelarReparacion = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        botonEditarReparacion = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        SpinnerPiezasReparacion = new javax.swing.JSpinner();
        fechaEntradaReparacion = new com.toedter.calendar.JDateChooser();
        fechaSalidaReparacion = new com.toedter.calendar.JDateChooser();
        jLabel40 = new javax.swing.JLabel();
        cajaIDVehiculos = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        comboVehiculos = new javax.swing.JComboBox<>();
        comboPiezas = new javax.swing.JComboBox<>();
        panelPiezas = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        cajaIDPieza = new javax.swing.JTextField();
        botonNuevoPieza = new javax.swing.JButton();
        botonSalvarPieza = new javax.swing.JButton();
        botonCancelarPieza = new javax.swing.JButton();
        botonEditarPieza = new javax.swing.JButton();
        botonRemoverPieza = new javax.swing.JButton();
        cajaBuscarPieza = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        botonBuscarPieza = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaDescripcion = new javax.swing.JTextArea();
        SpinnerStock = new javax.swing.JSpinner();
        panelGraficos = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MenuVentanas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                MenuVentanasStateChanged(evt);
            }
        });
        MenuVentanas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuVentanasMouseClicked(evt);
            }
        });

        panelLogin.setBackground(new java.awt.Color(255, 255, 255));
        panelLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelLoginMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelLoginMousePressed(evt);
            }
        });

        jLabel2.setText("Usuario");

        jLabel3.setText("Password");

        cajaUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaUsuarioActionPerformed(evt);
            }
        });

        botonAutentificar.setText("Autentificar");
        botonAutentificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAutentificarActionPerformed(evt);
            }
        });

        etiquetaTipoPerfil.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        etiquetaTipoPerfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/user (1).gif"))); // NOI18N

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLoginLayout.createSequentialGroup()
                .addContainerGap(233, Short.MAX_VALUE)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLoginLayout.createSequentialGroup()
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(43, 43, 43)
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botonAutentificar)
                            .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cajaContra, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                .addComponent(cajaUsuario)))
                        .addGap(247, 247, 247))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLoginLayout.createSequentialGroup()
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(302, 302, 302))))
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(287, 287, 287)
                .addComponent(etiquetaTipoPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(etiquetaTipoPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cajaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(28, 28, 28)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cajaContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(27, 27, 27)
                .addComponent(botonAutentificar)
                .addGap(116, 116, 116))
        );

        MenuVentanas.addTab("Login", panelLogin);

        botonBuscar.setText("Bucar");
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });

        botonSalvar.setText("Salvar");
        botonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalvarActionPerformed(evt);
            }
        });

        botonLimpiar.setText("Limpiar");
        botonLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLimpiarActionPerformed(evt);
            }
        });

        jLabel1.setText("Ingrese ID a buscar");

        botonNuevo.setText("Nuevo");
        botonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoActionPerformed(evt);
            }
        });

        botonEditar.setText("Editar");
        botonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarActionPerformed(evt);
            }
        });

        botonRemover.setText("Remover");
        botonRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRemoverActionPerformed(evt);
            }
        });

        cajaAM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaAMActionPerformed(evt);
            }
        });

        cajaTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTelefonoActionPerformed(evt);
            }
        });

        jLabel10.setText("Perfil");

        jLabel11.setText("Direccion");

        jLabel12.setText("Password");

        comboPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Gerente", "Secretaria", "Mecanico" }));

        jLabel4.setText("ID");

        jLabel5.setText("Nombre");

        jLabel6.setText("Apellido paterno");

        jLabel7.setText("Apellido materno");

        jLabel8.setText("Telefono");

        jLabel9.setText("User name");

        cajaEjemplo.setText("ejemplo@dominio.com");

        javax.swing.GroupLayout panelUsuariosLayout = new javax.swing.GroupLayout(panelUsuarios);
        panelUsuarios.setLayout(panelUsuariosLayout);
        panelUsuariosLayout.setHorizontalGroup(
            panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuariosLayout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuariosLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(36, 36, 36)
                        .addComponent(cajaBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botonBuscar)
                        .addGap(110, 110, 110))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuariosLayout.createSequentialGroup()
                        .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelUsuariosLayout.createSequentialGroup()
                                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(23, 23, 23)
                                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cajaID)
                                    .addComponent(cajaNombre)
                                    .addComponent(cajaAP, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelUsuariosLayout.createSequentialGroup()
                                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(20, 20, 20)
                                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cajaTelefono)
                                    .addComponent(cajaAM)
                                    .addComponent(cajaUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelUsuariosLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addGap(12, 12, 12)
                                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaDireccion)
                                    .addComponent(cajaPW, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelUsuariosLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(cajaEjemplo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUsuariosLayout.createSequentialGroup()
                        .addComponent(botonNuevo)
                        .addGap(18, 18, 18)
                        .addComponent(botonSalvar)
                        .addGap(18, 18, 18)
                        .addComponent(botonLimpiar)
                        .addGap(18, 18, 18)
                        .addComponent(botonEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonRemover)
                        .addGap(146, 146, 146))))
        );
        panelUsuariosLayout.setVerticalGroup(
            panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuariosLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cajaBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBuscar))
                .addGap(26, 26, 26)
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cajaID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(cajaNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cajaAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(cajaDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cajaAM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(cajaPW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cajaTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cajaUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cajaEjemplo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonSalvar)
                    .addComponent(botonLimpiar)
                    .addComponent(botonNuevo)
                    .addComponent(botonEditar)
                    .addComponent(botonRemover))
                .addGap(37, 37, 37))
        );

        MenuVentanas.addTab("Usuario", panelUsuarios);

        jLabel13.setText("ID");

        jLabel14.setText("Nombre");

        jLabel15.setText("Apellido Paterno");

        jLabel16.setText("Apellido Materno");

        cajaNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaNombreClienteActionPerformed(evt);
            }
        });

        botonNuevoCliente.setText("Nuevo");
        botonNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoClienteActionPerformed(evt);
            }
        });

        botonSalvarCliente.setText("Salvar");
        botonSalvarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalvarClienteActionPerformed(evt);
            }
        });

        botonCancelarCliente.setText("Cancelar");
        botonCancelarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarClienteActionPerformed(evt);
            }
        });

        botonEditarCliente.setText("Editar");
        botonEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarClienteActionPerformed(evt);
            }
        });

        botonRemoverCliente.setText("Remover");
        botonRemoverCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRemoverClienteActionPerformed(evt);
            }
        });

        jLabel17.setText("Ingrese ID a Buscar:");

        botonBuscarCliente.setText("Buscar");
        botonBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarClienteActionPerformed(evt);
            }
        });

        jLabel36.setText("Usuario");

        comboPerfilClientes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboPerfilClientesItemStateChanged(evt);
            }
        });
        comboPerfilClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboPerfilClientesMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                comboPerfilClientesMousePressed(evt);
            }
        });
        comboPerfilClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPerfilClientesActionPerformed(evt);
            }
        });

        cajaIDUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaIDUsuarioActionPerformed(evt);
            }
        });

        jLabel38.setText("ID Usuario");

        javax.swing.GroupLayout panelClientesLayout = new javax.swing.GroupLayout(panelClientes);
        panelClientes.setLayout(panelClientesLayout);
        panelClientesLayout.setHorizontalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesLayout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClientesLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(31, 31, 31)
                        .addComponent(cajaBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(botonBuscarCliente)
                        .addGap(128, 128, 128))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClientesLayout.createSequentialGroup()
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel38)
                            .addComponent(jLabel36))
                        .addGap(18, 18, 18)
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cajaIDUsuario)
                            .addComponent(comboPerfilClientes, 0, 214, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cajaIDCliente)
                            .addComponent(cajaNombreCliente)
                            .addComponent(cajaAPCliente)
                            .addComponent(cajaAMCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                        .addGap(69, 69, 69))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClientesLayout.createSequentialGroup()
                        .addComponent(botonNuevoCliente)
                        .addGap(23, 23, 23)
                        .addComponent(botonSalvarCliente)
                        .addGap(18, 18, 18)
                        .addComponent(botonCancelarCliente)
                        .addGap(18, 18, 18)
                        .addComponent(botonEditarCliente)
                        .addGap(18, 18, 18)
                        .addComponent(botonRemoverCliente)
                        .addGap(155, 155, 155))))
        );
        panelClientesLayout.setVerticalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cajaBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBuscarCliente))
                .addGap(33, 33, 33)
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelClientesLayout.createSequentialGroup()
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(comboPerfilClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(cajaIDUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelClientesLayout.createSequentialGroup()
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(cajaIDCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(cajaNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(cajaAPCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cajaAMCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonNuevoCliente)
                    .addComponent(botonSalvarCliente)
                    .addComponent(botonCancelarCliente)
                    .addComponent(botonEditarCliente)
                    .addComponent(botonRemoverCliente))
                .addGap(49, 49, 49))
        );

        MenuVentanas.addTab("Clentes", panelClientes);

        jLabel18.setText("ID Vehiculo");

        jLabel19.setText("Matricula");

        jLabel20.setText("Marca");

        jLabel21.setText("Modelo");

        cajaIDVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaIDVehiculoActionPerformed(evt);
            }
        });

        cajaMatriculaVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaMatriculaVehiculoActionPerformed(evt);
            }
        });

        botonNuevoVehiculo.setText("Nuevo");
        botonNuevoVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoVehiculoActionPerformed(evt);
            }
        });

        botonSalvarVehiculo.setText("Salvar");
        botonSalvarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalvarVehiculoActionPerformed(evt);
            }
        });

        botonCancelarVehiculo.setText("Cancelar");
        botonCancelarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarVehiculoActionPerformed(evt);
            }
        });

        botonEditarVehiculo.setText("Editar");
        botonEditarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarVehiculoActionPerformed(evt);
            }
        });

        botonRemoverVehiculo.setText("Remover");
        botonRemoverVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRemoverVehiculoActionPerformed(evt);
            }
        });

        jLabel22.setText("Ingrese ID a Buscar:");

        botonBuscarVehiculo.setText("Buscar");
        botonBuscarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarVehiculoActionPerformed(evt);
            }
        });

        jLabel23.setText("ID Cliente");

        jLabel24.setText("Fecha");

        jLabel39.setText("Cliente");

        comboClientes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboClientesItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelVehiculosLayout = new javax.swing.GroupLayout(panelVehiculos);
        panelVehiculos.setLayout(panelVehiculosLayout);
        panelVehiculosLayout.setHorizontalGroup(
            panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVehiculosLayout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVehiculosLayout.createSequentialGroup()
                        .addComponent(botonNuevoVehiculo)
                        .addGap(23, 23, 23)
                        .addComponent(botonSalvarVehiculo)
                        .addGap(18, 18, 18)
                        .addComponent(botonCancelarVehiculo)
                        .addGap(18, 18, 18)
                        .addComponent(botonEditarVehiculo)
                        .addGap(18, 18, 18)
                        .addComponent(botonRemoverVehiculo)
                        .addGap(152, 152, 152))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVehiculosLayout.createSequentialGroup()
                        .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelVehiculosLayout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(18, 18, 18)
                                .addComponent(fechaVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelVehiculosLayout.createSequentialGroup()
                                .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel23))
                                .addGap(27, 27, 27)
                                .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cajaIDClienteV, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                                .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelVehiculosLayout.createSequentialGroup()
                                        .addGap(61, 61, 61)
                                        .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel20)
                                            .addComponent(jLabel21)
                                            .addComponent(jLabel19)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVehiculosLayout.createSequentialGroup()
                                        .addGap(49, 49, 49)
                                        .addComponent(jLabel18)))
                                .addGap(18, 18, 18)
                                .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cajaMatriculaVehiculo)
                                    .addComponent(cajaMarcaVehiculo)
                                    .addComponent(cajaModeloVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cajaIDVehiculo, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(114, 114, 114))))
            .addGroup(panelVehiculosLayout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addComponent(jLabel22)
                .addGap(31, 31, 31)
                .addComponent(cajaBuscarVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(botonBuscarVehiculo)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelVehiculosLayout.setVerticalGroup(
            panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVehiculosLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(cajaBuscarVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBuscarVehiculo))
                .addGap(32, 32, 32)
                .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(cajaIDVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(comboClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVehiculosLayout.createSequentialGroup()
                        .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(cajaMatriculaVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(cajaMarcaVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cajaModeloVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel23)
                        .addComponent(cajaIDClienteV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(fechaVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(panelVehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonNuevoVehiculo)
                    .addComponent(botonSalvarVehiculo)
                    .addComponent(botonCancelarVehiculo)
                    .addComponent(botonEditarVehiculo)
                    .addComponent(botonRemoverVehiculo))
                .addGap(30, 30, 30))
        );

        MenuVentanas.addTab("Vehiculos", panelVehiculos);

        jLabel25.setText("Check in");

        botonRemoverReparacion.setText("Remover");
        botonRemoverReparacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRemoverReparacionActionPerformed(evt);
            }
        });

        jLabel26.setText("Check out");

        jLabel27.setText("Falla");

        jLabel28.setText("Ingrese ID a Buscar:");

        cajaIDReparacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaIDReparacionActionPerformed(evt);
            }
        });

        botonBuscarReparacion.setText("Buscar");
        botonBuscarReparacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarReparacionActionPerformed(evt);
            }
        });

        cajaIDReparacionPieza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaIDReparacionPiezaActionPerformed(evt);
            }
        });

        jLabel29.setText("Vehiculos");

        botonNuevoReparacion.setText("Nuevo");
        botonNuevoReparacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoReparacionActionPerformed(evt);
            }
        });

        jLabel30.setText("ID Pieza");

        botonSalvarReparacion.setText("Salvar");
        botonSalvarReparacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalvarReparacionActionPerformed(evt);
            }
        });

        botonCancelarReparacion.setText("Cancelar");
        botonCancelarReparacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarReparacionActionPerformed(evt);
            }
        });

        jLabel31.setText("ID");

        botonEditarReparacion.setText("Editar");
        botonEditarReparacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarReparacionActionPerformed(evt);
            }
        });

        jLabel32.setText("Cantidad de Piezas");

        jLabel40.setText("ID Vehiculo");

        jLabel41.setText("Piezas");

        comboVehiculos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboVehiculosItemStateChanged(evt);
            }
        });
        comboVehiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboVehiculosActionPerformed(evt);
            }
        });

        comboPiezas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPiezasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelReparacionesLayout = new javax.swing.GroupLayout(panelReparaciones);
        panelReparaciones.setLayout(panelReparacionesLayout);
        panelReparacionesLayout.setHorizontalGroup(
            panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReparacionesLayout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addComponent(jLabel28)
                .addGap(31, 31, 31)
                .addComponent(cajaBuscarReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(botonBuscarReparacion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelReparacionesLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel29)
                    .addComponent(jLabel32)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41))
                .addGap(18, 18, 18)
                .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cajaIDReparacionPieza)
                    .addComponent(SpinnerPiezasReparacion, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(cajaIDVehiculos)
                    .addComponent(comboVehiculos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(comboPiezas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(78, 78, 78)
                .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel26)
                    .addComponent(jLabel25)
                    .addComponent(jLabel31))
                .addGap(18, 18, 18)
                .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cajaIDReparacion)
                    .addComponent(cajaFallaReparacion)
                    .addComponent(fechaEntradaReparacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fechaSalidaReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 63, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelReparacionesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonNuevoReparacion)
                .addGap(23, 23, 23)
                .addComponent(botonSalvarReparacion)
                .addGap(18, 18, 18)
                .addComponent(botonCancelarReparacion)
                .addGap(18, 18, 18)
                .addComponent(botonEditarReparacion)
                .addGap(18, 18, 18)
                .addComponent(botonRemoverReparacion)
                .addGap(130, 130, 130))
        );
        panelReparacionesLayout.setVerticalGroup(
            panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelReparacionesLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(cajaBuscarReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBuscarReparacion))
                .addGap(43, 43, 43)
                .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelReparacionesLayout.createSequentialGroup()
                        .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelReparacionesLayout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jLabel26))
                            .addGroup(panelReparacionesLayout.createSequentialGroup()
                                .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel31)
                                    .addComponent(cajaIDReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(17, 17, 17)
                                .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(fechaEntradaReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(fechaSalidaReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cajaFallaReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelReparacionesLayout.createSequentialGroup()
                        .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(comboVehiculos, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(cajaIDVehiculos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(comboPiezas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(cajaIDReparacionPieza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(SpinnerPiezasReparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)))
                .addGroup(panelReparacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonNuevoReparacion)
                    .addComponent(botonSalvarReparacion)
                    .addComponent(botonCancelarReparacion)
                    .addComponent(botonEditarReparacion)
                    .addComponent(botonRemoverReparacion))
                .addGap(51, 51, 51))
        );

        MenuVentanas.addTab("Repaciones", panelReparaciones);

        jLabel33.setText("ID");

        jLabel34.setText("Descripcion");

        jLabel35.setText("Stock");

        botonNuevoPieza.setText("Nuevo");
        botonNuevoPieza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoPiezaActionPerformed(evt);
            }
        });

        botonSalvarPieza.setText("Salvar");
        botonSalvarPieza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalvarPiezaActionPerformed(evt);
            }
        });

        botonCancelarPieza.setText("Cancelar");
        botonCancelarPieza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarPiezaActionPerformed(evt);
            }
        });

        botonEditarPieza.setText("Editar");
        botonEditarPieza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarPiezaActionPerformed(evt);
            }
        });

        botonRemoverPieza.setText("Remover");
        botonRemoverPieza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRemoverPiezaActionPerformed(evt);
            }
        });

        jLabel37.setText("Ingrese ID a Buscar:");

        botonBuscarPieza.setText("Buscar");
        botonBuscarPieza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarPiezaActionPerformed(evt);
            }
        });

        areaDescripcion.setColumns(20);
        areaDescripcion.setRows(5);
        jScrollPane1.setViewportView(areaDescripcion);

        javax.swing.GroupLayout panelPiezasLayout = new javax.swing.GroupLayout(panelPiezas);
        panelPiezas.setLayout(panelPiezasLayout);
        panelPiezasLayout.setHorizontalGroup(
            panelPiezasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPiezasLayout.createSequentialGroup()
                .addGroup(panelPiezasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPiezasLayout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addGroup(panelPiezasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPiezasLayout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addGap(31, 31, 31)
                                .addComponent(cajaBuscarPieza, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(botonBuscarPieza))
                            .addGroup(panelPiezasLayout.createSequentialGroup()
                                .addComponent(botonNuevoPieza)
                                .addGap(23, 23, 23)
                                .addComponent(botonSalvarPieza)
                                .addGap(18, 18, 18)
                                .addComponent(botonCancelarPieza)
                                .addGap(18, 18, 18)
                                .addComponent(botonEditarPieza)
                                .addGap(18, 18, 18)
                                .addComponent(botonRemoverPieza))))
                    .addGroup(panelPiezasLayout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addGroup(panelPiezasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelPiezasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelPiezasLayout.createSequentialGroup()
                                    .addGap(341, 341, 341)
                                    .addComponent(jLabel35)
                                    .addGap(18, 18, 18)
                                    .addComponent(SpinnerStock, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelPiezasLayout.createSequentialGroup()
                                    .addComponent(jLabel33)
                                    .addGap(18, 18, 18)
                                    .addComponent(cajaIDPieza, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelPiezasLayout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(124, Short.MAX_VALUE))
        );
        panelPiezasLayout.setVerticalGroup(
            panelPiezasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPiezasLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(panelPiezasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(cajaBuscarPieza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBuscarPieza))
                .addGap(46, 46, 46)
                .addGroup(panelPiezasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(cajaIDPieza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(SpinnerStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelPiezasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPiezasLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(90, 90, 90))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(panelPiezasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonNuevoPieza)
                    .addComponent(botonSalvarPieza)
                    .addComponent(botonCancelarPieza)
                    .addComponent(botonEditarPieza)
                    .addComponent(botonRemoverPieza))
                .addGap(26, 26, 26))
        );

        MenuVentanas.addTab("Piezas", panelPiezas);

        javax.swing.GroupLayout panelGraficosLayout = new javax.swing.GroupLayout(panelGraficos);
        panelGraficos.setLayout(panelGraficosLayout);
        panelGraficosLayout.setHorizontalGroup(
            panelGraficosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 772, Short.MAX_VALUE)
        );
        panelGraficosLayout.setVerticalGroup(
            panelGraficosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

        MenuVentanas.addTab("Graficos", panelGraficos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(MenuVentanas, javax.swing.GroupLayout.PREFERRED_SIZE, 772, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MenuVentanas, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalvarActionPerformed
        try{
            error = file.Autentificar(cajaUserName.getText(),null);
            if(error==true){
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con el mismo UserName");
            }else{
                us = new Usuario();
                us.setNombre(cajaNombre.getText());
                us.setApellidoPaterno(cajaAP.getText());
                us.setApellidoMaterno(cajaAM.getText());
                us.setNumero(cajaTelefono.getText());
                us.setUserName(cajaUserName.getText());
                us.setPerfil(comboPerfil.getSelectedItem().toString());
                us.setDireccion(cajaDireccion.getText());
                us.setPassword(cajaPW.getText());

                JOptionPane.showMessageDialog(null, "Usuario salvado");
                file.AgregarUsuario(us);
            }
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null,"No se pudo agregar registro");
       }   
    }//GEN-LAST:event_botonSalvarActionPerformed

    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarActionPerformed
        botonEditar.setEnabled(true);
        botonRemover.setEnabled(true);
        botonNuevo.setEnabled(false);
        botonSalvar.setEnabled(true);
        try{
            us = new Usuario(); //Crea un objeto para poder agregar un nuevo contacto
            us.setID(Integer.parseInt(cajaBuscar.getText())); //se obtiene el valor a buscar

            
            us=file.FileSearch(us);
            if(us.getNombre()!=null){
                cajaID.setText(String.valueOf(us.getID()));
                cajaNombre.setText(String.valueOf(us.getNombre()));
                cajaAP.setText(String.valueOf(us.getApellidoPaterno()));
                cajaAM.setText(String.valueOf(us.getApellidoMaterno()));
                cajaTelefono.setText(String.valueOf(us.getNumero()));
                cajaUserName.setText(String.valueOf(us.getUserName()));
                comboPerfil.setSelectedItem(us.getPerfil().toString());
                cajaDireccion.setText(String.valueOf(us.getDireccion()));
                cajaPW.setText(String.valueOf(us.getPassword()));
            }
             
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null,"No se encontro registro");
            LimpiarCajas();
        }
    }//GEN-LAST:event_botonBuscarActionPerformed

    private void botonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLimpiarActionPerformed
        LimpiarCajas();   
        Habilitar();
        
        botonNuevo.setEnabled(true);
        botonSalvar.setEnabled(false);
    }//GEN-LAST:event_botonLimpiarActionPerformed

    private void botonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevoActionPerformed
        if(cajaID.getText().equals("") || cajaNombre.getText().equals("") || cajaAP.getText().equals("") || cajaAM.getText().equals("") || cajaTelefono.getText().equals("") || !cajaUserName.getText().matches(emailRegex) || comboPerfil.getSelectedItem() == null || cajaDireccion.getText().equals("") || cajaPW.getText().equals("")){
            JOptionPane.showMessageDialog(null, "No hay datos suficientes");            
        }
        else{
            try{
                error = file.Autentificar(cajaUserName.getText(),null);
                if(error==true){
                    JOptionPane.showMessageDialog(null, "Ya existe un usuario con el mismo UserName");
                }else{
                    us=new Usuario();
                    us.setID(Integer.parseInt(cajaID.getText()));
                    us.setNombre(cajaNombre.getText());
                    us.setApellidoPaterno(cajaAP.getText());
                    us.setApellidoMaterno(cajaAM.getText());
                    us.setNumero(cajaTelefono.getText());
                    us.setUserName(cajaUserName.getText());
                    us.setPerfil(comboPerfil.getSelectedItem().toString());
                    us.setDireccion(cajaDireccion.getText());
                    us.setPassword(cajaPW.getText());

                    file.FileWrite(us);
                    JOptionPane.showMessageDialog(null, "Usuario registrado");
                }
            }catch(IOException ex){

            }
            
            //DesHabilitar();
            botonNuevo.setEnabled(false);
            botonSalvar.setEnabled(true);
            botonEditar.setEnabled(true);
            botonRemover.setEnabled(true);
            
        }
    }//GEN-LAST:event_botonNuevoActionPerformed

    private void botonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarActionPerformed
        try{
            us = new Usuario();
            us.setID(Integer.parseInt(cajaBuscar.getText()));
            
            int ID = Integer.parseInt(cajaID.getText());
            String Nombre = cajaNombre.getText();
            String AP = cajaAP.getText();
            String AM = cajaAM.getText();
            String Numero = cajaTelefono.getText();
            String UserName = cajaUserName.getText();
            String Perfil = comboPerfil.getSelectedItem().toString();
            String Direccion = cajaDireccion.getText();
            String PW = cajaPW.getText();
            
            JOptionPane.showMessageDialog(null, "Usuario editado");
            file.EditarUsuario(us, ID, Nombre, AP, AM, Numero, UserName, Perfil, Direccion, PW);
            

        }catch(Exception ex){

        }
    }//GEN-LAST:event_botonEditarActionPerformed

    private void botonRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRemoverActionPerformed
        try{
            us = new Usuario();
            us.setID(Integer.parseInt(cajaID.getText()));
            JOptionPane.showMessageDialog(null, "Usuario eliminado");
            LimpiarCajas(); 
            file.EliminarUsuario(us);           
                    
        }catch(Exception ex){
            
        }
    }//GEN-LAST:event_botonRemoverActionPerformed

    private void cajaAMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaAMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaAMActionPerformed

    private void cajaTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaTelefonoActionPerformed

    private void cajaNombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaNombreClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaNombreClienteActionPerformed

    private void botonNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevoClienteActionPerformed
        
        if(cajaIDCliente.getText().equals("") || cajaNombreCliente.getText().equals("") || cajaAPCliente.getText().equals("") || cajaAMCliente.getText().equals("") ){
            JOptionPane.showMessageDialog(null, "No hay datos suficientes");
        }
        else{
            
            try{
                cl = new Cliente();
                cl.setID(Integer.parseInt(cajaIDCliente.getText()));
                cl.setUsuario(comboPerfilClientes.getSelectedItem().toString());
                cl.setIDUsuario(cajaIDUsuario.getText());
                cl.setNombre(cajaNombreCliente.getText());
                cl.setApellidoP(cajaAPCliente.getText());
                cl.setApellidoM(cajaAMCliente.getText());

                JOptionPane.showMessageDialog(null, "Cliente registrado");
                fileCliente.FileWriteCliente(cl);

            }catch(IOException ex){
            }
        }
        
        botonNuevoCliente.setEnabled(false);
            botonSalvarCliente.setEnabled(true);
            botonEditarCliente.setEnabled(true);
            botonRemoverCliente.setEnabled(true);
            
    }//GEN-LAST:event_botonNuevoClienteActionPerformed

    private void botonSalvarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalvarClienteActionPerformed
        try{
            error = fileCliente.AutentificarCliente(cajaNombreCliente.getText());
            if(error==true){
                JOptionPane.showMessageDialog(null, "Ya existe un cliente con los mismo datos");
            }else{
                cl = new Cliente();
                cl.setUsuario(comboPerfilClientes.getSelectedItem().toString());
                cl.setIDUsuario(cajaIDUsuario.getText());
                cl.setNombre(cajaNombreCliente.getText());
                cl.setApellidoP(cajaAPCliente.getText());
                cl.setApellidoM(cajaAMCliente.getText());

                JOptionPane.showMessageDialog(null, "Cliente salvado");
                fileCliente.AgregarCliente(cl);
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"No se pudo agregar registro");
        }
    }//GEN-LAST:event_botonSalvarClienteActionPerformed

    private void botonCancelarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarClienteActionPerformed
        LimpiarCajasClientes();
        HabilitarCajasClientes();

        botonNuevoCliente.setEnabled(true);
        botonSalvarCliente.setEnabled(false);
    }//GEN-LAST:event_botonCancelarClienteActionPerformed

    private void botonEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarClienteActionPerformed
        try{
            cl = new Cliente();
            cl.setID(Integer.parseInt(cajaBuscarCliente.getText()));
            
            int ID = Integer.parseInt(cajaIDCliente.getText());
            String Usuario = comboPerfilClientes.getSelectedItem().toString();
            String IDUsuario = cajaIDUsuario.getText();
            String Nombre = cajaNombreCliente.getText();
            String AP = cajaAPCliente.getText();
            String AM = cajaAMCliente.getText();

            
            JOptionPane.showMessageDialog(null, "Usuario editado");
            fileCliente.EditarCliente(cl, ID, Usuario, IDUsuario, Nombre, AP, AM);
            

        }catch(Exception ex){

        }
    }//GEN-LAST:event_botonEditarClienteActionPerformed

    private void botonRemoverClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRemoverClienteActionPerformed
        try{
            cl = new Cliente();
            cl.setID(Integer.parseInt(cajaIDCliente.getText()));
            JOptionPane.showMessageDialog(null, "Usuario eliminado");
            LimpiarCajasClientes();
            fileCliente.EliminarCliente(cl);

        }catch(Exception ex){

        }
    }//GEN-LAST:event_botonRemoverClienteActionPerformed

    private void botonBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarClienteActionPerformed
          
        botonEditarCliente.setEnabled(true);
        botonRemoverCliente.setEnabled(true);
        botonNuevoCliente.setEnabled(false);
        botonSalvarCliente.setEnabled(true);
        try{
            cl =  new Cliente();
            cl.setID(Integer.parseInt(cajaBuscarCliente.getText()));
            System.out.print("\nEl user name a buscar es: "+cajaBuscarCliente.getText());
            cl = fileCliente.FileSearchCliente(cl);
            if(cl.getNombre()!=null){
                cajaIDCliente.setText(String.valueOf(cl.getID()));
                comboPerfilClientes.setSelectedItem(cl.getUsuario());
                cajaNombreCliente.setText(cl.getNombre().toString());
                cajaAPCliente.setText(String.valueOf(cl.getApellidoP()));
                cajaAMCliente.setText(String.valueOf(cl.getApellidoM()));
            }

        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, "Usuario no encontrado");
            LimpiarCajasClientes();
        }
    }//GEN-LAST:event_botonBuscarClienteActionPerformed

    private void botonAutentificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAutentificarActionPerformed
        BloquearPaneles();
        close=true;
        boolean bandera = false;
        us = new Usuario();
        if(cajaUsuario.getText().equals("admin") && cajaContra.getText().equals("admin")){
            bandera = true;
            MenuVentanas.setEnabledAt(1, true);
            MenuVentanas.setEnabledAt(2, true);
            MenuVentanas.setEnabledAt(3, true);
            MenuVentanas.setEnabledAt(4, true);
            MenuVentanas.setEnabledAt(5, true); 
            etiquetaTipoPerfil.setText("Super Usuario");
        }
        try{
            ArrayList<Usuario> usuarios = file.Recuperar();
            for(Usuario usuario: usuarios){
                if(cajaUsuario.getText().equals(usuario.getUserName()) && cajaContra.getText().equals(usuario.getPassword())){      
                    bandera = true;
                    us = usuario;
                    System.out.print("\nEl tipo de usuario es: "+us.getPerfil()); 
                    if(us.getPerfil().equals("Administrador")){
                        etiquetaTipoPerfil.setText("Administrador");
                        MenuVentanas.setEnabledAt(1, true);
                        MenuVentanas.setEnabledAt(2, true);
                        MenuVentanas.setEnabledAt(3, true);
                        MenuVentanas.setEnabledAt(4, true);
                        MenuVentanas.setEnabledAt(5, true);
                    }
                    else if(us.getPerfil().equals("Gerente")){
                        etiquetaTipoPerfil.setText("Gerente");
                        MenuVentanas.setEnabledAt(1, false);
                        MenuVentanas.setEnabledAt(2, true);
                        MenuVentanas.setEnabledAt(3, true);
                        MenuVentanas.setEnabledAt(4, true);
                        botonRemoverReparacion.setEnabled(false);
                        MenuVentanas.setEnabledAt(5, true);
                    }
                    else if(us.getPerfil().equals("Secretaria")){
                        etiquetaTipoPerfil.setText("Secretaria");
                        MenuVentanas.setEnabledAt(1, false);
                        MenuVentanas.setEnabledAt(2, true);
                        botonEditarCliente.setEnabled(false);
                        botonRemoverCliente.setEnabled(false);
                        MenuVentanas.setEnabledAt(3, true);
                        botonEditarVehiculo.setEnabled(false);
                        botonRemoverVehiculo.setEnabled(false);
                        MenuVentanas.setEnabledAt(4, false);
                        MenuVentanas.setEnabledAt(5, false);
                    }
                    else if(us.getPerfil().equals("Mecanico")){
                        etiquetaTipoPerfil.setText("Mecanico");
                        MenuVentanas.setEnabledAt(1, false);
                        MenuVentanas.setEnabledAt(2, false);
                        MenuVentanas.setEnabledAt(3, false);
                        MenuVentanas.setEnabledAt(4, true);
                        botonEditarReparacion.setEnabled(false);
                        botonRemoverReparacion.setEnabled(false);
                        MenuVentanas.setEnabledAt(5, false);
                    }     
                }
            }
            
            
            if(bandera == true){ //si los nombres son iguales tienes acceso
                JOptionPane.showMessageDialog(null, "Usuario existente");
            }else{
                JOptionPane.showMessageDialog(null, "No existe usuario");
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "No hay usuarios registrados");
        }
        

        
        
    }//GEN-LAST:event_botonAutentificarActionPerformed

    private void cajaMatriculaVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaMatriculaVehiculoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaMatriculaVehiculoActionPerformed

    private void botonNuevoVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevoVehiculoActionPerformed
        if(cajaIDVehiculo.getText().equals("") || cajaMatriculaVehiculo.getText().equals("") || cajaMarcaVehiculo.getText().equals("") || cajaModeloVehiculo.getText().equals("") || cajaIDClienteV.getText().equals("") || fechaVehiculo.getDate()==null){
            JOptionPane.showMessageDialog(null, "No hay datos suficientes");
        }
        else{
            try{
                //error = file.AutentificarUser(cajaUserName.getText());
                if(error==true){
                    JOptionPane.showMessageDialog(null, "Ya existe un vehiculo con la misma matricula");
                }else{
                    Date fecha = fechaVehiculo.getDate();
                    // Formatear la fecha como una cadena de caracteres en el formato "dd/MM/yyyy"
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                    String date = formatoFecha.format(fecha);
                    
                    car = new Vehiculo();
                    car.setID(Integer.parseInt(cajaIDVehiculo.getText()));
                    car.setCliente(comboClientes.getSelectedItem().toString());
                    car.setIdCliente(cajaIDClienteV.getText());
                    car.setMatricula(cajaMatriculaVehiculo.getText());
                    car.setMarca(cajaMarcaVehiculo.getText());
                    car.setModelo(cajaModeloVehiculo.getText());                    
                    car.setFecha(date);
 

                    fileVehiculo.FileWriteVehiculo(car);
                    JOptionPane.showMessageDialog(null, "Usuario registrado");
                }
            }catch(IOException ex){

            }
            
            //DesHabilitar();
            botonNuevoVehiculo.setEnabled(false);
            botonSalvarVehiculo.setEnabled(true);
            botonEditarVehiculo.setEnabled(true);
            botonRemoverVehiculo.setEnabled(true);
        }
    }//GEN-LAST:event_botonNuevoVehiculoActionPerformed

    private void botonSalvarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalvarVehiculoActionPerformed
        try{
            //error = file.AutentificarUser(cajaUserName.getText());
            if(error==true){
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con el mismo UserName");
            }else{
                Date fecha = fechaVehiculo.getDate();
                    // Formatear la fecha como una cadena de caracteres en el formato "dd/MM/yyyy"
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                    String date = formatoFecha.format(fecha);
                    
                car = new Vehiculo();
                car.setID(Integer.parseInt(cajaIDVehiculo.getText()));
                car.setCliente(comboClientes.getSelectedItem().toString());
                car.setIdCliente(cajaIDClienteV.getText());
                car.setMatricula(cajaMatriculaVehiculo.getText());
                car.setMarca(cajaMarcaVehiculo.getText());
                car.setModelo(cajaModeloVehiculo.getText()); 
                car.setFecha(date);

                JOptionPane.showMessageDialog(null, "Vehiculo salvado");
                fileVehiculo.AgregarVehiculo(car);
            }
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null,"No se pudo agregar registro");
       } 
    }//GEN-LAST:event_botonSalvarVehiculoActionPerformed

    private void botonCancelarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarVehiculoActionPerformed
        LimpiarCajasVehiculos();   
        //HabilitarVehiculos();
        
        botonNuevoVehiculo.setEnabled(true);
        botonSalvarVehiculo.setEnabled(false);
    }//GEN-LAST:event_botonCancelarVehiculoActionPerformed

    private void botonEditarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarVehiculoActionPerformed
        try{
            Date fecha= fechaVehiculo.getDate();
              // Formatear la fecha como una cadena de caracteres en el formato "dd/MM/yyyy"
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

            car = new Vehiculo();
            car.setID(Integer.parseInt(cajaBuscarVehiculo.getText()));
            
            int ID = Integer.parseInt(cajaIDVehiculo.getText());
            String Cliente = comboClientes.getSelectedItem().toString();
            String IDCliente = cajaIDClienteV.getText();
            String Matricula = cajaMatriculaVehiculo.getText();
            String Marca = cajaMarcaVehiculo.getText();
            String Modelo = cajaModeloVehiculo.getText();           
            String Fecha  = formatoFecha.format(fecha);
            
            JOptionPane.showMessageDialog(null, "Usuario editado");
            fileVehiculo.EditarVehiculo(car, ID, Cliente, IDCliente, Matricula, Marca, Modelo, Fecha);
            

        }catch(Exception ex){

        }
    }//GEN-LAST:event_botonEditarVehiculoActionPerformed

    private void botonRemoverVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRemoverVehiculoActionPerformed
        try{
            car = new Vehiculo();
            car.setID(Integer.parseInt(cajaIDVehiculo.getText())); 
            JOptionPane.showMessageDialog(null, "Vehiculo eliminado");
            LimpiarCajasVehiculos(); 
            fileVehiculo.EliminarVehiculo(car);    
                    
        }catch(Exception ex){
            
        }
    }//GEN-LAST:event_botonRemoverVehiculoActionPerformed

    private void botonBuscarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarVehiculoActionPerformed
        botonEditarVehiculo.setEnabled(true);
        botonRemoverVehiculo.setEnabled(true);
        botonNuevoVehiculo.setEnabled(false);
        botonSalvarVehiculo.setEnabled(true);
        try{
            car = new Vehiculo(); //Crea un objeto para poder agregar un nuevo contacto
            car.setID(Integer.parseInt(cajaBuscarVehiculo.getText())); //se obtiene el valor a buscar

            
            car = fileVehiculo.FileSearchVehiculo(car);
            if(car.getMatricula()!=null){
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                Date date = formatoFecha.parse(car.getFecha());
                comboClientes.setSelectedItem(car.getCliente());
                cajaIDVehiculo.setText(String.valueOf(car.getID()));
                cajaMatriculaVehiculo.setText(String.valueOf(car.getMatricula()));
                cajaMarcaVehiculo.setText(String.valueOf(car.getMarca()));
                cajaModeloVehiculo.setText(String.valueOf(car.getModelo()));
                fechaVehiculo.setDate(date);
                cajaIDClienteV.setText(String.valueOf(car.getIdCliente()));
            }
             
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null,"No se encontro registro");
            LimpiarCajasVehiculos();
        } catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonBuscarVehiculoActionPerformed

    private void cajaIDVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaIDVehiculoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaIDVehiculoActionPerformed

    private void botonRemoverReparacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRemoverReparacionActionPerformed
        try{
            rp = new Reparacion();
            rp.setID(Integer.parseInt(cajaIDReparacion.getText()));
            JOptionPane.showMessageDialog(null, "Reparacion eliminada");
            LimpiarCajasReparaciones(); 
            fileReparacion.EliminarReparacion(rp);           
                    
        }catch(Exception ex){   
            
        }
    }//GEN-LAST:event_botonRemoverReparacionActionPerformed

    private void cajaIDReparacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaIDReparacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaIDReparacionActionPerformed

    private void botonBuscarReparacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarReparacionActionPerformed
        botonEditarReparacion.setEnabled(true);
        botonRemoverReparacion.setEnabled(true);
        botonNuevoReparacion.setEnabled(false);
        botonSalvarReparacion.setEnabled(true);
        try{
            rp = new Reparacion(); //Crea un objeto para poder agregar un nuevo contacto
            rp.setID(Integer.parseInt(cajaBuscarReparacion.getText())); //se obtiene el valor a buscar

            
            rp=fileReparacion.FileSearchReparacion(rp);
            if(rp.getEntrada()!=null){
                
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                Date entrada = formatoFecha.parse(rp.getEntrada());
                Date salida = formatoFecha.parse(rp.getSalida());
                        
                cajaIDReparacion.setText(String.valueOf(rp.getID()));
                fechaEntradaReparacion.setDate(entrada);
                fechaSalidaReparacion.setDate(salida);
                cajaFallaReparacion.setText(String.valueOf(rp.getFalla()));
                comboVehiculos.setSelectedItem(rp.getMatricula());
                cajaIDVehiculos.setText(String.valueOf(rp.getIDVehiculo()));
                comboPiezas.setSelectedItem(rp.getPieza());
                cajaIDReparacionPieza.setText(String.valueOf(rp.getIDPieza()));
                SpinnerPiezasReparacion.setValue(Integer.parseInt(rp.getCantidadPiezas()));

            }
             
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null,"No se encontro registro");
            LimpiarCajasReparaciones();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null,"No se encontro registro");
            LimpiarCajasReparaciones();
        }
    }//GEN-LAST:event_botonBuscarReparacionActionPerformed

    private void cajaIDReparacionPiezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaIDReparacionPiezaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaIDReparacionPiezaActionPerformed

    private void botonNuevoReparacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevoReparacionActionPerformed
        if(cajaIDReparacion.getText().equals("") || fechaEntradaReparacion.getDate() == null || fechaSalidaReparacion.getDate()==null || cajaFallaReparacion.getText().equals("") || cajaIDVehiculos.getText().equals("") || cajaIDReparacionPieza.getText().equals("") || SpinnerPiezasReparacion.getValue() == null){
            JOptionPane.showMessageDialog(null, "No hay datos suficientes");
        }
        else if(fechaSalidaReparacion.getDate().compareTo(fechaEntradaReparacion.getDate()) < 0){
            JOptionPane.showMessageDialog(null, "Las fechas son no son validas");
        }
        else{
            try{
                Date fechaEntrada = fechaEntradaReparacion.getDate();
                Date fechaSalida = fechaSalidaReparacion.getDate();

                // Formatear la fecha como una cadena de caracteres en el formato "dd/MM/yyyy"
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                String entrada = formatoFecha.format(fechaEntrada);
                String salida = formatoFecha.format(fechaSalida);

                System.out.print("la fecha de entrada es: "+entrada);
                System.out.print("la fecha de entrada es: "+salida);

                rp = new Reparacion();
                rp.setID(Integer.parseInt(cajaIDReparacion.getText()));
                rp.setEntrada(entrada);
                rp.setSalida(salida);
                rp.setFalla(cajaFallaReparacion.getText());
                //aparte
                rp.setMatricula(comboVehiculos.getSelectedItem().toString());
                rp.setIDVehiculo(cajaIDVehiculos.getText());
                rp.setPieza(comboPiezas.getSelectedItem().toString());
                rp.setIDPieza(cajaIDReparacionPieza.getText());
                rp.setCantidadPiezas(SpinnerPiezasReparacion.getValue().toString());


                fileReparacion.FileWriteReparacion(rp);
                
                pz = new Pieza();
                System.out.print("\n"+cajaIDReparacionPieza.getText());
                pz.setID(Integer.parseInt(cajaIDReparacionPieza.getText()));
                pz = filePieza.FileSearchPieza(pz);
                
                int ID = Integer.parseInt(cajaIDReparacionPieza.getText());
                String Descripcion = comboPiezas.getSelectedItem().toString();
                String Stock = SpinnerPiezasReparacion.getValue().toString();

                JOptionPane.showMessageDialog(null, "Reparacion registrada");
                filePieza.ReducirStock(pz,ID,Descripcion, Stock);
     
            }catch(Exception ex){

            //DesHabilitar();
            botonNuevo.setEnabled(false);
            botonSalvar.setEnabled(true);
            botonEditar.setEnabled(true);
            botonRemover.setEnabled(true);
        }
        }
    }//GEN-LAST:event_botonNuevoReparacionActionPerformed

    private void botonSalvarReparacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalvarReparacionActionPerformed
        try{    
            Date fechaEntrada = fechaEntradaReparacion.getDate();
            Date fechaSalida = fechaSalidaReparacion.getDate();

                // Formatear la fecha como una cadena de caracteres en el formato "dd/MM/yyyy"
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                String entrada = formatoFecha.format(fechaEntrada);
                String salida = formatoFecha.format(fechaSalida);
                
                rp = new Reparacion();
                rp.setID(Integer.parseInt(cajaIDReparacion.getText()));
                rp.setEntrada(entrada);
                rp.setSalida(salida);
                rp.setFalla(cajaFallaReparacion.getText());
                //aparte
                rp.setMatricula(comboVehiculos.getSelectedItem().toString());
                rp.setIDVehiculo(cajaIDVehiculos.getText());
                rp.setPieza(comboPiezas.getSelectedItem().toString());
                rp.setIDPieza(cajaIDReparacionPieza.getText());
                rp.setCantidadPiezas(SpinnerPiezasReparacion.getValue().toString());
                
                fileReparacion.AgregarReparacion(rp);
                
                //DECREMENTO DEL STOCK
                pz = new Pieza();
                System.out.print("\n"+cajaIDReparacionPieza.getText());
                pz.setID(Integer.parseInt(cajaIDReparacionPieza.getText()));
                pz = filePieza.FileSearchPieza(pz);
                
                int ID = Integer.parseInt(cajaIDReparacionPieza.getText());
                String Descripcion = comboPiezas.getSelectedItem().toString();
                String Stock = SpinnerPiezasReparacion.getValue().toString();

                //System.out.print("\nValores de la pieza: "+pz.getID()+pz.getDescripcion()+pz.getStock());
                
                System.out.print("\nEL id es: "+ID);
                System.out.print("\nLa descripcion es: "+Descripcion);
                System.out.print("\nEl valor es: "+Stock);
                
                JOptionPane.showMessageDialog(null, "Reparacion salvada");
                
                filePieza.ReducirStock(pz,ID,Descripcion, Stock);
                     
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null,"No se pudo agregar registro");
       }   
    }//GEN-LAST:event_botonSalvarReparacionActionPerformed

    private void botonCancelarReparacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarReparacionActionPerformed
        LimpiarCajasReparaciones();   

        botonNuevoReparacion.setEnabled(true);
        botonSalvarReparacion.setEnabled(false);
    }//GEN-LAST:event_botonCancelarReparacionActionPerformed

    private void botonEditarReparacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarReparacionActionPerformed
        try{
            Date fechaEntrada = fechaEntradaReparacion.getDate();
            Date fechaSalida = fechaSalidaReparacion.getDate();

                // Formatear la fecha como una cadena de caracteres en el formato "dd/MM/yyyy"
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

                
            rp = new Reparacion();
            rp.setID(Integer.parseInt(cajaBuscarReparacion.getText()));
            
            int ID = Integer.parseInt(cajaIDReparacion.getText());
            String Entrada = formatoFecha.format(fechaEntrada);
            String Salida = formatoFecha.format(fechaSalida);
            String Falla = cajaFallaReparacion.getText();
            String Matricula = comboVehiculos.getSelectedItem().toString();
            String IDVehiculo = cajaIDVehiculos.getText();
            String Pieza = comboPiezas.getSelectedItem().toString();
            String IDPieza = cajaIDReparacionPieza.getText();
            String CantidadPiezas = SpinnerPiezasReparacion.getValue().toString();

            
            JOptionPane.showMessageDialog(null, "Usuario editado");
            fileReparacion.EditarReparacion(rp, ID, Entrada, Salida, Falla, Matricula, IDVehiculo, Pieza, IDPieza, CantidadPiezas);
            

        }catch(Exception ex){

        }
    }//GEN-LAST:event_botonEditarReparacionActionPerformed

    private void botonBuscarPiezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarPiezaActionPerformed
        botonEditarPieza.setEnabled(true);
        botonRemoverPieza.setEnabled(true);
        botonNuevoPieza.setEnabled(false);
        botonSalvarPieza.setEnabled(true);
        try{
            pz = new Pieza(); //Crea un objeto para poder agregar un nuevo contacto
            pz.setID(Integer.parseInt(cajaBuscarPieza.getText())); //se obtiene el valor a buscar
            System.out.print("\nEl valor a buscar es: "+pz.getID());
            
            pz = filePieza.FileSearchPieza(pz);
            
            if(pz.getDescripcion()!=null){
                cajaIDPieza.setText(String.valueOf(pz.getID()));
                areaDescripcion.setText(String.valueOf(pz.getDescripcion()));
                SpinnerStock.setValue(Integer.parseInt(pz.getStock()));
            }
             
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null,"No se encontro registro");
            LimpiarCajasPiezas();
        }
    }//GEN-LAST:event_botonBuscarPiezaActionPerformed

    private void botonRemoverPiezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRemoverPiezaActionPerformed
        try{
            pz = new Pieza();
            pz.setID(Integer.parseInt(cajaIDPieza.getText()));
            JOptionPane.showMessageDialog(null, "Pieza eliminada");         
            LimpiarCajasPiezas(); 
            filePieza.EliminarPieza(pz);           
                    
        }catch(Exception ex){
            
        }
    }//GEN-LAST:event_botonRemoverPiezaActionPerformed

    private void botonEditarPiezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarPiezaActionPerformed
        try{
            pz = new Pieza();
            pz.setID(Integer.parseInt(cajaBuscarPieza.getText()));
            
            int ID = Integer.parseInt(cajaIDPieza.getText());
            String Descripcion = areaDescripcion.getText();
            String Stock = SpinnerStock.getValue().toString();

            
            JOptionPane.showMessageDialog(null, "Pieza editado");
            filePieza.EditarPieza(pz, ID, Descripcion, Stock);
            

        }catch(Exception ex){

        }
    }//GEN-LAST:event_botonEditarPiezaActionPerformed

    private void botonCancelarPiezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarPiezaActionPerformed
        LimpiarCajasPiezas();   
        //Habilitar();
        
        botonNuevoPieza.setEnabled(true);
        botonSalvarPieza.setEnabled(false);
    }//GEN-LAST:event_botonCancelarPiezaActionPerformed

    private void botonSalvarPiezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalvarPiezaActionPerformed
        try{                    
                pz =new Pieza();
                pz.setID(Integer.parseInt(cajaIDPieza.getText()));
                pz.setDescripcion(areaDescripcion.getText());
                pz.setStock(SpinnerStock.getValue().toString());
                    
                System.out.print("\nID: "+pz.getID());
                System.out.print("\nDescripcion: "+pz.getDescripcion());
                System.out.print("\nStock: "+pz.getStock());
                
                filePieza.AgregarPieza(pz);
                JOptionPane.showMessageDialog(null, "Pieza salvada");
            
       }catch(Exception ex){
           JOptionPane.showMessageDialog(null,"No se pudo agregar registro");
       }   
    }//GEN-LAST:event_botonSalvarPiezaActionPerformed

    private void botonNuevoPiezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevoPiezaActionPerformed
        if(cajaIDPieza.getText().equals("") || areaDescripcion.getText().equals("") || SpinnerStock.getValue() == null){
            JOptionPane.showMessageDialog(null, "No hay datos suficientes");
        }
        else{
            try{
                    pz = new Pieza();
                    pz.setID(Integer.parseInt(cajaIDPieza.getText()));
                    pz.setDescripcion(areaDescripcion.getText());
                    pz.setStock(SpinnerStock.getValue().toString());
                    
                    filePieza.FileWritePieza(pz);
                    JOptionPane.showMessageDialog(null, "Pieza registrada");
                
            }catch(IOException ex){

            }
            
            //DesHabilitar();
            botonNuevoPieza.setEnabled(false);
            botonSalvarPieza.setEnabled(true);
            botonEditarPieza.setEnabled(true);
            botonRemoverPieza.setEnabled(true);
            
        }
    }//GEN-LAST:event_botonNuevoPiezaActionPerformed

    private void comboPerfilClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPerfilClientesActionPerformed
        
    }//GEN-LAST:event_comboPerfilClientesActionPerformed

    private void cajaUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaUsuarioActionPerformed

    private void MenuVentanasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuVentanasMouseClicked
        ObtenerUsuarios();
        ObtenerClientes();
        ObtenerVehiculos();
        ObtenerPiezas();
        LimpiarCombos();
        
    }//GEN-LAST:event_MenuVentanasMouseClicked

    private void comboPerfilClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboPerfilClientesMouseClicked

    }//GEN-LAST:event_comboPerfilClientesMouseClicked

    private void comboPerfilClientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboPerfilClientesMousePressed

    }//GEN-LAST:event_comboPerfilClientesMousePressed

    private void cajaIDUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaIDUsuarioActionPerformed

    }//GEN-LAST:event_cajaIDUsuarioActionPerformed

    private void comboPerfilClientesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboPerfilClientesItemStateChanged
        ObtenerIDUsuario();
    }//GEN-LAST:event_comboPerfilClientesItemStateChanged

    private void comboClientesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboClientesItemStateChanged
        ObtenerIDCliente();
    }//GEN-LAST:event_comboClientesItemStateChanged

    private void comboVehiculosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboVehiculosActionPerformed
        ObtenerIDVehiculo();
    }//GEN-LAST:event_comboVehiculosActionPerformed

    private void comboPiezasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPiezasActionPerformed
        ObtenerIDPieza();
    }//GEN-LAST:event_comboPiezasActionPerformed

    private void comboVehiculosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboVehiculosItemStateChanged
        
    }//GEN-LAST:event_comboVehiculosItemStateChanged

    private void panelLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLoginMouseClicked

    }//GEN-LAST:event_panelLoginMouseClicked

    private void panelLoginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLoginMousePressed

    }//GEN-LAST:event_panelLoginMousePressed

    private void MenuVentanasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_MenuVentanasStateChanged
        int indice = MenuVentanas.getSelectedIndex();
        
        if(indice == 0 && close==true){
            BloquearPaneles();
            etiquetaTipoPerfil.setText("");
            cajaUsuario.setText("");
            cajaContra.setText("");
        }else if(indice == 6){
            //Se necesita cuando se quiera ir directamente a ver las graficas
            GenerarGrafico();
        }
    }//GEN-LAST:event_MenuVentanasStateChanged

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane MenuVentanas;
    private javax.swing.JSpinner SpinnerPiezasReparacion;
    private javax.swing.JSpinner SpinnerStock;
    private javax.swing.JTextArea areaDescripcion;
    private javax.swing.JButton botonAutentificar;
    private javax.swing.JButton botonBuscar;
    private javax.swing.JButton botonBuscarCliente;
    private javax.swing.JButton botonBuscarPieza;
    private javax.swing.JButton botonBuscarReparacion;
    private javax.swing.JButton botonBuscarVehiculo;
    private javax.swing.JButton botonCancelarCliente;
    private javax.swing.JButton botonCancelarPieza;
    private javax.swing.JButton botonCancelarReparacion;
    private javax.swing.JButton botonCancelarVehiculo;
    private javax.swing.JButton botonEditar;
    private javax.swing.JButton botonEditarCliente;
    private javax.swing.JButton botonEditarPieza;
    private javax.swing.JButton botonEditarReparacion;
    private javax.swing.JButton botonEditarVehiculo;
    private javax.swing.JButton botonLimpiar;
    private javax.swing.JButton botonNuevo;
    private javax.swing.JButton botonNuevoCliente;
    private javax.swing.JButton botonNuevoPieza;
    private javax.swing.JButton botonNuevoReparacion;
    private javax.swing.JButton botonNuevoVehiculo;
    private javax.swing.JButton botonRemover;
    private javax.swing.JButton botonRemoverCliente;
    private javax.swing.JButton botonRemoverPieza;
    private javax.swing.JButton botonRemoverReparacion;
    private javax.swing.JButton botonRemoverVehiculo;
    private javax.swing.JButton botonSalvar;
    private javax.swing.JButton botonSalvarCliente;
    private javax.swing.JButton botonSalvarPieza;
    private javax.swing.JButton botonSalvarReparacion;
    private javax.swing.JButton botonSalvarVehiculo;
    private javax.swing.JTextField cajaAM;
    private javax.swing.JTextField cajaAMCliente;
    private javax.swing.JTextField cajaAP;
    private javax.swing.JTextField cajaAPCliente;
    private javax.swing.JTextField cajaBuscar;
    private javax.swing.JTextField cajaBuscarCliente;
    private javax.swing.JTextField cajaBuscarPieza;
    private javax.swing.JTextField cajaBuscarReparacion;
    private javax.swing.JTextField cajaBuscarVehiculo;
    private javax.swing.JTextField cajaContra;
    private javax.swing.JTextField cajaDireccion;
    private javax.swing.JLabel cajaEjemplo;
    private javax.swing.JTextField cajaFallaReparacion;
    private javax.swing.JTextField cajaID;
    private javax.swing.JTextField cajaIDCliente;
    private javax.swing.JTextField cajaIDClienteV;
    private javax.swing.JTextField cajaIDPieza;
    private javax.swing.JTextField cajaIDReparacion;
    private javax.swing.JTextField cajaIDReparacionPieza;
    private javax.swing.JTextField cajaIDUsuario;
    private javax.swing.JTextField cajaIDVehiculo;
    private javax.swing.JTextField cajaIDVehiculos;
    private javax.swing.JTextField cajaMarcaVehiculo;
    private javax.swing.JTextField cajaMatriculaVehiculo;
    private javax.swing.JTextField cajaModeloVehiculo;
    private javax.swing.JTextField cajaNombre;
    private javax.swing.JTextField cajaNombreCliente;
    private javax.swing.JTextField cajaPW;
    private javax.swing.JTextField cajaTelefono;
    private javax.swing.JTextField cajaUserName;
    private javax.swing.JTextField cajaUsuario;
    private javax.swing.JComboBox<String> comboClientes;
    private javax.swing.JComboBox<String> comboPerfil;
    private javax.swing.JComboBox<String> comboPerfilClientes;
    private javax.swing.JComboBox<String> comboPiezas;
    private javax.swing.JComboBox<String> comboVehiculos;
    private javax.swing.JLabel etiquetaTipoPerfil;
    private com.toedter.calendar.JDateChooser fechaEntradaReparacion;
    private com.toedter.calendar.JDateChooser fechaSalidaReparacion;
    private com.toedter.calendar.JDateChooser fechaVehiculo;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelClientes;
    private javax.swing.JPanel panelGraficos;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelPiezas;
    private javax.swing.JPanel panelReparaciones;
    private javax.swing.JPanel panelUsuarios;
    private javax.swing.JPanel panelVehiculos;
    // End of variables declaration//GEN-END:variables
}
