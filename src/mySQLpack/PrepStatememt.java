/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mySQLpack;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *Classe astratta che racchiude tutte le proprietà necessarie per un preparedstatement
 * @author Alessandro
 */
public abstract class PrepStatememt {
    
    protected Connection connection;
    protected ResultSet resultSet;
    protected PreparedStatement preparedStatement;
    protected Statement statement;
    

    public PrepStatememt(Connection connection, PreparedStatement preparedStatement) throws SQLException {
       
        this.connection = connection;
        this.preparedStatement = preparedStatement;
        statement = (Statement) connection.createStatement();
        resultSet = null;
        
    }
    
   /*public PrepStatememt() {
    }*/
    
   // public  void New(DataAccount dataAccount){}

    
    
}
