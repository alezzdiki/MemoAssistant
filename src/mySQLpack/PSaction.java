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
import memoassistant.Action;

/**
 *Gestisce i preparedstatement per le action
 * @author Alessandro
 */
public class PSaction extends PrepStatememt{

    
    public PSaction(Connection connection, PreparedStatement preparedStatement) throws SQLException {
        super(connection, preparedStatement);
    }
    
   
    
    
     public void New(Action action, String email) {
       
                   
           //String className = event.getClass().getSimpleName();
           
            String query="INSERT INTO memo (`date`, `hour`, `where`, `what`, `latitude`, `longitude`, `type`, `conclused`, `warned`, `user_email`) "
                    + "VALUES (default, default, ?, ?, ?, ?, ?, default, default, ?)";

           try{  

            preparedStatement = (PreparedStatement) connection.prepareStatement(query);

            //preparedStatement.setDate(1, null);
            //preparedStatement.setTime(2, null);
            preparedStatement.setString(1, action.getWhere());
            preparedStatement.setString(2, action.getWhat());
            preparedStatement.setDouble(3, action.getLatitude());
            preparedStatement.setDouble(4, action.getLongitude());
            preparedStatement.setString(5, "action");
            preparedStatement.setString(6, email);

           
            preparedStatement.executeUpdate();

           } catch (SQLException ex) {

               System.err.println("Error PSaction(New): " + ex.getMessage());
            }    
        }
    
    
   public void Update(Action action) {
       
                
        String query="UPDATE memo SET `where` = ?, `what` = ?, `latitude` = ?, `longitude` = ?, `type` = ?, `conclused` = ?, `warned` = ? "
                + "WHERE `idmemo` = ?";
        
       try{  
           
        preparedStatement = (PreparedStatement) connection.prepareStatement(query);
          
        
	preparedStatement.setString(1, action.getWhere());
	preparedStatement.setString(2, action.getWhat());
        preparedStatement.setDouble(3, action.getLatitude());
        preparedStatement.setDouble(4, action.getLongitude());
 	preparedStatement.setString(5, "action");
        preparedStatement.setByte(6, action.getConclused());
        preparedStatement.setByte(7, action.getWarned());
        preparedStatement.setInt(8, action.getIdmemo());
       
        
        preparedStatement.executeUpdate();

       } catch (SQLException ex) {

           System.err.println("Error PSaction(Update): " + ex.getMessage());
     }
   } 
   
   
      public LinkedList<Action> askQuery(String email){
       
       email = "'" + email + "'";   
       String query = "SELECT * FROM memo WHERE type = 'action' AND user_email = " + email;
       System.out.println(query);
       LinkedList<Action> list = new LinkedList<>();

      
       try{
            resultSet = statement.executeQuery(query);
             

            while(resultSet.next()){
               
               Action temp = new Action(); 
               
               temp.setIdmemo(resultSet.getInt("idmemo"));
               temp.setWhere(resultSet.getString("where"));
               temp.setWhat(resultSet.getString("what"));
               temp.setLatitude(resultSet.getDouble("latitude"));
               temp.setLongitude(resultSet.getDouble("longitude"));
               temp.setConclused(resultSet.getByte("conclused"));
               temp.setWarned(resultSet.getByte("warned"));
               
               list.add(temp);

               
          }
            
        } catch (SQLException ex) {
    
            System.err.println("Error PSaction(ask): " + ex.getMessage());
        } 
     
       return list;
 }
      
  
      public LinkedList<Action> askThreadQuery(String email){
       
       email = "'" + email + "'";   
       String query = "SELECT * FROM memo WHERE type = 'action' AND conclused = '0' AND warned = '0' AND user_email = " + email;
       System.out.println(query);
       LinkedList<Action> list = new LinkedList<>();
       
       try{
            resultSet = statement.executeQuery(query);
             

            while(resultSet.next()){
                     
               Action temp = new Action();
 
               temp.setIdmemo(resultSet.getInt("idmemo"));
               temp.setWhere(resultSet.getString("where"));
               temp.setWhat(resultSet.getString("what"));
               temp.setLatitude(resultSet.getDouble("latitude"));
               temp.setLongitude(resultSet.getDouble("longitude"));
               temp.setConclused(resultSet.getByte("conclused"));
               temp.setWarned(resultSet.getByte("warned"));
               list.add(temp);

               
          }
            
        } catch (SQLException ex) {
    
            System.err.println("Error PSaction(askThread): " + ex.getMessage());
        } 
     
       return list;
 }
      
      
     public void Delete(Action action) {
       
        String query="DELETE FROM memo WHERE idmemo = ?";
        
       try{  
           
        preparedStatement = (PreparedStatement) connection.prepareStatement(query);
          
        preparedStatement.setInt(1, action.getIdmemo());
        preparedStatement.executeUpdate();

       } catch (SQLException ex) {
    
    System.err.println("Error PSaction(delete): " + ex.getMessage());
    
       } 
   } 
}
