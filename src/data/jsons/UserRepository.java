package data.jsons;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final String filePath;
    private final ObjectMapper objectMapper;

    public UserRepository(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
    }

    public UserRepository() {
        this.filePath = "src/data/jsons/user.json";
        this.objectMapper = new ObjectMapper();
    }

    public String getFilePath() {
        return filePath;
    }

    public List<User> getAllUsers() {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            System.out.println("The JSON file is empty or does not exist.");
            return new ArrayList<>();
        }
        try {
            List<User> users = objectMapper.readValue(file, new TypeReference<List<User>>() {});
            if (users == null || users.isEmpty()) {
                return new ArrayList<>();
            }
            return users;
        } catch (IOException e) {
            System.err.println("Error during the loading of the users: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAllUsers(List<User> users) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), users);
        } catch (IOException e) {
            System.err.println("Error during the saving of the users : " + e.getMessage());
        }
    }

    public void addUser(User user) {
        List<User> users = getAllUsers();
        users.add(user);
        saveAllUsers(users);
    }

    public void removeUserById(int userId) {
        List<User> users = getAllUsers();
        users.removeIf(user -> user.getUserId() == userId);
        saveAllUsers(users);
    }

    public User getUserById(int userId) {
        return getAllUsers().stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .orElse(null);
    }

    public User getUserByPseudonym(String pseudonym) {
        return getAllUsers().stream()
                .filter(user -> user.getPseudonym().equals(pseudonym))
                .findFirst()
                .orElse(null);
    }

    public User updateAccount(User user) {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == user.getUserId()) {
                users.set(i, user);
                saveAllUsers(users);
                return user;
            }
        }
        System.err.println("User not found.");
        return null;
    }
}