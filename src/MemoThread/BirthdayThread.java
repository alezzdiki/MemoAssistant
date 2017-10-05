/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MemoThread;

import client_server.Client;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import memoassistant.DataAccount;
import memoassistant.Friend;
import org.joda.time.*;
import userGUI.newPlanning;
import userGUI.notice;

/**
 * Si occupa della gestione del thread che controlla la lista di friends dell'user, e notifica se l'i-esimo friend
 * sta per compiere gli anni
 * 
 * @author Alessandro
 */
public class BirthdayThread extends Thread implements MemoThread{

    
    private notice memo;    // riferimento che verrà istanziato non appena si dovrà notificare qualcosa
    private LinkedList<Friend> list; //lista che contiene i friends da controllare
    public final String USER_EMAIL;
    
 /**
 * Si setta in nome del thread con la parola chiave super.
 * 
 * @param email servirà per la query
 */
    public BirthdayThread(String email) {
        super("BirthdayThread");
        USER_EMAIL = email;
        list = new LinkedList<>();
    }
    
    /**
     * Carica la lista di tutti i friends dal database
     */
    @Override
    public void loadList() {
         try {
            Client client = new Client(this.getName());
            client.send("Friends", USER_EMAIL);
            list = (LinkedList<Friend>) client.receive();
            client.closeSocket(this.getName());
            System.out.println(this.getName() + " lista caricata");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
    class dateofBirthdayComparator implements Comparator<Friend> {
    
        @Override
    public int compare(Friend a, Friend b) {
        DateTime aDateTime = new DateTime(a.getDate().getTime());
        DateTime bDateTime = new DateTime(b.getDate().getTime());
        
        int res = aDateTime.monthOfYear().get() - bDateTime.monthOfYear().get();
        if (res != 0) return res;
        return aDateTime.dayOfMonth().get() - bDateTime.dayOfMonth().get();
        
    }
}*/
    
    /**
     * Si occupa di inoltrare l'update a seguito di una richiesta di inserimento di un' action
     * 
     * @param <T> definisce la variabile di tipo vincolata al tipo di una classe che estende la superclasse DataAccount
     * @param friend riferimento all'oggetto di tipo , in quetso caso sarà un friend
     */

    @Override
    public <T extends DataAccount> void sendUpdate(T friend) {
        try {
            LinkedList<T> list = new LinkedList<>();
            list.add(friend);
            Client client = new Client();
            client.send(list, "Update", null);
            client.closeSocket();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server out of services", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Crea l'avviso quando il compleanno di un friend è vicino. Se l'user seleziona 'yes' si istanzia un oggetto newplannig
     * @param f il friend che deve compiere gli anni
     */
    private void advisor(Friend f){
    
        int result = JOptionPane.showConfirmDialog(null, composeStringAlert(f), "Add alert", 
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (result == JOptionPane.YES_OPTION){
                newPlanning newplannig = new newPlanning();
                newplannig.setWhatTA(composeStringText(f));
                newplannig.setEventRadioButton();
                newplannig.setVisible(true);
            }
    }
    /**
     * Compone la stringa da visualizzare nella text area
     * 
     * @return 
     */
    private String composeStringText(Friend f){
    
        if (!f.getInteressi().equals(""))
            return "Buy a gift for: " + f.getName() + " " + f.getSurname() + "'s BirthDay\n\n" + "Interested in: " + f.getInteressi();
        else
            return "Buy a gift for: " + f.getName() + " " + f.getSurname() + "'s BirthDay\n\n";

    }
    /**
     * Compone la stringa che chiede all'use se inserire una action per il regalo o meno
     * 
     */
    private String composeStringAlert(Friend f){
    
        return f.getName() + " " + f.getSurname() + "'s BirthDay: " + f.getDate() + 
                "\nWould you like to add an alert to remind you to buy the gift?" ;
    }
    
    /**
     * Metodo che verifica la data del compleanno di tutti i friend che hanno il campo alertBirthDay = 0.
     * Si ottengono il mese e giorno della data corrente, della data del compleanno dell'i-esimo friend e si eseguono
     * i controlli. Se si ottiene un valore inferiore a 15 giorni(DAY_TWOWEEK) si crea l'avviso. Per semplicità tutti
     * i mesi sono stati considerati di 31 giorni.
     * 
     * @param currentMonth il mese della data corrente
     * @param currentDay  il giorno della data corrente
     */
    private void checkDate0(Friend f, int currentMonth, int currentDay){
    
         long birthTime = f.getDate().getTime();
                DateTime birthDate = new DateTime(birthTime);
                
                int birthMonth = birthDate.monthOfYear().get();
                int birthDay = birthDate.dayOfMonth().get();
                
                int resultMonth = birthMonth - currentMonth;
                
                //System.out.println("resultmese: " + resultMonth);
                
                
                    if (resultMonth == 0){
                        int resultDay = birthDay - currentDay;
                        if (resultDay >= 0 && resultDay <= DAYS_TWOWEEK){ 
                            System.out.println(f.getName() + " ACTION CREATED, resultDay: " + resultDay);
                            f.setAlertBirthday((byte)1);
                            sendUpdate(f);
                            advisor(f);
                        } //create action
                    }
                    else if (resultMonth == 1 || resultMonth == -11) {
                        int spareCurrent = DAYS_MONTH - currentDay + birthDay;
                        if (spareCurrent <= DAYS_TWOWEEK){ 
                            System.out.println(f.getName() + " ACTION CREATED, spare: " + spareCurrent);
                            f.setAlertBirthday((byte)1);
                            sendUpdate(f);
                            advisor(f);
                        } //create action
                    }
    }
    /**
     * Molto che si occupa di controllare tutti i friend che hanno il campo alertBirthDay = 1. Infatti se questo campo è
     * uguale a 1, sostanzialmente vuol dire che, o il friend deve festeggiare il compleanno in un arco di tempo compreso
     * tra la data di oggi e i 15 gg successivi, oppure che lo ha già festeggiato. Nel primo caso non verrà effettuata nessuna
     * operazione, mentre nel secondo si imposterà alertBirthDay = 0 per poter esser verificato l'anno successivo.
     * 
     */
    private void checkDate1(Friend f, int currentMonth, int currentDay){
    
                long birthTime = f.getDate().getTime();
                DateTime birthDate = new DateTime(birthTime);
                
                int birthMonth = birthDate.monthOfYear().get();
                int birthDay = birthDate.dayOfMonth().get();
                
                int resultMonth = birthMonth - currentMonth;
                
                //System.out.println("resultmese: " + resultMonth);
                
                
                    if (resultMonth == 0){
                        int resultDay = birthDay - currentDay;
                        if (!(resultDay >= 0 && resultDay <= DAYS_TWOWEEK)){ 
                            //System.out.println(f.getName() + " ACTION ALREADY CREATED, resultDay: " + resultDay);
                            f.setAlertBirthday((byte)0);
                            sendUpdate(f);
                        } //create action
                        
                    }
                    else if (resultMonth == 1 || resultMonth == -11) {
                        int spareCurrent = DAYS_MONTH - currentDay + birthDay;
                        if (!(spareCurrent <= DAYS_TWOWEEK)){ 
                            //System.out.println(f.getName() + " ACTION CREATED, spare: " + spareCurrent);
                            f.setAlertBirthday((byte)0);
                            sendUpdate(f);
                        } //create action
                    }
                    else{
                        f.setAlertBirthday((byte)0);
                    }
    }
    /**
     * Crea un oggetto con la data odierna (tramite la libreria JodaTime) e controlla la lista di friend
     */
     @Override
    public void run() {
        while (!this.isInterrupted()) {
            loadList();
            
            DateTime currentTime = new DateTime();
            int currentMonth = currentTime.monthOfYear().get();
            int currentDay = currentTime.dayOfMonth().get();
            
            for (Friend f : list) {
                
                
                if (f.getAlertBirthday() == 0){
                
                    checkDate0(f, currentMonth, currentDay);
                }
                
                else{
                    checkDate1(f, currentMonth, currentDay);
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
