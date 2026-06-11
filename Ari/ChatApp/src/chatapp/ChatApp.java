package chatapp;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class ChatApp {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        LoginFeature check = new LoginFeature();
        
        // registration
        System.out.println("========================================");
        System.out.println("            WELCOME TO CHATAPP");
        System.out.println("========================================\n");
        
        System.out.print("Enter first name: ");
        String fname = scan.nextLine();
        System.out.print("Enter last name: ");
        String lname = scan.nextLine();
        System.out.println();
        
        String username;
        do {
            System.out.print("Enter username (must contain _ and be ≤5 chars): ");
            username = scan.nextLine();
            if (!check.checkUserName(username))
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        } while (!check.checkUserName(username));
        System.out.println("Username successfully captured.\n");
        
        String password;
        do {
            System.out.print("Enter password (8+ chars, 1 capital, 1 number, 1 special): ");
            password = scan.nextLine();
            if (!check.checkPasswordComplexity(password))
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        } while (!check.checkPasswordComplexity(password));
        System.out.println("Password successfully captured.\n");
        
        String mobile;
        do {
            System.out.print("Enter cell phone number (+27 then 9 digits): ");
            mobile = scan.nextLine();
            if (!check.checkCellPhoneNumber(mobile))
                System.out.println("Cell phone number incorrectly formatted or does not contain international code; please correct the number and try again.");
        } while (!check.checkCellPhoneNumber(mobile));
        System.out.println("Cell phone number successfully added.\n");
        
        String regResult = check.registerUser(username, password, fname, lname, mobile);
        System.out.println(regResult);
        
        // login
        System.out.println("\n========================================");
        System.out.println("              LOGIN SECTION");
        System.out.println("========================================\n");
        boolean logged = false;
        while (!logged) {
            System.out.print("Enter username: ");
            String u = scan.nextLine();
            System.out.print("Enter password: ");
            String p = scan.nextLine();
            logged = check.loginUser(u, p);
            if (logged)
                System.out.println("\n" + check.returnLoginStatus(true, fname, lname));
            else
                System.out.println("Username or password incorrect, please try again.\n");
        }
        
        // Part 2 + Part 3 menu
        System.out.println("\n========================================");
        System.out.println("          Welcome to ChatApp");
        System.out.println("========================================");
        
        // parallel arrays for Part 3
        ArrayList<String> sentList = new ArrayList<>();
        ArrayList<String> storedList = new ArrayList<>();
        ArrayList<String> discardList = new ArrayList<>();
        ArrayList<String> idList = new ArrayList<>();
        ArrayList<String> hashList = new ArrayList<>();
        ArrayList<String> recipList = new ArrayList<>();
        ArrayList<String> textList = new ArrayList<>();
        
        int totalSent = 0;
        boolean quit = false;
        
        while (!quit) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages (Coming Soon)");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages (Part 3)");
            System.out.print("Choose an option: ");
            int choice = scan.nextInt();
            scan.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("How many messages do you want to send? ");
                    int howMany = scan.nextInt();
                    scan.nextLine();
                    
                    for (int i = 1; i <= howMany; i++) {
                        System.out.println("\n--- Message " + i + " ---");
                        
                        String receiver;
                        do {
                            System.out.print("Recipient cell number (+27 then 9 digits): ");
                            receiver = scan.nextLine();
                            if (!check.checkCellPhoneNumber(receiver))
                                System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                        } while (!check.checkCellPhoneNumber(receiver));
                        
                        String messageText;
                        while (true) {
                            System.out.print("Message (max 250 characters): ");
                            messageText = scan.nextLine();
                            if (messageText.length() <= 250) {
                                System.out.println("Message ready to send.");
                                break;
                            } else {
                                int excess = messageText.length() - 250;
                                System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size.");
                            }
                        }
                        
                        Message msg = new Message(i, receiver, messageText);
                        
                        System.out.println("\nWhat would you like to do?");
                        System.out.println("1. Send Message");
                        System.out.println("2. Disregard Message");
                        System.out.println("3. Store Message to send later");
                        System.out.print("Choice: ");
                        int action = scan.nextInt();
                        scan.nextLine();
                        
                        switch (action) {
                            case 1:
                                System.out.println("Message successfully sent");
                                totalSent++;
                                sentList.add(msg.getText());
                                idList.add(msg.getId());
                                hashList.add(msg.getHash());
                                recipList.add(msg.getReceiver());
                                textList.add(msg.getText());
                                break;
                            case 2:
                                System.out.println("Press 0 to delete the message");
                                discardList.add(msg.getText());
                                break;
                            case 3:
                                System.out.println("Message successfully stored");
                                msg.saveToFile();
                                storedList.add(msg.getText());
                                idList.add(msg.getId());
                                hashList.add(msg.getHash());
                                recipList.add(msg.getReceiver());
                                textList.add(msg.getText());
                                break;
                            default:
                                System.out.println("Invalid option – message disregarded.");
                        }
                        
                        System.out.println("\nMessage Details");
                        System.out.println("Message ID: " + msg.getId());
                        System.out.println("Message Hash: " + msg.getHash());
                        System.out.println("Recipient: " + msg.getReceiver());
                        System.out.println("Message: " + msg.getText());
                    }
                    
                    System.out.println("\nTotal messages sent: " + totalSent);
                    break;
                    
                case 2:
                    System.out.println("Coming Soon");
                    break;
                    
                case 3:
                    quit = true;
                    System.out.println("Goodbye!");
                    break;
                    
                case 4:
                    boolean back = false;
                    while (!back) {
                        System.out.println("\n--- Stored Messages Menu ---");
                        System.out.println("a. Show sender and recipient of all stored messages");
                        System.out.println("b. Show the longest stored message");
                        System.out.println("c. Find message by ID");
                        System.out.println("d. Find all messages for a recipient");
                        System.out.println("e. Delete a message by hash");
                        System.out.println("f. Full report (hash, recipient, message)");
                        System.out.println("g. Back to main menu");
                        System.out.print("Choose: ");
                        String opt = scan.nextLine();
                        
                        switch (opt) {
                            case "a":
                                System.out.println("\nSender and Recipient:");
                                for (int i = 0; i < storedList.size(); i++) {
                                    System.out.println("Sender: " + fname + " " + lname + " | Recipient: " + recipList.get(i));
                                }
                                break;
                            case "b":
                                String longest = "";
                                for (String s : storedList) {
                                    if (s.length() > longest.length()) longest = s;
                                }
                                System.out.println("\nLongest stored message: " + longest);
                                break;
                            case "c":
                                System.out.print("Enter Message ID: ");
                                String searchId = scan.nextLine();
                                boolean found = false;
                                for (int i = 0; i < idList.size(); i++) {
                                    if (idList.get(i).equals(searchId)) {
                                        System.out.println("Recipient: " + recipList.get(i));
                                        System.out.println("Message: " + textList.get(i));
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) System.out.println("ID not found.");
                                break;
                            case "d":
                                System.out.print("Enter recipient number: ");
                                String targetRecip = scan.nextLine();
                                System.out.println("\nMessages to " + targetRecip + ":");
                                for (int i = 0; i < recipList.size(); i++) {
                                    if (recipList.get(i).equals(targetRecip)) {
                                        System.out.println("- " + textList.get(i));
                                    }
                                }
                                break;
                            case "e":
                                System.out.print("Enter Message Hash to delete: ");
                                String delHash = scan.nextLine();
                                boolean removed = false;
                                for (int i = 0; i < hashList.size(); i++) {
                                    if (hashList.get(i).equals(delHash)) {
                                        hashList.remove(i);
                                        idList.remove(i);
                                        recipList.remove(i);
                                        textList.remove(i);
                                        storedList.remove(i);
                                        System.out.println("Message successfully deleted.");
                                        removed = true;
                                        break;
                                    }
                                }
                                if (!removed) System.out.println("Hash not found.");
                                break;
                            case "f":
                                System.out.println("\n--- Full Report ---");
                                System.out.println("Hash\t\tRecipient\t\tMessage");
                                for (int i = 0; i < storedList.size(); i++) {
                                    System.out.println(hashList.get(i) + "\t" + recipList.get(i) + "\t" + storedList.get(i));
                                }
                                break;
                            case "g":
                                back = true;
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                    }
                    break;
                    
                default:
                    System.out.println("Invalid menu choice.");
            }
        }
        scan.close();
    }
}