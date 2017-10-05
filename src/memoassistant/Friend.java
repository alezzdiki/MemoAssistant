/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package memoassistant;

import java.sql.Date;
import java.util.Objects;

/**
 *Estende la classe astratta persona, e la specializza aggiungendo nuovi membri
 * @author Alessandro
 */
public class Friend extends Person{
    
    private int idfriend;   //identificativo di un friend, viene assegnato dal database
    private String interessi; // viaggi, sport, libri
    private String categ;   //famiglia, amici
    private byte alertBirthday; //indica se per il friend è stato impostato un avviso per il regalo di compleanno
    
    
        
    
    public Friend() {
    }

    
    public Friend(String interessi, String categ, String name, String surname, Date date) {
        super(name, surname, date);
        this.interessi = interessi;
        this.categ = categ;
    }

    public Friend(String interessi, String categ) {
        this.interessi = interessi;
        this.categ = categ;
    }

    
   
    public byte getAlertBirthday() {
        return alertBirthday;
    }

    public void setAlertBirthday(byte alertBirthday) {
        this.alertBirthday = alertBirthday;
    }

    
    
    public int getIdfriend() {
        return idfriend;
    }

    public void setIdfriend(int idfriend) {
        this.idfriend = idfriend;
    }

    
    public String getInteressi() {
        return interessi;
    }

    public void setInteressi(String interessi) {
        this.interessi = interessi;
    }

    public String getCategory() {
        return categ;
    }

    public void setCategory(String categ) {
        this.categ = categ;
    }

    @Override
    public String toString() {
        return "Friend{" + super.toString() + " " + "interessi=" + interessi + ", categ=" + categ + '}';
    }
 @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Friend other = (Friend) obj;
        if (!Objects.equals(this.idfriend, other.idfriend)) {
            return false;
        }
        if (!Objects.equals(this.interessi, other.interessi)) {
            return false;
        }
        if (!Objects.equals(this.categ, other.categ)) {
            return false;
        }
        return super.equals(obj);
    }

  
    
}
