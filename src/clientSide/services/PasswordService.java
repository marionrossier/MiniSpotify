package clientSide.services;

import serverSide.entities.User;
import middle.IUserRepository;

import java.util.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static clientSide.services.PrintHelper.*;

public class PasswordService {

    private final IUserRepository userLocalRepository;

    public PasswordService(IUserRepository userLocalRepository) {
        this.userLocalRepository = userLocalRepository;
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

        User searchedUser = userLocalRepository.getUserByPseudonymLogin(pseudonym);

        if (searchedUser == null) {
            printInfo("This user does not exist.");
            return false;
        }

        String givenHashedPassword = hashPassword(password, searchedUser.getSalt());

        if (givenHashedPassword.equals(searchedUser.getPassword())) {
            return true;
        } else {
            printInfo("The password is incorrect.");
            return false;
        }
    }

    public boolean passwordCheck(String pseudonym, String password) {
        return verifyUserAuthentification(pseudonym, password);
    }
}