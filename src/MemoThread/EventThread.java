/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MemoThread;

import client_server.Client;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import memoassistant.*;
import userGUI.notice;

/**
 * Si occupa della gestione del thread che controlla l'avvicinarsi di un evento, rispetto la data corrente
 * @author Alessandro
 */
public class EventThread extends Thread implements MemoThread{
        
    private notice memo;    // riferimento che verrà istanziato non appena si dovrà notificare qualcosa
    private LinkedList<Event> list; //lista che contiene i friends da controllare
    public final String USER_EMAIL;
    
    
     /**
 * Si setta in nome del thread con la parola chiave super.
 * 
 * @param email servirà per la query
 */
    public EventThread(String email) {
        super("EventThread");
        USER_EMAIL = email;
        list = new LinkedList<>();
    }
    
     /**
     * Carica la lista dal database, con tutti gli eventi che non si sono conclusi e la ordino in funzione dell'ordinamento naturale (compareTo)
     * ossia dal più vicino al più lontano
     */
    @Override
    public void loadList(){
        try {
            Client client = new Client(this.getName());
            client.send("EventThread", USER_EMAIL);
            list = (LinkedList<Event>) client.receive();
            client.closeSocket(this.getName());
            Collections.sort(list);
            System.out.println(this.getName() + " lista caricata");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
     /**
     * Si occupa di inoltrare l'update a seguito di una richiesta di inserimento di un' action
     * 
     * @param <T> definisce la variabile di tipo vincolata al tipo di una classe che estende la superclasse DataAccount
     * @param event riferimento all'oggetto di tipo , in quetso caso sarà un event
     */
    @Override
    public <T extends DataAccount> void sendUpdate(T event){
        try {
            LinkedList<T> list = new LinkedList<>();
            list.add(event);
            
            Client client = new Client();
            client.send(list, "Update", null);
            client.closeSocket();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Per ogni evento della lista (non concluso) se la differenza è minore di zero, vuol dire che l'evento si deve settare
     * conncluso. Altrimenti si controlla se avverrà entro un giorno: in questo caso verrà notificato solo se warned = 0, 
     * ovvero se non è già stato segnalato. Se non avverà entro un giorno, si blocca la ricerca di tutti gli eventi successivi
     */
    @Override
    public void run() {
        while (!this.isInterrupted()) {
            loadList();
            
            long time = System.currentTimeMillis();
            
            for (Event e : list) {
                long eventTime = e.getEventDate().getTime() + e.getEventHour().getTime() + HOUR_MILLIS;
                
                long result = eventTime - time;
                //System.out.println("evenTime: " + eventTime + " time: " + time + " sottrazione = " + result);
                
                    if (result >= 0) {
                        if (result <= DAY_MILLIS){
                            if(e.getWarned() == 0){
                                e.setWarned((byte)1);
                                sendUpdate(e);
                                memo = new notice(e);
                                memo.setVisible(true);
                            }
                    }
                    else{
                        break;
                    }
                } 
                else{
                    e.setConclused((byte)1);
                    sendUpdate(e);
                }
                
            }

            try {
                System.out.println(this.getName() + ": mi addormento..");
                sleep(15000);
            } catch (InterruptedException ex) {
                System.err.println(this.getName() + " interrupted");
            }
        }
    }
}