/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package memoassistant;

import java.sql.Date;
import java.util.Objects;

/**
 *Estende la classe Persona e la specializza per gli utenti
 * @author Alessandro
 */
public class User extends Person{
    
    private String email;
    private String password;

    
    
    public User(String email, String password, String name, String surname, Date date) {
        super(name, surname, date);
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
  
    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

 
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "User{" + "email=" + email + ", password=" + password + " " + super.toString() + '}';
    }

    
    
    
  
    
    
}
