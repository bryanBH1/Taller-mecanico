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


public class File {
    Object [][] data;
    private String url="usuarios.txt";
    private DataOutputStream write;
    private DataInputStream read;
    private ArrayList<Usuario> nombres;
    
    public File(){
    read = null;
    write=null;
    data = new Object[50][9];
    }
    
    public Usuario FileSearch(Usuario user) throws IOException{
     try {
        read = new DataInputStream( new FileInputStream(url));
        int id=0;
        String Nombre="", AP="", AM="",Numero="",UserName="",Perfil="",Direccion="", PW="";
            while (true){
            id=read.readInt();
            Nombre=read.readUTF();
            AP=read.readUTF();
            AM=read.readUTF();
            Numero=read.readUTF();
            UserName=read.readUTF();
            Perfil=read.readUTF();
            Direccion=read.readUTF();
            PW=read.readUTF();
            
                if (id==user.getID() ||UserName.equals(user.getUserName())){
                    user.setID(id);
                    user.setNombre(Nombre);
                    user.setApellidoPaterno(AP);
                    user.setApellidoMaterno(AM);
                    user.setNumero(Numero);
                    user.setUserName(UserName);
                    user.setPerfil(Perfil);
                    user.setDireccion(Direccion);
                    user.setPassword(PW);

                    return user;
                }
            }
        }catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(null, "No se encontro el archivo");
        }
        return user;
    }
    
    public void FileWrite(Usuario user) throws IOException {
        try {
            write = new DataOutputStream(new FileOutputStream(url, true));
            write.writeInt(user.getID());
            write.writeUTF(user.getNombre());
            write.writeUTF(user.getApellidoPaterno());
            write.writeUTF(user.getApellidoMaterno());
            write.writeUTF(user.getNumero());
            write.writeUTF(user.getUserName());
            write.writeUTF(user.getPerfil());
            write.writeUTF(user.getDireccion());
            write.writeUTF(user.getPassword());
            write.close();

            } catch (FileNotFoundException ex) {
            }
    }
    
    public void AgregarUsuario(Usuario user) throws IOException {
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
                    read.readUTF();
                    read.readUTF();
                    read.readUTF();
                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }
        
        user.setID(maxId+1);
        FileWrite(user);
    } catch (FileNotFoundException ex) {
        System.out.println("El archivo no existe.");
    } catch (IOException ex) {
        System.out.println("Error al agregar el contacto.");
    }
    }
    
    public void EditarUsuario(Usuario user, int id, String Nombre, String AP, String AM, String Numero, String UserName, String Perfil, String Direccion, String PW) throws IOException {
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
            data[x][6] = read.readUTF();
            data[x][7] = read.readUTF();
            data[x][8] = read.readUTF();
            x++;
        }
    }catch(IOException ex){
        
    }
 
    for (int i = 0; i < data.length; i++) {
        //se obtiene el dato de la primera collumna de todas las filas que es el id
        int dato = (int) data[i][0];
        if (dato == user.getID()) {
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
        data[pos][1] = Nombre;
        data[pos][2] = AP;
        data[pos][3] = AM;
        data[pos][4] = Numero;
        data[pos][5] = UserName;
        data[pos][6] = Perfil;
        data[pos][7] = Direccion;
        data[pos][8] = PW;


    write = new DataOutputStream(new FileOutputStream(url));
    //se reeescribe el archivo
    for (int i = 0; i < data.length; i++) {
        write.writeInt((int) data[i][0]);
        write.writeUTF((String) data[i][1]);
        write.writeUTF((String) data[i][2]);
        write.writeUTF((String) data[i][3]);
        write.writeUTF((String) data[i][4]);
        write.writeUTF((String) data[i][5]);
        write.writeUTF((String) data[i][6]);
        write.writeUTF((String) data[i][7]);
        write.writeUTF((String) data[i][8]);
    }
    write.close();
}
    
    public void EliminarUsuario(Usuario user) throws IOException {
        
    try {
        read = new DataInputStream(new FileInputStream(url));
        int x = 0;
        //Se obtiene todas los registros por fila
        while (true) {
            data[x][0] = read.readInt(); //int
            data[x][1] = read.readUTF(); //nombre
            data[x][2] = read.readUTF(); //ap
            data[x][3] = read.readUTF(); //am
            data[x][4] = read.readUTF(); //numero
            data[x][5] = read.readUTF(); //user name
            data[x][6] = read.readUTF(); //perfil
            data[x][7] = read.readUTF(); //Diireccion
            data[x][8] = read.readUTF(); //contra
            x++;
        }
    }catch(IOException ex){
        
    }

    int pos = -1;
    //se busca el id
    for (int i = 0; i < data.length; i++) {
        int dato = (int) data[i][0];
        if (dato == user.getID()) {
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
            write.writeUTF((String) data[i][6]);
            write.writeUTF((String) data[i][7]);
            write.writeUTF((String) data[i][8]);
        }
    }
    write.close();
    }
   
    public Boolean Autentificar(String username, String pw){
        try {
        read = new DataInputStream( new FileInputStream(url));
        int id=0;
        String Nombre="", ApellidoP="", ApellidoM="", Telefono="", UserName="", Perfil="",Direccion="", PW="";
        
            while (true){
                id=read.readInt();
                Nombre=read.readUTF();
                ApellidoP=read.readUTF();
                ApellidoM=read.readUTF();
                Telefono=read.readUTF();
                UserName=read.readUTF();
                Perfil=read.readUTF();
                Direccion=read.readUTF();
                PW=read.readUTF();
            
                    if (username.equals(UserName)){
                        return true;
                    }
                    if (username.equals(UserName)){
                        if(pw.equals(PW)){
                            return true;
                        }                
                    }
            }
        }catch(IOException ex){
            
        }
        return false;
    }
    
    public ArrayList<Usuario> Recuperar() throws IOException {
        nombres = new ArrayList<Usuario>();

        try (DataInputStream read = new DataInputStream(new FileInputStream(url))) {
            int id = 0;
            String Nombre = "",ApellidoP="", ApellidoM="", Telefono="", UserName="", Perfil="",Direccion="", PW="";
            while (read.available() > 0) {
                id = read.readInt();
                Nombre = read.readUTF();
                ApellidoP=read.readUTF();
                ApellidoM=read.readUTF();
                Telefono=read.readUTF();
                UserName=read.readUTF();
                Perfil=read.readUTF();
                Direccion=read.readUTF();
                PW=read.readUTF();
                
                Usuario user = new Usuario();
                user.setID(id);
                user.setNombre(Nombre);
                user.setApellidoPaterno(ApellidoP);
                user.setApellidoMaterno(ApellidoM);
                user.setNumero(Telefono);
                user.setUserName(UserName);
                user.setPerfil(Perfil);
                user.setDireccion(Direccion);
                user.setPassword(PW);
                nombres.add(user);
            }
        } catch (FileNotFoundException ex) {
            // JOptionPane.showMessageDialog(null, "No se encontro el archivo");
        }
        return nombres;
    }
    
}
