package services.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entities.User;
import ressources.Routing;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SearchUser {
    private static final String USER_FILE_PATH = Routing.USER_FILE_PATH;

    /*
        * This method is used to search a user by its pseudo.
        * It returns the user GuId if the pseudo exists, or -1 if not found.
     */
    public static int searchUserGuIDByPseudo(String pseudo) {
        ObjectMapper objectMapper = new ObjectMapper();
        File userFile = new File(USER_FILE_PATH);

        try {
            // Load the users from the JSON
            if (!userFile.exists() || userFile.length() == 0) {
                System.err.println("Le fichier user.json est vide ou inexistant.");
                return -1; // Return -1 if the file is empty or inconsistent
            }

            List<User> users = objectMapper.readValue(userFile, new TypeReference<List<User>>() {});

            // Look up for the user with the pseudoo
            for (User user : users) {
                if (user.getPseudonym().equals(pseudo)) {
                    return user.getUserId(); // Return the GuId if found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1; // Return -1 if the pseudo was not found
    }

   public static User searchUserByPseudo(String pseudonym) {
        ObjectMapper objectMapper = new ObjectMapper();
        File userFile = new File(USER_FILE_PATH);

        try {
            // Load users from the JSON file
            if (!userFile.exists() || userFile.length() == 0) {
                System.err.println("The user.json file is empty or does not exist.");
                return null; // Return null if the file is empty or does not exist
            }

            List<User> users = objectMapper.readValue(userFile, new TypeReference<List<User>>() {});

            // Iterate through users to find the pseudonym
            for (User user : users) {
                if (user.getPseudonym().equals(pseudonym)) {
                    return user; // Return the user if found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Return null if the pseudonym is not found
    }
}