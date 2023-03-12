/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class FileCCliente {
    Object [][] data;
    private String url="clientes.txt";
    private DataOutputStream write;
    private DataInputStream read;
    private ArrayList<Cliente> clientes;
    
    public FileCCliente(){
    read = null;
    write=null;
    data = new Object[50][6];
    }
    
    public Cliente FileSearchCliente(Cliente cl) throws IOException{
     try {
        read = new DataInputStream( new FileInputStream(url));
        int id=0;
        String Usuario="",IDUsuario="", Nombre="", AP="", AM="";
            while (true){
            id=read.readInt();
            Usuario = read.readUTF();
            IDUsuario = read.readUTF();
            Nombre=read.readUTF();
            AP=read.readUTF();
            AM=read.readUTF();
            
                if (id==cl.getID() || Nombre.equals(cl.getNombre())){
                    cl.setID(id);
                    cl.setUsuario(Usuario);
                    cl.setIDUsuario(IDUsuario);
                    cl.setNombre(Nombre);
                    cl.setApellidoP(AP);
                    cl.setApellidoM(AM);

                    return cl;
                }
            }
        }catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(null, "No se encontro el archivo");
            
        }
        return cl;
    }
    
    public void FileWriteCliente(Cliente cl) throws IOException {
        try {
            write = new DataOutputStream(new FileOutputStream(url, true));
            write.writeInt(cl.getID());
            write.writeUTF(cl.getUsuario());
            write.writeUTF(cl.getIDUsuario());
            write.writeUTF(cl.getNombre());
            write.writeUTF(cl.getApellidoP());
            write.writeUTF(cl.getApellidoM());
            write.close();

            } catch (FileNotFoundException ex) {
            }
    }
    
    public void AgregarCliente(Cliente cl) throws IOException {
    try {
        // Obtener el id más alto en el archivo y sumarle 1 para crear un nuevo id
        int maxId = 0;
     
            try (DataInputStream read = new DataInputStream(new FileInputStream(url))) {
                while (read.available() > 0) {
                    int id = read.readInt();
                    read.readUTF();
                    read.readUTF();
                    read.readUTF();
                    read.readUTF();
                    read.readUTF();

                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }
        
        cl.setID(maxId+1);
        FileWriteCliente(cl);
    } catch (FileNotFoundException ex) {
        System.out.println("El archivo no existe.");
    } catch (IOException ex) {
        System.out.println("Error al agregar el contacto.");
    }
    }
    
    public void EditarCliente(Cliente cl, int id, String Usuario, String IDUsuario, String Nombre, String AP, String AM) throws IOException {
    int pos = -1;
    try{
        read = new DataInputStream(new FileInputStream(url));
        int x = 0;
        //alamacena cada resgistro por fila
        while (true) {
            data[x][0] = read.readInt();
            data[x][1] = read.readUTF();
            data[x][2] = read.readUTF();
            data[x][3] = read.readUTF();
            data[x][4] = read.readUTF();
            data[x][5] = read.readUTF();

            x++;
        }
    }catch(IOException ex){
        
    }
 
    for (int i = 0; i < data.length; i++) {
        //se obtiene el dato de la primera collumna de todas las filas que es el id
        int dato = (int) data[i][0];
        if (dato == cl.getID()) {
            pos = i;
            break;
        }
    }
    if (pos == -1) {
        // El objeto no se encuentra en la matriz
        return;
    }
    //Cuando si se encuentre el registro
        data[pos][0] = id;
        data[pos][1] = Usuario;
        data[pos][2] = IDUsuario;
        data[pos][3] = Nombre;
        data[pos][4] = AP;
        data[pos][5] = AM;

    write = new DataOutputStream(new FileOutputStream(url));
    //se reeescribe el archivo
    for (int i = 0; i < data.length; i++) {
        write.writeInt((int) data[i][0]);
        write.writeUTF((String) data[i][1]);
        write.writeUTF((String) data[i][2]);
        write.writeUTF((String) data[i][3]);
        write.writeUTF((String) data[i][4]);
        write.writeUTF((String) data[i][5]);

    }
    write.close();
}
    
    public void EliminarCliente(Cliente cl) throws IOException {
        
    try {
        read = new DataInputStream(new FileInputStream(url));
        int x = 0;
        //Se obtiene todas los registros por fila
        while (true) {
            data[x][0] = read.readInt(); //int
            data[x][1] = read.readUTF(); //usuario
            data[x][2] = read.readUTF(); //id usuario
            data[x][3] = read.readUTF(); //nobre
            data[x][4] = read.readUTF(); //ap
            data[x][5] = read.readUTF(); //am
            x++;
        }
    }catch(IOException ex){
        
    }

    int pos = -1;
    //se busca el id
    for (int i = 0; i < data.length; i++) {
        int dato = (int) data[i][0];
        if (dato == cl.getID()) {
            pos = i;
            break;
        }
    }
    if (pos == -1) {
        // El objeto no se encuentra en la matriz
        return;
    }

    // Eliminar el objeto de la matriz, comienza en la posicion a eliminar para hacer el salto al siguiente dato, 
    //y se omite el ultimo elemento para al final pasarlo a nulo
    for (int i = pos; i < data.length - 1; i++) {
        data[i] = data[i + 1];
    }
    //se deshace del ultimo elemento asi que su tamaño cambia
    data[data.length - 1] = null;
    // Escribir la matriz actualizada en el archivo
    write = new DataOutputStream(new FileOutputStream(url));
    
    for (int i = 0; i < data.length; i++) {
        if (data[i] != null) {
            write.writeInt((int) data[i][0]);
            write.writeUTF((String) data[i][1]);
            write.writeUTF((String) data[i][2]);
            write.writeUTF((String) data[i][3]);
            write.writeUTF((String) data[i][4]);
            write.writeUTF((String) data[i][5]);
        }
    }
    write.close();
    }
      
    public Boolean AutentificarCliente(String nombre){
        try {
        read = new DataInputStream( new FileInputStream(url));
        int id=0;
        String Usuario="", IDUsuario="", Nombre="", AP="", AM="";
        
            while (true){
                id=read.readInt();
                Usuario = read.readUTF();
                IDUsuario = read.readUTF();
                Nombre=read.readUTF();
                AP=read.readUTF();
                AM=read.readUTF();
            
                    if (nombre.equals(Nombre)){ //mejorear
                        return true;
                    }
            }
        }catch(IOException ex){
            
        }
        return false;
    }
    
    
    
    public ArrayList<Cliente> RecuperarClientes() throws IOException{
     try {
        clientes = new ArrayList<Cliente>();
        read = new DataInputStream( new FileInputStream(url));
        int id=0;
        String Usuario="",IDUsuario="", Nombre="", AP="", AM="";
            while (read.available() > 0){
            id=read.readInt();
            Usuario = read.readUTF();
            IDUsuario = read.readUTF();
            Nombre=read.readUTF();
            AP=read.readUTF();
            AM=read.readUTF();
            
                Cliente cl = new Cliente();
                
                cl.setID(id);
                cl.setUsuario(Usuario);
                cl.setIDUsuario(IDUsuario);
                cl.setNombre(Nombre);
                cl.setApellidoP(AP);
                cl.setApellidoM(AM);               
                              
                clientes.add(cl);            
            }
        }catch(FileNotFoundException ex){
                
        }
        return clientes;
    }   
    
}
