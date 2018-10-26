/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mySQLpack;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import memoassistant.Friend;


/**
 *Gestisce i preparedstatement i friends
 * @author Alessandro
 */
public class PSfriend extends PrepStatememt{

    public PSfriend(Connection connection, PreparedStatement preparedStatement) throws SQLException {
        super(connection, preparedStatement);
    }
    
   
        

    public void New(Friend friend, String email) {
       
                   
           
            String query="INSERT INTO friend (`name`, `surname`, `dateofbirth`, `category`, `interests`, `alertBirthday`, `user_email`) "
                    + "VALUES (?, ?, ?, ?, ?, default, ?)";

           try{  

            preparedStatement = (PreparedStatement) connection.prepareStatement(query);

            preparedStatement.setString(1, friend.getName());
            preparedStatement.setString(2, friend.getSurname());
            preparedStatement.setDate(3, (Date) friend.getDate());
            preparedStatement.setString(4, friend.getCategory());
            preparedStatement.setString(5, friend.getInteressi());
            preparedStatement.setString(6, email);
            
            preparedStatement.executeUpdate();

           } catch (SQLException ex) {

               System.err.println("Error PSfriend(New): " + ex.getMessage());
            }    
        }
    
    
   public void Update(Friend friend) {
       
                
        String query= "UPDATE friend SET name = ?, surname = ?, dateofbirth = ?, category = ?, interests = ?, alertBirthday = ? "
                + "WHERE idfriend = ?";
        System.out.println(query + "\nNome: " + friend.getIdfriend());
       try{  
           
        preparedStatement = (PreparedStatement) connection.prepareStatement(query);
          
        preparedStatement.setString(1, friend.getName());
	preparedStatement.setString(2, friend.getSurname());
	preparedStatement.setDate(3, (Date) friend.getDate());
	preparedStatement.setString(4, friend.getCategory());
	preparedStatement.setString(5, friend.getInteressi());
        preparedStatement.setByte(6, friend.getAlertBirthday());
        preparedStatement.setInt(7, friend.getIdfriend());
        preparedStatement.executeUpdate();

       } catch (SQLException ex) {

           System.err.println("Error PSfriend(Update): " + ex.getMessage());
     }
   } 
   
   
      public LinkedList<Friend> askQuery(String email){
       
       email = "'" + email + "'";   
       String query = "SELECT * FROM friend WHERE user_email = " + email;
       System.out.println(query);
          LinkedList<Friend> list = new LinkedList<>();
      
       try{
            resultSet = statement.executeQuery(query);
             

            while(resultSet.next()){
               
               Friend temp = new Friend();

               temp.setIdfriend(resultSet.getInt("idfriend"));
               temp.setName(resultSet.getString("name"));
               temp.setSurname(resultSet.getString("surname"));
               temp.setDate(resultSet.getDate("dateofbirth"));
               temp.setCategory(resultSet.getString("category"));
               temp.setInteressi(resultSet.getString("interests"));
               temp.setAlertBirthday(resultSet.getByte("alertBirthday"));
               list.add(temp);
               //System.out.println("ci sono into psf");

          }
            
        } catch (SQLException ex) {
    
            System.err.println("Error PSfriend(ask): " + ex.getMessage());
        } 
     
       return list;
 }
      
     public void Delete(Friend friend) {
       
        String query="DELETE FROM friend WHERE `idfriend` = ?";
        System.out.println(query);
       try{  
           
        preparedStatement = (PreparedStatement) connection.prepareStatement(query);
          
        preparedStatement.setInt(1, friend.getIdfriend());
        preparedStatement.executeUpdate();

       } catch (SQLException ex) {
    
    System.err.println("Error PSfriend(delete): " + ex.getMessage());
    
       } 
   } 
}
