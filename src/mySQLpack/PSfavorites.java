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
import memoassistant.Favorites;


/**
 *Gestisce i preparedstatement per i preferiti
 * @author Alessandro
 */
public class PSfavorites extends PrepStatememt{

    public PSfavorites(Connection connection, PreparedStatement preparedStatement) throws SQLException {
        super(connection, preparedStatement);
    }
    
  
    
      

    public void New(Favorites favorites, String email) {
       
                   
           
            String query="INSERT INTO favorites (name, latitude, longitude, address, user_email) "
                    + "VALUES (?, ?, ?, ?, ?)";

           try{  

            preparedStatement = (PreparedStatement) connection.prepareStatement(query);

            preparedStatement.setString(1, favorites.getDescription());
            preparedStatement.setDouble(2, favorites.getLatitude());
            preparedStatement.setDouble(3,  favorites.getLongitude());
            preparedStatement.setString(4, favorites.getAddress());
            preparedStatement.setString(5, email);
            
            
            preparedStatement.executeUpdate();

           } catch (SQLException ex) {

               System.err.println("Error PSfavorites(New): " + ex.getMessage());
            }    
        }
    
    
   public void Update(Favorites favorites) {
       
                
        String query="UPDATE favorites SET name = ?, latitude = ?, longitude = ?, address = ? "
                + "WHERE idfavorites = ?";
        
       try{  
           
        preparedStatement = (PreparedStatement) connection.prepareStatement(query);
          
        preparedStatement.setString(1, favorites.getDescription());
	preparedStatement.setDouble(2, favorites.getLatitude());
	preparedStatement.setDouble(3, favorites.getLongitude());
	preparedStatement.setString(4, favorites.getAddress());
	        
        preparedStatement.setInt(5, favorites.getIdfavorites());

        preparedStatement.executeUpdate();

       } catch (SQLException ex) {

           System.err.println("Error PSfavorites(Update): " + ex.getMessage());
     }
   } 
   
   
      public LinkedList<Favorites> askQuery(String email){
       
       email = "'" + email + "'";   
       String query = "SELECT * FROM favorites WHERE user_email = " + email;
       System.out.println(query);
       LinkedList<Favorites> list = new LinkedList<>();
       
       try{
            resultSet = statement.executeQuery(query);
             

            while(resultSet.next()){
                     
               Favorites temp = new Favorites();

               temp.setIdfavorites(resultSet.getInt("idfavorites"));
               temp.setDescription(resultSet.getString("name"));
               temp.setLatitude(resultSet.getDouble("latitude"));
               temp.setLongitude(resultSet.getDouble("longitude"));
               temp.setAddress(resultSet.getString("address"));
               list.add(temp);
               
          }
            
        } catch (SQLException ex) {
    
            System.err.println("Error PSfavorites(ask): " + ex.getMessage());
        } 
     
       return list;
 }
      
     public void Delete(Favorites favorites) {
       
        
        String query="DELETE FROM favorites WHERE idfavorites = ?";
        
       try{  
           
        preparedStatement = (PreparedStatement) connection.prepareStatement(query);
          
        preparedStatement.setInt(1, favorites.getIdfavorites());
     
        preparedStatement.executeUpdate();

       } catch (SQLException ex) {
    
        System.err.println("Error PSfavorites(delete): " + ex.getMessage());
    
       } 
   } 
}
