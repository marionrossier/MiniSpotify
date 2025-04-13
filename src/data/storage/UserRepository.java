package data.storage;

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
        this.filePath = "src/data/storage/user.json";
        this.objectMapper = new ObjectMapper();
    }

    public String getFilePath() {
        return filePath;
    }

    public List<User> getAllUsers() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<User>>() {});
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des utilisateurs : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAllUsers(List<User> users) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), users);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des utilisateurs : " + e.getMessage());
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

    public User findUserById(int userId) {
        return getAllUsers().stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .orElse(null);
    }
}