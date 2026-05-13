package chatapp;

import java.util.Scanner;
import java.util.ArrayList;

public class ChatApp {
    
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        LoginFeature loginHelper = new LoginFeature();
        
        // ----- PART 1: REGISTRATION -----
        System.out.println("========================================");
        System.out.println("            WELCOME TO CHATAPP");
        System.out.println("========================================\n");
        
        System.out.print("Enter first name: ");
        String firstName = keyboard.nextLine();
        System.out.print("Enter last name: ");
        String lastName = keyboard.nextLine();
        System.out.println();
        
        String username, password, phone;
        do {
            System.out.print("Enter username (must contain _ and be 5 or fewer characters): ");
            username = keyboard.nextLine();
            if (!loginHelper.checkUserName(username))
                System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        } while (!loginHelper.checkUserName(username));
        System.out.println("Username successfully captured.\n");
        
        do {
            System.out.print("Enter password (8+ chars, 1 capital, 1 number, 1 special): ");
            password = keyboard.nextLine();
            if (!loginHelper.checkPasswordComplexity(password))
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        } while (!loginHelper.checkPasswordComplexity(password));
        System.out.println("Password successfully captured.\n");
        
        do {
            System.out.print("Enter cell phone number (+27 then 9 digits): ");
            phone = keyboard.nextLine();
            if (!loginHelper.checkCellPhoneNumber(phone))
                System.out.println("Cell phone number incorrectly formatted or does not contain international code; please correct the number and try again.");
        } while (!loginHelper.checkCellPhoneNumber(phone));
        System.out.println("Cell phone number successfully added.\n");
        
        String regMsg = loginHelper.registerUser(username, password, firstName, lastName, phone);
        System.out.println(regMsg);
        
        // ----- PART 1: LOGIN -----
        System.out.println("\n========================================");
        System.out.println("              LOGIN SECTION");
        System.out.println("========================================\n");
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("Enter username: ");
            String loginUser = keyboard.nextLine();
            System.out.print("Enter password: ");
            String loginPass = keyboard.nextLine();
            loggedIn = loginHelper.loginUser(loginUser, loginPass);
            if (loggedIn)
                System.out.println("\n" + loginHelper.returnLoginStatus(true, firstName, lastName));
            else
                System.out.println("Username or password incorrect, please try again.\n");
        }
        
        // ----- PART 2: WELCOME TO CHATAPP & MENU -----
        System.out.println("\n========================================");
        System.out.println("          Welcome to ChatApp");
        System.out.println("========================================");
        
        ArrayList<Message> allMessages = new ArrayList<>();
        int totalSent = 0;
        boolean quit = false;
        
        while (!quit) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages (Coming Soon)");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");
            int choice = keyboard.nextInt();
            keyboard.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    // ----- SEND MESSAGES LOOP -----
                    System.out.print("How many messages do you want to send? ");
                    int numMessages = keyboard.nextInt();
                    keyboard.nextLine();
                    
                    for (int i = 1; i <= numMessages; i++) {
                        System.out.println("\n--- Message " + i + " ---");
                        
                        // recipient validation (reuse Part 1 method)
                        String recipient;
                        do {
                            System.out.print("Recipient cell number (+27 then 9 digits): ");
                            recipient = keyboard.nextLine();
                            if (!loginHelper.checkCellPhoneNumber(recipient))
                                System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                        } while (!loginHelper.checkCellPhoneNumber(recipient));
                        
                        // message text with length check
                        String messageText;
                        while (true) {
                            System.out.print("Message (max 250 characters): ");
                            messageText = keyboard.nextLine();
                            if (messageText.length() <= 250) {
                                System.out.println("Message ready to send.");
                                break;
                            } else {
                                int excess = messageText.length() - 250;
                                System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size.");
                            }
                        }
                        
                        // create Message object (auto-generates ID, number, hash)
                        Message msg = new Message(i, recipient, messageText);
                        
                        // ask what to do with the message
                        System.out.println("\nWhat would you like to do?");
                        System.out.println("1. Send Message");
                        System.out.println("2. Disregard Message");
                        System.out.println("3. Store Message to send later");
                        System.out.print("Your choice: ");
                        int action = keyboard.nextInt();
                        keyboard.nextLine();
                        
                        switch (action) {
                            case 1:
                                System.out.println("Message successfully sent");
                                totalSent++;
                                allMessages.add(msg);
                                break;
                            case 2:
                                System.out.println("Press 0 to delete the message");
                                // not stored, not added to list
                                break;
                            case 3:
                                System.out.println("Message successfully stored");
                                msg.storeToJson();   // saves to JSON file
                                allMessages.add(msg);
                                break;
                            default:
                                System.out.println("Invalid option, message disregarded.");
                        }
                        
                        // display message details in required order
                        System.out.println("\n--- Message Details ---");
                        System.out.println("Message ID: " + msg.getMessageId());
                        System.out.println("Message Hash: " + msg.getMessageHash());
                        System.out.println("Recipient: " + msg.getRecipient());
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
                    
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
        keyboard.close();
    }
}