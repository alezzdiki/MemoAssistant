/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package memoassistant;

import java.io.Serializable;

/**
 *Superclasse, da origine alla gerarchia di classi le cui istanze compongono un account. tutte le classi che la estendono
 * devono implementare il metodo compareTo, equals e toString
 * @author Alessandro
 */
public abstract class DataAccount implements Comparable, Serializable{

    public DataAccount() {
    }

    @Override
    public abstract int compareTo(Object o);

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object obj) ;
    
    

   
   
}
