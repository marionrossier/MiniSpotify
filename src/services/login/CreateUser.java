package services.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import datas.entities.User;
import datas.entities.PlanEnum;
import ressources.Routing;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/*
This class add a new user to user.json with as parameters the pseudo, email, password and plan.
The user receive automatically a guid and has no playlist at the beginning.
 */

public class CreateUser {

    private static final String USER_FILE_PATH = Routing.USER_FILE_PATH;

    public static void addUser(String pseudonym, String email, String password, PlanEnum plan) {
        ObjectMapper objectMapper = new ObjectMapper();
        File userFile = new File(USER_FILE_PATH);

        try {
            // Load the existings users from the JSON
            List<User> users = userFile.exists()
                ? objectMapper.readValue(userFile, new TypeReference<List<User>>() {})
                : new LinkedList<>();

            // Check if the pseudonym already exists
            if (isPseudonymExists(pseudonym)) {
                System.err.println("The pseudonym already exists.");
                return;
            }

            // Generate unique salt
            byte[] salt = generateSalt();

            // Hash password with salt
            String hashedPassword = hashPassword(password, salt);

            //User id
            int userId = Math.abs(UUID.randomUUID().hashCode());


            // Create new user
            User newUser = new User();
            newUser.setUserGuId(userId);
            newUser.setPseudonym(pseudonym);
            newUser.setEmail(email);
            newUser.setPassword(hashedPassword);
            newUser.setSalt(salt);
            newUser.setPlanEnum(plan);
            newUser.setPlaylists(null);

            // Add user to the users' list
            users.add(newUser);

            // write the updated list to the JSON file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(userFile, users);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error by adding user.");
        }
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private static String hashPassword(String password, byte[] salt){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(salt); // Add salt to hash
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword); // Encodage in Base64
    }

    public static boolean isPseudonymExists(String pseudonym) {
        ObjectMapper objectMapper = new ObjectMapper();
        File userFile = new File(USER_FILE_PATH);

        try {
            // Load the existings users from JSON
            if (!userFile.exists() || userFile.length() == 0) {
                return false; // json file does not exist or is empty
            }

            List<User> users = objectMapper.readValue(userFile, new TypeReference<List<User>>() {});

            // Verify if the pseudonym already exists
            for (User user : users) {
                if (user.getPseudonym().equals(pseudonym)) {
                    return true; // the pseudonym already exists
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // the pseudonym does not exist
    }
}