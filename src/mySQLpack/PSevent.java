/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mySQLpack;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import memoassistant.Event;

/**
 *Gestisce i preparedstatement per gli eventi
 * @author Alessandro
 */
public class PSevent extends PrepStatememt{

    public PSevent(Connection connection, PreparedStatement preparedStatement) throws SQLException {
        super(connection, preparedStatement);
    }
    
   
    
    
    
     public void New(Event event, String email) {
       
                   
           //String className = event.getClass().getSimpleName();
           
            String query="INSERT INTO memo (`date`, `hour`, `where`, `what`, `latitude`, `longitude`, `type`, `conclused`, `warned`, `user_email`) "
                    + "VALUES (?, ?, ?, ?, default, default, ?, default, default, ?)";
           try{  

            preparedStatement = (PreparedStatement) connection.prepareStatement(query);

            preparedStatement.setDate(1, event.getEventDate());
            preparedStatement.setTime(2, event.getEventHour());
            preparedStatement.setString(3, event.getWhere());
            preparedStatement.setString(4, event.getWhat());
            preparedStatement.setString(5, "event");
            preparedStatement.setString(6, email);
           
            preparedStatement.executeUpdate();

           } catch (SQLException ex) {

               System.err.println("Error PSevent(New): " + ex.getMessage());
            }    
        }
    
    
   public void Update(Event event) {
       
                
        String query="UPDATE memo SET `date` = ?, `hour` = ?, `where` = ?, `what` = ?, `type` = ?, `conclused` = ?, `warned` = ? "
                + "WHERE `idmemo` = ?";
        
       try{  
           
        preparedStatement = (PreparedStatement) connection.prepareStatement(query);
          
        preparedStatement.setDate(1, event.getEventDate());
	preparedStatement.setTime(2, event.getEventHour());
	preparedStatement.setString(3, event.getWhere());
	preparedStatement.setString(4, event.getWhat());
 	preparedStatement.setString(5, "event");
        preparedStatement.setByte(6, event.getConclused());
        preparedStatement.setByte(7, event.getWarned());
        preparedStatement.setInt(8, event.getIdmemo());
       
        
        preparedStatement.executeUpdate();

       } catch (SQLException ex) {

           System.err.println("Error PSevent(Update): " + ex.getMessage());
     }
   } 
   
   
      public LinkedList<Event> askQuery(String email){
       
       email = "'" + email + "'";   
       String query = "SELECT * FROM memo WHERE type = 'event' AND user_email = " + email;
       System.out.println(query);
       LinkedList<Event> list = new LinkedList<>();
       
       try{
            resultSet = statement.executeQuery(query);
             

            while(resultSet.next()){
                     
               Event temp = new Event();
 
               temp.setIdmemo(resultSet.getInt("idmemo"));
               temp.setEventDate(resultSet.getDate("date"));
               temp.setEventHour(resultSet.getTime("hour"));
               temp.setWhere(resultSet.getString("where"));
               temp.setWhat(resultSet.getString("what"));
               temp.setConclused(resultSet.getByte("conclused"));
               temp.setWarned(resultSet.getByte("warned"));
               list.add(temp);

          }
            
        } catch (SQLException ex) {
    
            System.err.println("Error PSevent(ask): " + ex.getMessage());
        } 
     
       return list;
 }
      
    public LinkedList<Event> askThreadQuery(String email){
       
       email = "'" + email + "'";   
       String query = "SELECT * FROM memo WHERE type = 'event' AND conclused = '0' AND user_email = " + email;
       System.out.println(query);
       LinkedList<Event> list = new LinkedList<>();
       
       try{
            resultSet = statement.executeQuery(query);
             

            while(resultSet.next()){
                     
               Event temp = new Event();
 
               temp.setIdmemo(resultSet.getInt("idmemo"));
               temp.setEventDate(resultSet.getDate("date"));
               temp.setEventHour(resultSet.getTime("hour"));
               temp.setWhere(resultSet.getString("where"));
               temp.setWhat(resultSet.getString("what"));
               temp.setConclused(resultSet.getByte("conclused"));
               temp.setWarned(resultSet.getByte("warned"));
               list.add(temp);

          }
            
        } catch (SQLException ex) {
    
            System.err.println("Error PSevent(askThread): " + ex.getMessage());
        } 
     
       return list;
 }
            
     public void Delete(Event event) {
       
        String query="DELETE FROM memo WHERE idmemo = ?";
        
       try{  
           
        preparedStatement = (PreparedStatement) connection.prepareStatement(query);
          
        preparedStatement.setInt(1, event.getIdmemo());
        preparedStatement.executeUpdate();

       } catch (SQLException ex) {
    
    System.err.println("Error PSevent(delete): " + ex.getMessage());
    
       } 
   } 
}
