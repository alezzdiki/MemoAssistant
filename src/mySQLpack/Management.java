/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mySQLpack;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *Classe che gestisce l'interfacciamento col databse
 * @author Alessandro
 */
public class Management {
    
    public final String ID = "root";
    public final String PASSWORD = "memoassistant";
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private Statement statement;

   
    public Management(){
    
        connection = null;
        resultSet = null;
        preparedStatement = null;
        statement = null;
        
    }
    
    /**
     * Carica i driver
     * @return ritorna un oggetto connection
     */
    public Connection getconn(){
    
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) 
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/memoassistantdbms", ID, PASSWORD);
             //statement = (Statement) connection.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Errore connessione" + ex);
        }
        
        return connection;
    }
}
