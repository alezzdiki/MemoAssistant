/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mySQLpack;

import com.mysql.jdbc.PreparedStatement;
import java.sql.*;
import java.util.LinkedList;
import memoassistant.*;

/**
 *Gestisce i preparedstatement l'user
 * @author Alessandro
 */
public class PSuser extends PrepStatememt{

    public PSuser(Connection connection, PreparedStatement preparedStatement) throws SQLException {
        super(connection, preparedStatement);
    }

  
    

    public void New(User user) {
       
                   
           
            String query="INSERT INTO user VALUES (?, ?, ?, ?, ?)";

           try{  

            preparedStatement = (PreparedStatement) connection.prepareStatement(query);

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setDate(5, (Date) user.getDate());
            
            preparedStatement.executeUpdate();

           } catch (SQLException ex) {

               System.err.println("Error PSuser(New): " + ex.getMessage());
            }    
        }
    
    public void Update(User user) {
        
        String query = "UPDATE User SET  password = ?, name = ?, surname = ?, dateofbirth = ?  "
                + "WHERE email = ?";
        
       try{  
           
        preparedStatement = (PreparedStatement) connection.prepareStatement(query);
          
        preparedStatement.setString(5, user.getEmail());
	preparedStatement.setString(1, user.getPassword());
	preparedStatement.setString(2, user.getName());
	preparedStatement.setString(3, user.getSurname());
	preparedStatement.setDate(4, (Date) user.getDate());
     
        preparedStatement.executeUpdate();

       } catch (SQLException ex) {
 
           System.err.println("Error PSuser(Update): " + ex.getMessage());
       }
   }
    
      public LinkedList<User> askQuery(String email){
       
       email = "'" + email + "'";
       String query = "SELECT * FROM user WHERE email = " + email ;
       System.out.println(query);
       LinkedList<User> list = new LinkedList<>();

       try{
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
               
               User temp = new User();
                
               temp.setEmail(resultSet.getString("email"));
               temp.setPassword(resultSet.getString("password"));
               temp.setName(resultSet.getString("name"));
               temp.setSurname(resultSet.getString("surname"));
               temp.setDate(resultSet.getDate("dateofbirth"));
               list.add(temp);
          }
            
        } catch (SQLException ex) {
    
            System.err.println("Error PSuser(ask): " + ex.getMessage());
        } 
     
       return list;
 }
      
     public void Delete(User user) {
       
        String query="DELETE FROM user WHERE email = ?";
        
       try{  
           
        preparedStatement = (PreparedStatement) connection.prepareStatement(query);
          
        preparedStatement.setString(1, user.getEmail());
        //preparedStatement.setString(2, user.getPassword());
        preparedStatement.executeUpdate();

       } catch (SQLException ex) {
    
    System.err.println("Error PSuser(delete): " + ex.getMessage());
    
       } 
   }    
        
}
    



