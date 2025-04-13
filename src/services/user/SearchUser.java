package services.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entities.User;
import data.storage.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SearchUser {
    private final UserRepository userRepository = new UserRepository();
    private final String USER_FILE_PATH = userRepository.getFilePath();

    /*
        * This method is used to search a user by its pseudo.
        * It returns the user GuId if the pseudo exists, or -1 if not found.
     */
    public int searchUserGuIDByPseudo(String pseudo) {
        ObjectMapper objectMapper = new ObjectMapper();
        File userFile = new File(USER_FILE_PATH);

        try {
            // Load the users from the JSON
            if (!userFile.exists() || userFile.length() == 0) {
                System.err.println("The file user.json is empty or does not exist.");
                return -1; // Return -1 if the file is empty or inconsistent
            }

            List<User> users = objectMapper.readValue(userFile, new TypeReference<List<User>>() {});

            // Look up for the user with the pseudo
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

   public User searchUserByPseudo(String pseudonym) {
        ObjectMapper objectMapper = new ObjectMapper();
        File userFile = new File(USER_FILE_PATH);

        try {
            // Load users from the JSON file
            if (!userFile.exists() || userFile.length() == 0) {
                System.err.println("The user file does not exist or is empty.");
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