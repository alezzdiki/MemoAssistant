/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import memoassistant.*;
import java.sql.*;
import java.util.LinkedList;

/**
 *Classe server. Implementata per multiclient
 * @author Alessandro
 */

public class Server{
    
            
    private final static int PORT = 4000;  
    

    /**
     * Loop infinito, il server si mette in ascolto e non appena riceve una connessione, avvia un thread per gestire il client
     * e si rimette in ascolto
     * 
     */
    public Server(){
       
        try (ServerSocket serverSocket = new ServerSocket(PORT)) { 
            while (true) {                       
                System.out.println("server in attesa di client");
                new MultiServerThread(serverSocket.accept()).start();
	        }
	    }
                catch (IOException ex) {
                System.err.println("Error server loop: " + ex.getMessage());
                }
            
    }
 
    /**
     * Private nested class per gestire i thread
     */
    private class MultiServerThread extends Thread {
        
    private Socket client_socket = null;
    

    public MultiServerThread(Socket socket) {
        super("MultiServerThread");
        this.client_socket = socket;
    }
    /**
     * il thread si mette in ascolto delle richieste del client, al termine delle quali chiude il socket
     */
        @Override
        public void run() {

            try {
                receive(client_socket);
            } catch (IOException | ClassNotFoundException | SQLException ex) {
                System.err.println("Error: " + ex.getMessage());
            } finally {
                try {
                    client_socket.close();
                    System.out.println("Socket closed");
                } catch (IOException ex) {
                    System.err.println("Socket not closed");
                }
            }
        }
}
      
    public static void main(String[] args){
    
        
        Server server = new Server();
        
          
    }
    /**
     * Riceve prima il comando e ne fa lo switch.
     * @param clientSocket il socket con il client
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public synchronized void receive(Socket clientSocket) throws IOException, ClassNotFoundException, SQLException{
        
        ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
        
        String user_email;
        String option = (String) input.readObject();
        System.out.println(option);
        
        if( (option.equalsIgnoreCase("New")) || (option.equalsIgnoreCase("Update")) || (option.equalsIgnoreCase("Delete"))){
        
            LinkedList<? extends DataAccount> list = (LinkedList<DataAccount>) input.readObject();
            user_email = (String) input.readObject();
            ServerTranslator.editTable(option, list, user_email);

        }
        
        
        else if ((option.equalsIgnoreCase("Friends")) || (option.equalsIgnoreCase("Favorites")) || 
                (option.equalsIgnoreCase("Event")) || (option.equalsIgnoreCase("Action")) || 
                (option.equalsIgnoreCase("User")) || (option.equalsIgnoreCase("EventThread")) ||
                (option.equalsIgnoreCase("ActionThread"))){
            
            user_email = (String) input.readObject();
            System.out.println("ci sono in server:" + option);
            LinkedList<? extends DataAccount> list = ServerTranslator.queryTable(option, user_email);
            send(list, clientSocket);
                   System.out.println("sono server, ho appena inviato al client");

        }
        
                
        else System.err.println("Error: command not found");
    }
    /**
     * Inoltra al client la linkedlist contente gli oggetti dopo una query
     * @param list container
     * @param clienSocket il socket con il client
     */
    
    private void send(LinkedList<? extends DataAccount> list, Socket clienSocket){
        try {
            ObjectOutputStream output = null;
            
            output = new ObjectOutputStream(clienSocket.getOutputStream());
            output.writeObject(list);
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
