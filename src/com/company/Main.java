package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to KeyPass! What would you like to do?\nEnter a number from the corresponding options: ");
        System.out.println("1: Generate a new random password for a website\n" +
                           "2: Set your own password for a website\n" +
                           "3: Update a password\n" +
                           "4: Retrieve a password");
        Scanner in = new Scanner(System.in);
        int userOption = 0;

        do {
            try {
                userOption = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Please enter a number next time\n");
            }
            in.nextLine(); // clears the buffer
        } while (userOption < 1 || userOption > 4);

        Database db = new Database("jdbc:mysql://localhost:3306/keypass","root", "root");

        switch(userOption) {
            case 1:
                GeneratePassword newPassword = new GeneratePassword();
                System.out.println("For which website or application will this password be used for? (i.e for " +
                        "Bankofamerica.com use 'Bank of America')");

                String website = in.nextLine();
                newPassword.generateRandomPassword(website);
                db.insertIntoTable(newPassword.getWebsite(), newPassword.getPassword());
                newPassword.copyToClipboard(newPassword.getPassword());
                break;
            case 2:
                System.out.println("For which website/application will this password be used for?");
                website = in.nextLine();
                System.out.println("Enter in your new password: ");
                String password = in.nextLine();
                GeneratePassword userPassword = new GeneratePassword(website, password);
                db.insertIntoTable(userPassword.getWebsite(), userPassword.getPassword());
                userPassword.copyToClipboard(userPassword.getPassword());
                break;
            case 3:
                db.updatePassword();
                break;
            case 4:
                db.getPassword();
                break;
            default:
                break;
        }
    }
}
