package com.company;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.awt.*;

public class GeneratePassword {

    private final char[] symbols = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '/', '?', '[', ']', '{', '}', '+', '=', '-'};
    private final char[] numbers = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    private final char[] uppercase = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private final char[] lowercase = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private final char[] allChars = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '/', '?', '[', ']', '{', '}', '+', '=', '-', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private char[] password;
    private int passwordLength;
    private String website;

    public GeneratePassword() {

    }

    public GeneratePassword(String website, String password) {
        this.website = website;
        this.password = password.toCharArray();
    }

    public String getWebsite() {
        return this.website;
    }

    public String getPassword() {
        String s = new String(this.password);
        return s;
    }

    public char generateRandomChar(char[] arr) {
        int random = new Random().nextInt(arr.length);
        return arr[random];
    }

    public void shuffleArray(char[] arr) {
        Random rand = new Random();
        for(int i= arr.length-1; i >= 0; i--) {
            int index = rand.nextInt(i + 1);
            swap(arr, index, i);
        }
    }

    public void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void generateRandomPassword(String website) {
        Scanner in = new Scanner(System.in);
        boolean good = false;

        try {
            do {
                System.out.println("Enter a number between 10 - 25 to set the length of your password");
                int passLength = in.nextInt();
                if(passLength > 9 && passLength < 26) {
                    this.passwordLength = passLength;
                    good = true;
                } else {
                    System.out.println("Please adhere to the length requirements");
                }
            } while(!good);
        } catch(InputMismatchException e) {
            System.out.println("Please enter a number next time");
            generateRandomPassword(website);
        }


        this.website = website;

        this.password = new char[this.passwordLength];
        this.password[0] = generateRandomChar(this.symbols);
        this.password[1] = generateRandomChar(this.numbers);
        this.password[2] = generateRandomChar(this.uppercase);
        this.password[3] = generateRandomChar(this.lowercase);

        shuffleArray(this.allChars);
        int j = 4;

        while(j < this.passwordLength) {
            password[j] = generateRandomChar(this.allChars);
            j++;
        }

        shuffleArray(this.password);
    }

    public void copyToClipboard(String s) {
        StringSelection stringSelection = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        System.out.println("Password copied to clipboard!");
    }
}