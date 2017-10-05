/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MemoThread;

import client_server.Client;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import memoassistant.Action;
import memoassistant.DataAccount;
import userGUI.myPosition;
import userGUI.notice;

/**
 * Classe che si occupa della gestione del thread che controlla la posizione attuale e la confronta con le
 * posizioni relative alle actions (salvate) da svolgere.
 * @author Alessandro
 */
public class ActionThread extends Thread implements MemoThread{
    
 
    
   
    private notice memo;    // riferimento che verrà istanziato non appena si dovrà notificare qualcosa
    private LinkedList<Action> list;    //lista che contiene le actions da controllare
    public final String USER_EMAIL;     
    
    
/**
 * Si setta in nome del thread con la parola chiave super.
 * 
 * @param email servirà per la query
 */
    public ActionThread(String email) {
        super("ActionThread");
        USER_EMAIL = email;
        list = new LinkedList<>();
    }
    
    /**
     * Carica la lista dal database: tutte le actions che non sono state ancora notificate 
     */
    @Override
    public void loadList(){
        try {
            Client client = new Client(this.getName());
            client.send("ActionThread", USER_EMAIL);
            list = (LinkedList<Action>) client.receive();
            client.closeSocket(this.getName());
            System.out.println(this.getName() + " lista caricata");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Si occupa di inoltrare l'update a seguito di una notifica di una action
     * 
     * @param <T> definisce la variabile di tipo vincolata al tipo di una classe che estende la superclasse DataAccount
     * @param action riferimento all'oggetto di tipo, in questo caso sarà una action
     */
    @Override
    public <T extends DataAccount> void sendUpdate(T action){
        try {
            LinkedList<T> list = new LinkedList<>();
            list.add(action);
            Client client = new Client();
            client.send(list, "Update", null);
            client.closeSocket();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Si occupa del calcolo della distanza tra due punti con coordinate geografiche (latitudine, longitudine)
     * 
     * @return distanza in metri
     */
    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = EARTHRADIUS_METRE * c * 1000;

        return dist;
    }
    /**
     * Override del metodo run. Controlla la lista caricata, se la differenza tra la posizione corrente e quella dell'action
     * i-esima è minore di 1000 metri, crea un avviso (notice), setta ad 1 il campo warned e concluse dell'action e inoltra
     * l' update. 
     */
    @Override
    public void run() {

        while (!this.isInterrupted()) { 
           
            loadList();
            
            for (Action a : list){
            
                double distance = distFrom(myPosition.getLatitude(), myPosition.getLongitude(), 
                        a.getLatitude(), a.getLongitude());
                System.out.println(a.toString() + " distante " + distance + "m dalla mia posizione");
                if (distance <= KILOMETRE_METRE){
                        a.setWarned((byte)1); a.setConclused((byte)1);
                        sendUpdate(a);
                        memo = new notice(a);
                        memo.setVisible(true);
                } 
            }
            try {
                System.out.println(this.getName() + ": mi addormento..");
                sleep(15000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ActionThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    

}
