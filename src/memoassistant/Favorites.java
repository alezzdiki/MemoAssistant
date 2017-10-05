/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package memoassistant;

import java.util.Objects;

/**
 *Classe che gestisce i preferiti di un utente
 * @author Alessandro
 */
public class Favorites extends DataAccount{
    
    private Double latitude, longitude; //coordinate geografiche di un preferito
    private String address; 
    private String description;
    private int idfavorites;    //identificativo di un preferito, ottenuto dal database
    
    
    public Favorites(){
        latitude = 0.0;
        longitude = 0.0;
   }

    public Favorites(Double lat, Double lon, String address, String description){
        this.latitude = lat;
        this.longitude = lon;
        this.address = address;
        this.description = description;
    }

    
    public Favorites(String address, String description) {
        this.address = address;
        this.description = description;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdfavorites() {
        return idfavorites;
    }

    public void setIdfavorites(int idfavorites) {
        this.idfavorites = idfavorites;
    }

    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Favorites other = (Favorites) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return Objects.equals(this.idfavorites, other.idfavorites);
    }

    @Override
    public String toString() {
        return "Favorites{" + "Lat=" + latitude + ", Long=" + longitude + ", address=" + address + ", description=" + description + '}';
    }

    @Override
    public int compareTo(Object o) {

        Favorites otherFavorites = (Favorites) o;
        int result = this.description.compareToIgnoreCase(otherFavorites.description);
        return result;
    }

    

   


  
    
    
  
    
    
}
