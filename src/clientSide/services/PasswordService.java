package clientSide.services;

import serverSide.entities.User;
import serverSide.repositories.UserRepository;

import java.util.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordService {

    private final UserRepository userRepository;

    public PasswordService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
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

    public boolean verifyUserAuthentification(String pseudonym, String password) {

        User searchedUser = userRepository.getUserByPseudonym(pseudonym);

        if (searchedUser == null) {
            System.err.println("The user does not exist.");
            return false;
        }

        String givenHashedPassword = hashPassword(password, searchedUser.getSalt());

        if (givenHashedPassword.equals(searchedUser.getPassword())) {
            return true;
        } else {
            System.err.println("The password is incorrect.");
            return false;
        }
    }

    public boolean passwordCheck(String pseudonym, String password) {
        return verifyUserAuthentification(pseudonym, password);
    }
}