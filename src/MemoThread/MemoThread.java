/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MemoThread;


import memoassistant.*;

/**
 *Interfaccia che definisce il contratto delle classi che gestiscono i thread 
 * @author Alessandro
 */
public interface MemoThread{
    
  static final double EARTHRADIUS_METRE = 6371.00;
  static final double KILOMETRE_METRE = 1000.00;
  static final long DAY_MILLIS = 86400000;
  static final int MINUTE_MILLIS = 60000;
  static final long HOUR_MILLIS = 3600000;
  static final int DAYS_MONTH = 31;
  static final int DAYS_TWOWEEK = 15;
  
   void loadList();

   <T extends DataAccount> void sendUpdate(T memo);
    
}
