/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client_server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import mySQLpack.*;
import memoassistant.*;

/**
 *Classe di servizio, che si interpone tra il server e l'interfacciamento al database
 * @author Alessandro
 */
public final class ServerTranslator {
    
    private static final Management man = new Management();
    private static final Connection conn = man.getconn();  
    
    /**
     * Metodo utilizzato per richieste di tipo: insert, edit, delete. Fa lo switch sul tipo di oggetto e ne istanzia il 
     * relativo preparedstatement
     * @param command new, update, delete
     * @param list la lista contenente l'oggetto
     * @param user_email l'email dell'user
     * @throws SQLException 
     */
    public static void editTable(String command, LinkedList<? extends DataAccount> list, String user_email) throws SQLException{
    
          
        
        Iterator<? extends DataAccount> itr = list.iterator();
        
        while (itr.hasNext()){
            
        DataAccount dataAccount = itr.next();
        
  
               if (dataAccount instanceof User){
                   User user = (User) dataAccount;
                   PSuser pSuser = new PSuser(conn, null);
                   
                   if (command.equalsIgnoreCase("New"))
                       pSuser.New(user);
                   else if (command.equalsIgnoreCase("Update"))
                       pSuser.Update(user);
                   else if (command.equalsIgnoreCase("Delete"))
                       pSuser.Delete(user);
                }
               
               else if (dataAccount instanceof Friend){
                   Friend friend = (Friend) dataAccount;
                   PSfriend pSfriend = new PSfriend(conn, null);
                   
                   if (command.equalsIgnoreCase("New"))
                       pSfriend.New(friend, user_email);
                   else if (command.equalsIgnoreCase("Update"))
                       pSfriend.Update(friend);
                   else if (command.equalsIgnoreCase("Delete"))
                       pSfriend.Delete(friend);
                }
               
               else if (dataAccount instanceof Favorites){
                   Favorites favorites = (Favorites) dataAccount;
                   PSfavorites pSfavorites = new PSfavorites(conn, null);
                   
                   if (command.equalsIgnoreCase("New"))
                       pSfavorites.New(favorites, user_email);
                   else if (command.equalsIgnoreCase("Update"))
                       pSfavorites.Update(favorites);
                   else if (command.equalsIgnoreCase("Delete"))
                       pSfavorites.Delete(favorites);
                }
               
               else if (dataAccount instanceof Event){
                   Event event = (Event) dataAccount;
                   PSevent pSevent = new PSevent(conn, null);
                   
                   if (command.equalsIgnoreCase("New"))
                       pSevent.New(event, user_email);
                   else if (command.equalsIgnoreCase("Update"))
                       pSevent.Update(event);
                   else if (command.equalsIgnoreCase("Delete"))
                       pSevent.Delete(event);
                }
               
               else if (dataAccount instanceof Action){
                   Action action = (Action) dataAccount;
                   PSaction pSaction = new PSaction(conn, null);
                  
                   if (command.equalsIgnoreCase("New"))
                       pSaction.New(action, user_email);
                   else if (command.equalsIgnoreCase("Update"))
                       pSaction.Update(action);
                   else if (command.equalsIgnoreCase("Delete"))
                       pSaction.Delete(action);
                }
        }
    }//ediTable end
    
    /**
     * Metodo utilizzato per le query
     * @param command la table del database da interrogare
     * @param user_email l'email dell'user
     * @return la linkedlist di record
     * @throws SQLException 
     */
    
    public static LinkedList<? extends DataAccount> queryTable(String command, String user_email) throws SQLException{
    
        LinkedList<? extends DataAccount> list = null;
        switch (command){
        
            case "User":
                PSuser pSuser = new PSuser(conn, null);
                list = pSuser.askQuery(user_email);
                break;
                
            case "Friends":
                PSfriend pSfriend = new PSfriend(conn, null);
                list = pSfriend.askQuery(user_email);
                break;
                
            case "Event":
                PSevent pSevent = new PSevent(conn, null);
                list = pSevent.askQuery(user_email);
                break;
                
            case "Action":
                PSaction pSaction = new PSaction(conn, null);
                list = pSaction.askQuery(user_email);
                break;  
                
            case "Favorites":
                PSfavorites pSfavorites = new PSfavorites(conn, null);
                list = pSfavorites.askQuery(user_email);
                break;
                
            case "EventThread":
                PSevent pSevent1 = new PSevent(conn, null);
                list = pSevent1.askThreadQuery(user_email);
                break;
                
            case "ActionThread":
                PSaction pSaction1 = new PSaction(conn, null);
                list = pSaction1.askThreadQuery(user_email);
                break;
        }
        
        return list;
        
    }//queryTable end
    
}
