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


public class FilePieza {
    Object [][] data;
    private String url="piezas.txt";
    private DataOutputStream write;
    private DataInputStream read;
    private ArrayList<Pieza> piezas;
    
    public FilePieza(){
    read = null;
    write=null;
    data = new Object[50][3];
    }
    
    public Pieza FileSearchPieza(Pieza pz) throws IOException{
     try {
        read = new DataInputStream( new FileInputStream(url));
        int id=0;
        String Descripcion="", Stock="";
            while (true){
            id=read.readInt();
            Descripcion=read.readUTF();
            Stock=read.readUTF();

            
                if (id==pz.getID() ){
                    pz.setID(id);
                    pz.setDescripcion(Descripcion);
                    pz.setStock(Stock);

                    return pz;
                }
            }
        }catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(null, "No se encontro el archivo");
        }
        return pz;
    }
    
    public void FileWritePieza(Pieza pz) throws IOException {
        try {
            write = new DataOutputStream(new FileOutputStream(url, true));
            write.writeInt(pz.getID());
            write.writeUTF(pz.getDescripcion());
            write.writeUTF(pz.getStock());

            write.close();

            } catch (FileNotFoundException ex) {
            }
    }
    
    public void AgregarPieza(Pieza pz) throws IOException {
    try {
        // Obtener el id más alto en el archivo y sumarle 1 para crear un nuevo id
        int maxId = 0;
     
            try (DataInputStream read = new DataInputStream(new FileInputStream(url))) {
                while (read.available() > 0) {
                    int id = read.readInt();
                    read.readUTF();
                    read.readUTF();
                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }
        
        pz.setID(maxId+1);
        FileWritePieza(pz);
    } catch (FileNotFoundException ex) {
        System.out.println("El archivo no existe.");
    } catch (IOException ex) {
        System.out.println("Error al agregar el contacto.");
    }
    }
    
    public void ReducirStock(Pieza pz, int id, String Descripcion, String Stock) throws IOException {
    int pos = -1;
    int act=0;
    int ide=0;
    String des="", sto="";
    
    try{
        read = new DataInputStream(new FileInputStream(url));
        int x = 0;
        
        //alamacena cada resgistro por fila
        while (true) {
            ide = read.readInt();
            des = read.readUTF();
            sto = read.readUTF();
            data[x][0] = ide;
            data[x][1] = des;
            data[x][2] = sto;
            x++;
        }
        
        
    }catch(IOException ex){
        
    }
 
    for (int i = 0; i < data.length; i++) {
        //se obtiene el dato de la primera collumna de todas las filas que es el id
        int dato = (int) data[i][0];
        if (dato == pz.getID()) {
            pos = i;
            act= Integer.parseInt(pz.getStock())-Integer.parseInt(Stock);
            break;
        }
    }
    if (pos == -1) {
        // El objeto no se encuentra en la matriz
        return;
    }

    String nuevo = Integer.toString(act);
    System.out.print("\nEl nuevo Stock es: "+nuevo);
    //Cuando si se encuentre el registro
        data[pos][0] = id;
        data[pos][1] = Descripcion;
        data[pos][2] = nuevo;
        
        System.out.print("\nEL valor es: "+data[pos][0]);
        System.out.print("\nEL valor es: "+data[pos][1]);
        System.out.print("\nEL valor es: "+data[pos][2]);


    write = new DataOutputStream(new FileOutputStream(url));
    //se reeescribe el archivo
    for (int i = 0; i < data.length; i++) {
        write.writeInt((int) data[i][0]);
        write.writeUTF((String) data[i][1]);
        write.writeUTF((String) data[i][2]);
    }
    write.close();
}
    
    public void EditarPieza(Pieza pz, int id, String Descripcion, String Stock) throws IOException {
    int pos = -1;
    try{
        read = new DataInputStream(new FileInputStream(url));
        int x = 0;
        //alamacena cada resgistro por fila
        while (true) {
            data[x][0] = read.readInt();
            data[x][1] = read.readUTF();
            data[x][2] = read.readUTF();
            x++;
        }
    }catch(IOException ex){
        
    }
 
    for (int i = 0; i < data.length; i++) {
        //se obtiene el dato de la primera collumna de todas las filas que es el id
        int dato = (int) data[i][0];
        if (dato == pz.getID()) {
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
        data[pos][1] = Descripcion;
        data[pos][2] = Stock;


    write = new DataOutputStream(new FileOutputStream(url));
    //se reeescribe el archivo
    for (int i = 0; i < data.length; i++) {
        write.writeInt((int) data[i][0]);
        write.writeUTF((String) data[i][1]);
        write.writeUTF((String) data[i][2]);
    }
    write.close();
}
    
    public void EliminarPieza(Pieza pz) throws IOException {
        
    try {
        read = new DataInputStream(new FileInputStream(url));
        int x = 0;
        //Se obtiene todas los registros por fila
        while (true) {
            data[x][0] = read.readInt(); //int
            data[x][1] = read.readUTF(); //nombre
            data[x][2] = read.readUTF(); //ap
            x++;
        }
    }catch(IOException ex){
        
    }

    int pos = -1;
    //se busca el id
    for (int i = 0; i < data.length; i++) {
        int dato = (int) data[i][0];
        if (dato == pz.getID()) {
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
        }
    }
    write.close();
    }
 
    public ArrayList<Pieza> RecuperarPiezas() throws IOException {
        piezas = new ArrayList<Pieza>();

        try (DataInputStream read = new DataInputStream(new FileInputStream(url))) {
            int id = 0;
            String Descripcion="", Stock="";
            while (read.available() > 0) {
                id = read.readInt();
                Descripcion = read.readUTF();
                Stock=read.readUTF();
                
                Pieza pz = new Pieza();
                pz.setID(id);
                pz.setDescripcion(Descripcion);
                pz.setStock(Stock);
                
               piezas.add(pz);
            }
        } catch (FileNotFoundException ex) {
            // JOptionPane.showMessageDialog(null, "No se encontro el archivo");
        }
        return piezas;
    }
    
    
    
}