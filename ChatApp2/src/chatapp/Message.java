package chatapp;

import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Message {
    
    private String messageId;
    private int messageNumber;
    private String recipient;
    private String text;
    private String messageHash;
    
    // constructor creates everything when a message is made
    public Message(int msgNumber, String recip, String msgText) {
        this.messageNumber = msgNumber;
        this.recipient = recip;
        this.text = msgText;
        this.messageId = generateMessageId();
        this.messageHash = createMessageHash();
    }
    
    // generate a random 10-digit ID
    private String generateMessageId() {
        Random rand = new Random();
        long id = 1000000000L + (long)(rand.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }
    
    // create hash in format: first two digits of ID : message number : FIRSTWORD + LASTWORD (uppercase)
    public String createMessageHash() {
        String idPrefix = messageId.substring(0, 2);
        String[] words = text.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        return idPrefix + ":" + messageNumber + ":" + firstWord + lastWord;
    }
    
    // check that message ID is not longer than 10 digits (always true with our generation)
    public boolean checkMessageId() {
        return messageId != null && messageId.length() <= 10;
    }
    
    // recipient validation (reuse same logic from Part 1) but here just a check
    public String checkRecipientCell() {
        // simple check, but we already validated in main; this is for test
        if (recipient == null || !recipient.matches("^\\+27[0-9]{9}$"))
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        return "Cell phone number successfully captured.";
    }
    
    // store message to JSON file (simple manual JSON)
    public void storeToJson() {
        try (FileWriter file = new FileWriter("stored_messages.json", true)) {
            file.write("{\"id\":\"" + messageId + "\", \"hash\":\"" + messageHash + "\", \"recipient\":\"" + recipient + "\", \"text\":\"" + text + "\"}\n");
        } catch (IOException e) {
            System.out.println("Error saving message to JSON.");
        }
    }
    
    // getters for display and testing
    public String getMessageId() { return messageId; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getText() { return text; }
    public String getMessageHash() { return messageHash; }
}