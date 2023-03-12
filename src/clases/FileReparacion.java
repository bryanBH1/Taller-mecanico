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
import javax.swing.JOptionPane;


public class FileReparacion {
    Object [][] data;
    private String url="reparaciones.txt";
    private DataOutputStream write;
    private DataInputStream read;
    
    public FileReparacion(){
    read = null;
    write=null;
    data = new Object[50][7];
    }
    
    public Reparacion FileSearchReparacion(Reparacion rp) throws IOException{
     try {
        read = new DataInputStream( new FileInputStream(url));
        int id=0;
        String Entrada="", Salida="", Falla="", Matricula="",IDVehiculo="",Pieza="",IDPieza="", CantidadPiezas="";
            while (true){
            id=read.readInt();
            Entrada=read.readUTF();
            Salida=read.readUTF();
            Falla=read.readUTF();
            Matricula = read.readUTF();
            IDVehiculo=read.readUTF();
            Pieza = read.readUTF();
            IDPieza=read.readUTF();
            CantidadPiezas=read.readUTF();
            
            
                if (id==rp.getID()){
                rp.setID(id);
                rp.setEntrada(Entrada);
                rp.setSalida(Salida);
                rp.setFalla(Falla);
                rp.setMatricula(Matricula);
                rp.setIDVehiculo(IDVehiculo);
                rp.setPieza(Pieza);
                rp.setIDPieza(IDPieza);
                rp.setCantidadPiezas(CantidadPiezas);
                
                return rp;
                }
            }
        }catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(null, "No se encontro el archivo");
        }
        return rp;
    }
    
    public void FileWriteReparacion(Reparacion rp) throws IOException {
        try {
            write = new DataOutputStream(new FileOutputStream(url, true));
            write.writeInt(rp.getID());
            write.writeUTF(rp.getEntrada());
            write.writeUTF(rp.getSalida());
            write.writeUTF(rp.getFalla());
            write.writeUTF(rp.getMatricula());
            write.writeUTF(rp.getIDVehiculo());
            write.writeUTF(rp.getPieza());
            write.writeUTF(rp.getIDPieza());
            write.writeUTF(rp.getCantidadPiezas());
                     
            write.close();

            } catch (FileNotFoundException ex) {
            }
    }
    
    public void EliminarReparacion(Reparacion rp) throws IOException {
        
    try {
        read = new DataInputStream(new FileInputStream(url));
        int x = 0;
        //Se obtiene todas los registros por fila
        while (true) {
            data[x][0] = read.readInt(); //int
            data[x][1] = read.readUTF(); //entrada
            data[x][2] = read.readUTF(); //salida
            data[x][3] = read.readUTF(); //falla
            data[x][4] = read.readUTF(); //id vehiculo
            data[x][5] = read.readUTF(); //id pieza
            data[x][6] = read.readUTF(); //cantidad de piezas
            data[x][7] = read.readUTF(); //id pieza
            data[x][8] = read.readUTF(); //cantidad de piezas
            x++;
        }
    }catch(IOException ex){
        
    }

    int pos = -1;
    //se busca el id
    for (int i = 0; i < data.length; i++) {
        int dato = (int) data[i][0];
        if (dato == rp.getID()) {
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
    
    public void AgregarReparacion(Reparacion rp) throws IOException {
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
        
        rp.setID(maxId+1);
        FileWriteReparacion(rp);
    } catch (FileNotFoundException ex) {
        System.out.println("El archivo no existe.");
    } catch (IOException ex) {
        System.out.println("Error al agregar el contacto.");
    }
    }
    
    public void EditarReparacion(Reparacion rp, int id, String Entrada, String Salida, String Falla, String Matricula, String IDVehiculo, String Pieza, String IDPieza, String CantidadPiezas) throws IOException {
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
        if (dato == rp.getID()) {
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
        data[pos][1] = Entrada;
        data[pos][2] = Salida;
        data[pos][3] = Falla;
        data[pos][4] = Matricula;
        data[pos][5] = IDVehiculo;
        data[pos][6] = Pieza;
        data[pos][7] = IDPieza;
        data[pos][8] = CantidadPiezas;



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
}
