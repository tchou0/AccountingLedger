package org.example;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Home {
    public static void main(String[] args) {
        homescreen();
    }


    public static void homescreen() {
        Scanner scanner = new Scanner(System.in);   //create scanner for user inputs

        //menu what the user will see
        System.out.println("\n======================== Welcome! MAIN MENU =========================\n");
        System.out.println("[D] - Add Deposit");
        System.out.println("[P] - Make a Payment");
        System.out.println("[L] - Ledger");
        System.out.println("[X] - Exit");

        // asking the user to choose an option (has to be a string)
        String input = scanner.nextLine();      //input = to users input, user typed

        //use switch method to give options to users and if they select that option, do what's inside
//switch(expression) = what letter the user will type
        switch (input.toUpperCase()){      //use switch statement, we get the user input and use .toUpperCase() if the user typed lowercase
            case"D":
                makeADeposit();
                break;
            case"P":
                makeAPayment();
                break;
            case"L":
                showLedger();
                break;
            case"X":
                System.exit(0);
            default:                //if they don't type the letters above, say it's invalid
                System.out.println("\nPlease enter a valid option");
                break;
        }
        scanner.close();
    }


    public static void makeADeposit(){   // using Scanner to get user's input and storing it in a corresponding variable
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n======================== Your are now making a deposit =========================\n");

        LocalDate date = LocalDate.now();       //right now date = 05-04-2023

        LocalTime time = LocalTime.now();   // variable time = time right now       time now: 05:45:30.0439234234
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");      //need to format time to be HH:mm:ss
        String formattedtime = time.format(timeFormatter);      //example: 05:45:30

        System.out.println("Enter description: ");
        String description = scanner.nextLine();

        System.out.println("Enter Vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter Transaction Amount: ");
        double amount = scanner.nextDouble();   //amount has to be a double for prices

        try ( // writing the information from the variables to the csv file
              FileWriter fileWriter = new FileWriter("transactions.csv", true)){
            //add |  in between the variables
            fileWriter.write("\n" +
                    date + "|" + //this is our date variable
                    formattedtime + "|" + //this is our time variable
                    description + "|" +  //our description variable (user input)
                    vendor + "|" +  //name of vendor (user input)
                    amount   //amount from user input
            );
            fileWriter.close();
            System.out.println("\nDEPOSIT SUCCESSFUL");
        }
        catch(IOException e){
            System.out.println("\nError inputting data!");
        }
        homescreen();
        scanner.close();
    }


    public static void makeAPayment() {                 //using Scanner to get user's input and storing it in the corresponding variable
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n======================== Your are now making a payment =========================\n");


        LocalDate date = LocalDate.now();   //get today's date

        LocalTime time = LocalTime.now();   //need to format time to be HH:mm:ss
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedtime = time.format(timeFormatter);

        System.out.println("Enter Description:");
        String description = scanner.nextLine();
        System.out.println("Enter Vendor:");
        String vendor = scanner.nextLine();
        System.out.println("Enter Transaction Amount:");
        double amount = scanner.nextDouble();           //

        // use fileWriter to put data in our transactions.csv file
        try (FileWriter fileWriter = new FileWriter("transactions.csv", true)){
            fileWriter.write("\n" +
                    date + "|" +
                    formattedtime + "|" +
                    description + "|" +
                    vendor + "|" + "-" +
                    amount
            );
            fileWriter.close();
            System.out.println("\nPAYMENT SUCCESSFUL");
        }
        // Print out a error message when the input is wrong
        catch(IOException e){
            System.out.println("\nError inputting data!");
        }
        homescreen();
        scanner.close();

    }

    //Calling the showLedger()method from the ledger class
    public static void showLedger() {
        Ledger.showLedger();
    }

}