package org.example;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Ledger {
    // initialization an arrayList which holds transaction objects and calling it
    public static ArrayList<Transaction> transactions = getTransactions();

    public static ArrayList<Transaction> getTransactions() { // Declaring a method called getTransactions
        // of the type arrayList that holds transactions objects
        ArrayList<Transaction> transactions = new ArrayList<>(); // making a new arraylist

        // this method loads transaction objects into transactions array
        try {// we're creating file-reader and buffer reader and passing the transaction.csv
            // file into it.

            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);

            String input;
            while ((input = bufReader.readLine()) != null) { //check ALL items until there's no more
                if (!input.isEmpty()) { // skip empty lines
                    String[] parts = input.split("\\|");
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    Transaction transaction = new Transaction(date, time, description, vendor, amount);
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) { // if the file can't be found, throw an error.
            System.out.println("File not found!");
            System.exit(0);
        }

        // Sort our transactions ArrayList in decscending order before returning it
        Comparator<Transaction> compareByDate = Comparator.comparing(Transaction::getDate).reversed();
        Comparator<Transaction> compareByTime = Comparator.comparing(Transaction::getTime).reversed();
        Comparator<Transaction> compareByDateTime = compareByDate.thenComparing(compareByTime);

        transactions.sort(compareByDateTime.thenComparing(compareByDateTime));
        // we're returning the transcations array list to our method
        return transactions;

    }

// ================================= LEDGER MENU =================================

    public static void showLedger() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n======================== Ledger Account =========================\n");
        System.out.println("Please type one of the following options: \n");
        System.out.println("[A] - All Entries");
        System.out.println("[D] - Deposits");
        System.out.println("[P] - Payments");
        System.out.println("[R] - Reports");
        System.out.println("[H] - Home");
        // using switch method to ask the user to choose an option
        String input = scanner.nextLine();
        switch (input.toUpperCase()) {
            case "A":
                showEntries();
                break;
            case "D":
                showDepositedEntries();
                break;
            case "P":
                showPaymentEntries();
                break;
            case "R":
                reportsMenu();
            case "H":
                Home.homescreen();
            default:
                System.out.println("\nPlease enter a valid option");
                break;
        }
        scanner.close();
    }



// =================================== SHOW ALL ENTRIES ===================================
// If user choose  'A'
    public static void showEntries() {
        System.out.println("\n======================== All Entries to Date =========================\n");
        for (Transaction item : transactions) {
            System.out.printf("%s %s -- %-20s\t\t %-20s\t\t $%.2f\n",
                    item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
        }
        showLedger();
    }



// =============================== SHOW DEPOSIT ENTRIES ===============================
// If user choose  'D'
    public static void showDepositedEntries() {
        System.out.println("\n======================== DEPOSIT HISTORY =========================\n");
        for (Transaction item : transactions) { // loop through each transaction object(item in the transactionlist
            // array list and check if the amount is positive(Deposits)
            if (item.getAmount() > 0) {
                // Printing out it's private variables using th getter methods
                System.out.printf("%s %s -- %-20s\t\t %-20s\t\t $%.2f\n",
                        item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
            }
        }
        showLedger();
    }

// ========================================= SHOW PAYMENT ENTRIES =================================
// If user choose  'P'
    public static void showPaymentEntries() {
        System.out.println("\n======================== PAYMENT HISTORY =========================\n");
        for (Transaction item : transactions) { // loop through each transaction object(item in the transactionlist
            // array list and check if the price is negative (payment)
            if (item.getAmount() < 0) {
                // array list and printing out it's private variables using the getter methods
                System.out.printf("%s %s -- %-20s\t\t %-20s\t\t $%.2f\n",
                        item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
            }

        }
        // After showing all payments(money spent), we show the main menu again
        showLedger();
    }



//================================== REPORTS MENU =========================================
//  If user choose  'R'
    public static void reportsMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n======================== REPORTS MENU =========================\n");

        // create report menu
        System.out.println(
                "Please type an option: \n1) Month to Date\n2) Previous Month\n3) Year to Date\n4) Previous year\n5) Search By Vendor\n0) Back");
        int userInput = scanner.nextInt();

        switch (userInput) {
            case 1:
                monthToDate();
                reportsMenu();
                break;
            case 2:
                previousMonth();
                reportsMenu();
                break;
            case 3:
                yearToDate();
                reportsMenu();
                break;
            case 4:
                previousYear();
                reportsMenu();
                break;
            case 5:
                scanner.nextLine(); // must have this to be able to let the user type a string after using .nextInt()
                System.out.print("\nName of Vendor: ");
                String name = scanner.nextLine();
                searchByVendor(name);
                reportsMenu();
                break;
            case 0:
                break;
            default:
                System.out.println("\nPlease type the correct option");
                break;
        }

        showLedger();
        scanner.close();
    }



    // ==============================REPORTS SECTION===========================
// If user choose  ' 1 '
    public static void monthToDate() {

        System.out.println("\n============= MONTH TO DATE REPORT (5/1/2023 - 5/5/2023) ======================\n");

        // loop through each item in the list
        for (Transaction item : transactions) {

            //need to make the date into a string first
            String separateString = item.getDate().toString();
            String dateParts[] = separateString.split("-");

            // make the string values into integers
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);

            item.getDate();// must have this to use localDate below
            // if each year item = year NOW (2023) AND each month item = month NOW (05):
            // print info
            if (year == LocalDate.now().getYear() && month == LocalDate.now().getMonthValue()) {
                System.out.printf("%s %s -- %-20s\t\t %s\t\t $%.2f\n",
                        item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
            }

        }
    }
// If user choose  ' 2 '
    public static void previousMonth() {

        System.out.println("\n============= PREVIOUS MONTHS REPORT (4/1/2023 - 4/30/2023) ======================\n");

        // loop through each item in the list
        for (Transaction item : transactions) {
            // make the dates into a string to get the values
            String separateString = item.getDate().toString();
            String dateParts[] = separateString.split("-");

            // make the string values into integers
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);

            item.getDate();// must have this to use localDate below
            // if each year item = year NOW (2023) AND each month item (ex: july is 07) =
            // month NOW (05) - 1 (04): print info
            if (year == LocalDate.now().getYear() && month == (LocalDate.now().getMonthValue() - 1)) {
                System.out.printf("%s %s -- %-20s\t\t %-20s\t\t $%.2f\n",
                        item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
            }
        }
    }
// If user choose  ' 3 '
    public static void yearToDate() {
        System.out.println("\n===============YEAR TO DATE REPORT (1/1/2023 - 5/5/2023)====================\n");


        // loop through each item in the list
        for (Transaction item : transactions) {
            // make the dates into a string to get the values
            String separateString = item.getDate().toString();
            String dateParts[] = separateString.split("-");

            // make the string values into integers
            int year = Integer.parseInt(dateParts[0]);

            item.getDate();// must have this to use localDate below
            // if each year item = year NOW (2023)
            if (year == LocalDate.now().getYear()) {
                System.out.printf("%s %s -- %-20s\t\t %-20s\t\t $%.2f\n",
                        item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
            }
        }

    }
// If user choose  ' 4 '
    public static void previousYear() {
        System.out.println("\n============= PREVIOUS YEARS REPORT (1/1/2022 - 12/31/2022)=====================\n");

        // loop through each item in the list
        for (Transaction item : transactions) {
            // make the dates into a string to get the values
            String separateString = item.getDate().toString();
            String dateParts[] = separateString.split("-");

            // make the string values into integers
            int year = Integer.parseInt(dateParts[0]);

            item.getDate();// must have this to use localDate below
            // if each year item = year NOW (2023) - 1 (2022)
            if (year == LocalDate.now().getYear() - 1) {
                System.out.printf("%s %s -- %-20s\t\t %-20s\t\t $%.2f\n",
                        item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
            }
        }

    }
// If user choose  ' 5 '
    public static void searchByVendor(String vendorName) {
        System.out.println("\n===================== SEARCH BY VENDOR REPORT ======================\n");

        // loop through each item in the list
        for (Transaction item : transactions) {

            if (vendorName.equalsIgnoreCase(item.getVendor())) {
                System.out.printf("%s %s -- %-20s\t\t %-20s\t\t $%.2f\n",
                        item.getDate(), item.getTime(), item.getDescription(), item.getVendor(), item.getAmount());
            }
        }
    }

}