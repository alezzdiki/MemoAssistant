/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memoassistant;

import java.sql.Date;
import java.util.Objects;

/**
 * Classe astratta che implementa tutti i suoi metodi
 * @author Alessandro
 */
public abstract class Person extends DataAccount {

    
    private String name;
    private String surname;
    private Date date;  //data di nascita

    public Person(String name, String surname, Date date) {
        this.name = name;
        this.surname = surname;
        this.date = date;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "name=" + name + ", surname=" + surname + ", date=" + date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        return Objects.equals(this.date, other.date);
    }

    @Override
    public int compareTo(Object o) {

        Person otherPerson = (Person) o;
        int result = this.surname.compareToIgnoreCase(otherPerson.surname);
        if (result != 0) {
            return result;
        }
        return this.name.compareToIgnoreCase(otherPerson.name);
    }

}
