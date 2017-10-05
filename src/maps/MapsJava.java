/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package maps;

import java.util.ArrayList;
import org.w3c.dom.NodeList;

/**
 * Classe astratta, serve principalmente per impostare dei requisiti al momento di effettuare una richiesta
 * @author Alessandro
 */
public abstract class MapsJava {

    
    private static String region="it";
    private static String language="it";
    private static final Boolean sensor= Boolean.FALSE; //indica a Google che non si sta usando un sensore GPS
    private static String APIKey=""; //here a Google key it is required
    
  
    /**
     * Ritorna sottoforma di stringa, tutte le proprietà necessarie per una richiesta
     * @return 
     */
    
    protected String getSelectPropertiesRequest(){
        return "&region=" + MapsJava.region + "&language=" + MapsJava.language + 
                "&sensor=" + MapsJava.sensor;
    }
    
    
         
        
    /**
    * Author: Christian d'Heureuse (www.source-code.biz)
    * Reallocates an array with a new size, and copies the contents
    * of the old array to the new array.
    * @param oldArray  the old array, to be reallocated.
    * @param newSize   the new array size.
    * @return          A new array with the same contents.
    */
    protected Object resizeArray (Object oldArray, int newSize) {
       int oldSize = java.lang.reflect.Array.getLength(oldArray);
       Class elementType = oldArray.getClass().getComponentType();
       Object newArray = java.lang.reflect.Array.newInstance(
             elementType, newSize);
       int preserveLength = Math.min(oldSize, newSize);
       if (preserveLength > 0)
          System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
       return newArray; 
    }
    
    
   
    public static String getRegion() {
        return region;
    }
    
    
    public static void setRegion(String aRegion) {
        region = aRegion;
    }

    
    public static String getLanguage() {
        return language;
    }
    
    public static void setLanguage(String aLanguage) {
        language = aLanguage;
    }

    
    
    /**
     * Restituisce la APIkey. Necessario solo per google Places
     * @return la string con la chiave
     */
    public static String getKey() {
        return APIKey;
    }

    /**
     * Imposta la APIkey
     * @param aKey la chiave
     */
    public static void setKey(String aKey) {
        APIKey = aKey;
    }

  
}
