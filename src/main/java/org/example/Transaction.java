package org.example;
import java.time.LocalDate;
import java.time.LocalTime;

//================================= PROPERTIES ===================================
public class Transaction {
    private LocalDate date;
    private LocalTime time;                     // my properties to return the information
    private String description;
    private String vendor;
    private double amount;

//=============================== CONSTRUCTOR ========================================
    //our constructor that we can call on other classes, with our parameters
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
//================================== GETTERS ========================================
    public LocalDate getDate() {    //return the date (access data)
        return date;
    }

    public LocalTime getTime() {    //returns the time (access time)
        return time;
    }

    public String getDescription() {    //return the description (access description)
        return description;
    }

    public String getVendor() {     //return the vendor (access vendor)
        return vendor;
    }

    public double getAmount() {     //return the amount (access amount)
        return amount;
    }

}
