package chatapp;

import java.util.regex.Pattern;

public class LoginFeature {
    
    private String savedUsername;
    private String savedPassword;
    private String savedFirstName;
    private String savedLastName;
    private String savedPhone;
    
    public boolean checkUserName(String username) {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }
    
    public boolean checkPasswordComplexity(String password) {
        if (password == null) return false;
        boolean lengthOk = password.length() >= 8;
        boolean hasCapital = password.matches(".*[A-Z].*");
        boolean hasNumber = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?~`].*");
        return lengthOk && hasCapital && hasNumber && hasSpecial;
    }
    
    // reference: regex pattern from https://regex101.com/
    public boolean checkCellPhoneNumber(String phone) {
        if (phone == null) return false;
        return Pattern.matches("^\\+27[0-9]{9}$", phone);
    }
    
    public String registerUser(String username, String password, String firstName, String lastName, String phone) {
        StringBuilder messages = new StringBuilder();
        boolean allValid = true;
        
        if (!checkUserName(username)) {
            messages.append("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.\n");
            allValid = false;
        } else {
            messages.append("Username successfully captured.\n");
        }
        
        if (!checkPasswordComplexity(password)) {
            messages.append("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n");
            allValid = false;
        } else {
            messages.append("Password successfully captured.\n");
        }
        
        if (!checkCellPhoneNumber(phone)) {
            messages.append("Cell phone number incorrectly formatted or does not contain international code; please correct the number and try again.\n");
            allValid = false;
        } else {
            messages.append("Cell phone number successfully added.\n");
        }
        
        if (allValid) {
            savedUsername = username;
            savedPassword = password;
            savedFirstName = firstName;
            savedLastName = lastName;
            savedPhone = phone;
            return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.\nUser registered successfully.";
        } else {
            return messages.toString().trim();
        }
    }
    
    public boolean loginUser(String username, String password) {
        if (username == null || password == null) return false;
        return username.equals(savedUsername) && password.equals(savedPassword);
    }
    
    public String returnLoginStatus(boolean success, String firstName, String lastName) {
        if (success)
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        else
            return "Username or password incorrect, please try again.";
    }
    
    // getters for testing
    public String getSavedUsername() { return savedUsername; }
    public String getSavedPassword() { return savedPassword; }
    public String getSavedFirstName() { return savedFirstName; }
    public String getSavedLastName() { return savedLastName; }
    public String getSavedPhone() { return savedPhone; }
}