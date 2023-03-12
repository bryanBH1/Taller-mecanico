
package clases;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class FileVehiculo {
    
    Object [][] data;
    private String url="vehiculos.txt";
    private DataOutputStream write;
    private DataInputStream read;
    private ArrayList<Vehiculo> vehiculos;
    public FileVehiculo(){
        read = null;
        write=null;
        data = new Object[50][7];
    }
    
    public Vehiculo FileSearchVehiculo(Vehiculo car) throws IOException{
     try {
        read = new DataInputStream( new FileInputStream(url));
        int id=0;
        String  Cliente="", IDCliente="", Matricula="", Marca="", Modelo="", Fecha="";
            while (true){
            id=read.readInt();
            Cliente =  read.readUTF();
            IDCliente = read.readUTF();
            Matricula = read.readUTF();
            Marca = read.readUTF();
            Modelo = read.readUTF();
            Fecha= read.readUTF();
            
                if (id==car.getID()){
                    car.setCliente(Cliente);
                car.setIdCliente(IDCliente);
                car.setMatricula(Matricula);
                car.setMarca(Marca);
                car.setModelo(Modelo);
                car.setFecha(Fecha);
                
                return car;
                }
            }
        }catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(null, "No se encontro el archivo");
        }
        return car;
    }
     
    public void FileWriteVehiculo(Vehiculo car) throws IOException {
        try {
            write = new DataOutputStream(new FileOutputStream(url, true));
            write.writeInt(car.getID());
            write.writeUTF(car.getCliente());
            write.writeUTF(car.getIdCliente());
            write.writeUTF(car.getMatricula());
            write.writeUTF(car.getMarca());
            write.writeUTF(car.getModelo());           
            write.writeUTF(car.getFecha());
            write.close();

            } catch (FileNotFoundException ex) {

            }  
    }

    public void EditarVehiculo(Vehiculo car, int id, String Cliente, String IDCliente, String Matricula, String Marca, String Modelo, String Fecha) throws IOException {
    System.out.print("\nLos datos son: "+id+Matricula+Marca+Modelo+IDCliente+Fecha);
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
            x++;
        }
    }catch(IOException ex){
        
    }
 
    for (int i = 0; i < data.length; i++) {
        //se obtiene el dato de la primera collumna de todas las filas que es el id
        int dato = (int) data[i][0];
        if (dato == car.getID()) {
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
            data[pos][1] = Cliente;
            data[pos][2] = IDCliente;
            data[pos][3] = Matricula;
            data[pos][4] = Marca;
            data[pos][5] = Modelo;
            data[pos][6] = Fecha;
            
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
    }
    write.close();
}
    
    public void EliminarVehiculo(Vehiculo car) throws IOException {

        try {
            read = new DataInputStream(new FileInputStream(url));
            int x = 0;
            //Se obtiene todas los registros por fila
            while (true) {
                data[x][0] = read.readInt(); //int
                data[x][1] = read.readUTF(); //matricula
                data[x][2] = read.readUTF(); //marca
                data[x][3] = read.readUTF(); //modelo
                data[x][4] = read.readUTF(); //idcliente
                data[x][5] = read.readUTF(); //fecha
                data[x][6] = read.readUTF(); //fecha
                x++;
            }
        }catch(IOException ex){

        }

        int pos = -1;
        //se busca el id
        for (int i = 0; i < data.length; i++) {
            int dato = (int) data[i][0];
            if (dato == car.getID()) {
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
            }
        }
        write.close();
    }
    
    public void AgregarVehiculo(Vehiculo car) throws IOException {
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
                        if (id > maxId) {
                            maxId = id;
                        }
                    }
                }

            car.setID(maxId+1);
            FileWriteVehiculo(car);
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no existe.");
        } catch (IOException ex) {
            System.out.println("Error al agregar el contacto.");
        }
    }
    
    public Boolean AutentificarVehiculo(String matricula){
        try {
        read = new DataInputStream( new FileInputStream(url));
        int id=0;
        String Matricula="", Marca="", Modelo="", IDCliente="", Fecha="";
        
            while (true){
                id=read.readInt();
                Matricula=read.readUTF();
                Marca=read.readUTF();
                Modelo=read.readUTF();            
                IDCliente=read.readUTF();
                Fecha=read.readUTF();
                    if (matricula.equals(Matricula)){
                        return true;
                    }
            }
        }catch(IOException ex){
            
        }
        return false;
    }
    
    public ArrayList<Vehiculo> RecuperarVehiculos() throws IOException{
     try {
        vehiculos = new ArrayList<Vehiculo>();
        read = new DataInputStream( new FileInputStream(url));
        int id=0;
        String  Cliente="", IDCliente="", Matricula="", Marca="", Modelo="", Fecha="";
            while (read.available() > 0){
                id=read.readInt();
                Cliente =  read.readUTF();
                IDCliente = read.readUTF();
                Matricula = read.readUTF();
                Marca = read.readUTF();
                Modelo = read.readUTF();
                Fecha= read.readUTF();

                Vehiculo car = new Vehiculo();
                car.setID(id);
                car.setCliente(Cliente);
                car.setIdCliente(IDCliente);
                car.setMatricula(Matricula);
                car.setMarca(Marca);
                car.setModelo(Modelo);
                car.setFecha(Fecha);

                vehiculos.add(car);
            }
        }catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(null, "No se encontro el archivo");
        }
        return vehiculos;
    }
    
}
