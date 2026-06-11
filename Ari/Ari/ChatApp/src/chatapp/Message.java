package chatapp;

import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Message {
    
    private String msgId;
    private int msgNum;
    private String target;
    private String content;
    private String msgHash;
    
    public Message(int n, String recip, String text) {
        msgNum = n;
        target = recip;
        content = text;
        msgId = generateId();
        msgHash = buildHash();
    }
    
    // for unit tests only (fixed ID)
    public Message(int n, String recip, String text, String fixedId) {
        msgNum = n;
        target = recip;
        content = text;
        msgId = fixedId;
        msgHash = buildHash();
    }
    
    private String generateId() {
        Random r = new Random();
        long val = 1000000000L + (long)(r.nextDouble() * 9000000000L);
        return Long.toString(val);
    }
    
    private String keepLetters(String word) {
        return word.replaceAll("[^A-Za-z]", "");
    }
    
    public String buildHash() {
        String start = msgId.substring(0, 2);
        String[] parts = content.trim().split("\\s+");
        String first = keepLetters(parts[0]).toUpperCase();
        String last = keepLetters(parts[parts.length - 1]).toUpperCase();
        return start + ":" + msgNum + ":" + first + last;
    }
    
    public boolean idValid() {
        return msgId != null && msgId.length() <= 10;
    }
    
    public String validateRecipient() {
        if (target == null || !target.matches("^\\+27[0-9]{9}$"))
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        return "Cell phone number successfully captured.";
    }
    
    public void saveToFile() {
        try (FileWriter fw = new FileWriter("chatdata.json", true)) {
            fw.write("{\"id\":\"" + msgId + "\", \"hash\":\"" + msgHash + "\", \"to\":\"" + target + "\", \"msg\":\"" + content + "\"}\n");
        } catch (IOException e) {
            System.out.println("JSON write error.");
        }
    }
    
    public String getId() { return msgId; }
    public int getNum() { return msgNum; }
    public String getReceiver() { return target; }
    public String getText() { return content; }
    public String getHash() { return msgHash; }
}