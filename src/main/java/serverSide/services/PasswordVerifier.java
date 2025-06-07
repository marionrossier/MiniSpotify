package serverSide.services;

import common.entities.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordVerifier {

    public boolean verifyPassword(String passwordInput, User user) {
        String hashedInput = hashPassword(passwordInput, user.getSalt());
        return hashedInput.equals(user.getPassword());
    }

    public String hashPassword(String password, byte[] salt){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(salt); // Add salt to hash
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
}

