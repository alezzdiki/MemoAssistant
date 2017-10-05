/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package memoassistant;


import java.util.Objects;



/**
 *Superclasse astratta, 
 * @author Alessandro
 */
public abstract class Memo extends DataAccount{
    
    private String where;
    private String what;
    private int idmemo;
    private byte conclused;
    private byte warned;
    

    public Memo() {
       this.conclused = 0;
       this.warned = 0;
    }

    public Memo(String where, String what) {
        
        this.where = where;
        this.what = what;
        this.conclused = 0;
        this.warned = 0;
    }
    
    

    public int getIdmemo() {
        return idmemo;
    }

    public void setIdmemo(int idmemo) {
        this.idmemo = idmemo;
    }

    public byte getConclused() {
        return conclused;
    }

    public void setConclused(byte conclused) {
        this.conclused = conclused;
    }

    public byte getWarned() {
        return warned;
    }

    public void setWarned(byte warned) {
        this.warned = warned;
    }

    
    

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

   
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.where);
        hash = 89 * hash + Objects.hashCode(this.what);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Memo other = (Memo) obj;
        if (!Objects.equals(this.where, other.where)) {
            return false;
        }
        if (!Objects.equals(this.what, other.what)) {
            return false;
        }
        if (!Objects.equals(this.idmemo, other.idmemo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "where=" + where + ", what=" + what ;
    }

 
    

  
    
    
    
}
