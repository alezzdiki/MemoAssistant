/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import memoassistant.DataAccount;


/**
 *  Classe client
 * @author Alessandro
 */
public class Client {
    
   
    
    public final int PORT = 4000;   //un valore per la porta
    public final String IP = "127.0.0.1";   //indirizzo in localhost
    private final Socket sock;    // riferimento per il socket
    
/**
 * Crea il collegamento con il server istanziando l'oggeto di tipo socket
 * @throws IOException 
 */
    public Client() throws IOException {
   
            sock = new Socket(IP, PORT);
            System.out.println("sock creato " + sock.toString());
    
    }
    
    /**
     * Costruttore utilizzato dai thread per stampare a video i loro collegamenti
     * @param name il nome del thread
     * @throws IOException 
     */
     public Client(String name) throws IOException {
   
            sock = new Socket(IP, PORT);
            System.out.println(name + ": sock CREATO " + sock.toString());
    
    }
     
     /**
      * Chiude il socket
      */
    public void closeSocket(){
        try {
            sock.close();
            System.out.println("sock chiuso " + sock.toString());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Client's Socket not closed", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Client's Socket not closed", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
/**
 * Chiude il socket e stampa a video il nome del thread
 * @param name nome del thread
 */
     public void closeSocket(String name){
        try {
            sock.close();
            System.out.println(name + ": sock CHIUSO " + sock.toString());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Client's Socket not closed", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Client's Socket not closed", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Send utilizzata per inviare al server una richiesta di query
     * @param prompt comando che identifica la tabella da esaminare e la query da effettuare
     * @param user_email l'email dell'user
     */
  
public void send(String prompt, String user_email)  {
      
       ObjectOutputStream output = null;
       
       try{
           
        output = new ObjectOutputStream(sock.getOutputStream());
        output.writeObject(prompt);
        output.writeObject(user_email);
                
       }catch (IOException ex){
           System.out.println("Eccezzione client send method: " + ex.getMessage());
       }
       
      
    }

    /**
     * Send utilizzata per trasmettere al server una richiesta di: inserimento, update o delete di un record nel/dal database
     * Si utilizza una linkedList, anche se si effettua l'invio di un singolo oggetto per volta, per consentire un'eventuale
     * inoltro di più di un' istanza per volta. Inoltre il server è implementato per riceve linkedList di oggetti
     * 
     * @param list container per gli oggetti da inviare
     * @param command identifica il comando: new, update, delete
     * @param user_email email dell'user
     */
    
    public void send(LinkedList<? extends DataAccount> list, String command, String user_email) {
      
       ObjectOutputStream output = null;
       
       try{
           
        output = new ObjectOutputStream(sock.getOutputStream());
        output.writeObject(command);
        output.writeObject(list);
        output.writeObject(user_email);
                
       }catch (IOException ex ){
           System.out.println("Eccezzione client send method: " + ex.getMessage());
       }catch (Exception ex){
           System.out.println("Eccezione client send method: " + ex.getMessage());
       }
        
    }
    
    /**
     * Metodo che si occupa della ricezione di linkedList contenenti i record recuperati dal database
     * @return linkedList con oggetti di classi che estendono DataAccount
     */
    public LinkedList<? extends DataAccount> receive(){
        
        ObjectInputStream input = null;
        LinkedList<? extends DataAccount> list = null;
        
        try {
            input = new ObjectInputStream(sock.getInputStream());
            list = (LinkedList<DataAccount>) input.readObject();
            
            
        } catch (IOException | ClassNotFoundException ex) {
           System.out.println("Eccezione client receive method: " + ex.getMessage());
        } 
        return list;
    }
    
}
