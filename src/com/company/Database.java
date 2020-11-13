package com.company;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private Connection connection;
    private String url;
    private String user;
    private String password;

    //"jdbc:mysql://localhost:3306/keypass"
    public Database(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        createConnection();
    }

    public void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void insertIntoTable(String website, String password) {
        try {
            Statement statement = connection.createStatement();
            String query = String.format("INSERT INTO passwords VALUES('%s','%s')", website, password);
            statement.execute(query);
            statement.close();
            System.out.println("Query insert successful");
        } catch(SQLIntegrityConstraintViolationException e) {
            System.out.println("There is already a password for that website...");
            System.exit(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getPassword() {
        String password = "";

        System.out.println("From the list below, for which website would you like to retrieve the password for? (enter exactly as it appears)\n");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT website FROM passwords");

            while(resultSet.next()) {
                String website = resultSet.getString(1);
                System.out.println(website);
            }

            Scanner in = new Scanner(System.in);
            String userRequest = in.nextLine();

            resultSet = statement.executeQuery("SELECT password FROM passwords WHERE website = " + "'" + userRequest + "'");
            while(resultSet.next()) {
                password = resultSet.getString("password");
                copyToClipboard(password);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updatePassword() {
        System.out.println("From the list below, for which website would you like to retrieve the password for? (enter exactly as it appears)\n");

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT website FROM passwords");

            while(resultSet.next()) {
                String website = resultSet.getString(1);
                System.out.println(website);
            }

            Scanner in = new Scanner(System.in);
            String website = in.nextLine();

            System.out.println("Would you like to: \n" +
                               "1. Generate a new random password\n" +
                               "2. Set your own password");

            int userOption = 0;

            do {
                try {
                    userOption = in.nextInt();
                } catch (InputMismatchException e) {
                    System.out.print("Please enter a number next time\n");
                }
                in.nextLine(); // clears the buffer
            } while (userOption < 1 || userOption > 2);

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE passwords SET password = ? WHERE website = ?");

            if(userOption == 1) {
                GeneratePassword newPassword = new GeneratePassword();
                newPassword.generateRandomPassword(website);
                preparedStatement.setString(1, newPassword.getPassword());
                preparedStatement.setString(2, website);
                preparedStatement.executeUpdate();
            } else {
                System.out.println("Enter in your new password: ");
                String password = in.nextLine();
                GeneratePassword userPassword = new GeneratePassword(website, password);
                preparedStatement.setString(1, userPassword.getPassword());
                preparedStatement.setString(2, website);
                preparedStatement.executeUpdate();
            }

            System.out.println("Password updated!");
            preparedStatement.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void copyToClipboard(String s) {
        StringSelection stringSelection = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        System.out.println("Password copied to clipboard!");
    }

}
