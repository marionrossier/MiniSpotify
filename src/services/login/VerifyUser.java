package services.login;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entities.User;
import ressources.Routing;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

/*
This class verify if the user exist in the user.json file with as parameters the pseudo and password
according to the hash and salt.
 */
public class VerifyUser {

    private static final String USER_FILE_PATH = Routing.USER_FILE_PATH;

    //this method verify if the user exist in the user.json file with as parameters the pseudo and password
    public static boolean verifyUser(String pseudonym, String password) {
        ObjectMapper objectMapper = new ObjectMapper();
        File userFile = new File(USER_FILE_PATH);

        try {
            // Load users from JSON file
            if (!userFile.exists() || userFile.length() == 0) {
                System.err.println("The user file does not exist or is empty.");
                return false;
            }

            List<User> users = objectMapper.readValue(userFile, new TypeReference<List<User>>() {});

            // Look at the users to verify pseudo and password
            for (User user : users) {
                if (user.getPseudonym().equals(pseudonym)) {
                    // Verify hashed password with salt
                    String hashedPassword = hashPassword(password, user.getSalt());
                    return hashedPassword.equals(user.getPassword());
                }
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return false; // Return error message if user not found
    }

    // Method to generate a unique salt
    private static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt); // Add sel to hash
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword); // Encodage in Base64
    }
}
