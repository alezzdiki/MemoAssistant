/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package memoassistant;


import java.util.Objects;
import org.joda.time.DateTimeComparator;
import java.sql.*;
 
/**
 *Estende Memo, aggiunge data e orario per collocare un evento nel tempo.
 * @author Alessandro
 */
public class Event extends Memo{
    
    private Date eventDate;
    private Time eventHour;
    

    public Event() {
        super();
    }
    


    public Event(Date eventDate, Time eventHour, String where, String what) {
        super(where, what);
        this.eventDate = eventDate;
        this.eventHour = eventHour;
        
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Time getEventHour() {
        return eventHour;
    }

    public void setEventHour(Time eventHour) {
        this.eventHour = eventHour;
    }

 
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (!Objects.equals(this.eventDate, other.eventDate)) {
            return false;
        }
        if (!Objects.equals(this.eventHour, other.eventHour)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Event{" + "Date = " + eventDate  + " Hour = " + eventHour + " " + super.toString() + '}';
    }

    @Override
    public int compareTo(Object o) {
        
        Event otherEvent = (Event) o;
        int result = DateTimeComparator.getDateOnlyInstance().compare(this.eventDate, otherEvent.eventDate);
        if (result != 0)
            return result;
        return DateTimeComparator.getTimeOnlyInstance().compare(this.eventHour, otherEvent.eventHour);
    }
    
    
    
    
}
