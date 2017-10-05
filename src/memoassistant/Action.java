/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package memoassistant;

import java.util.Objects;

/**
 *Estende memo, aggiunge latitudine e longitudine
 * @author Alessandro
 */
public class Action extends Memo{
    
    
    private Double latitude, longitude;
    
    

    public Action() {
        super();
    }

    public Action(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

   public Action(String what, String where, Double latitude, Double longitude) {
        super(where, what);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Action other = (Action) obj;
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Action{" + "latitude= " + latitude + " " + "longitude= " + longitude + " " + super.toString() + '}';
    }

    @Override
    public  int compareTo(Object o){
        
       Action otherAction = (Action) o;
	return this.getIdmemo() - otherAction.getIdmemo();
}
    
}
