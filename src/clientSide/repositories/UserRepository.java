package clientSide.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import clientSide.entities.PlanEnum;
import clientSide.entities.User;

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
        this.filePath = "src/clientSide/jsons/user.json";
        this.objectMapper = new ObjectMapper();
    }

    public String getFilePath() {
        return filePath;
    }

    public List<User> getAllUsers() {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try {
            List<User> users = objectMapper.readValue(file, new TypeReference<>() {
            });
            if (users == null || users.isEmpty()) {
                return new ArrayList<>();
            }
            return users;
        } catch (IOException e) {
            System.err.println("Error during the loading of the users: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveUser(User user) {
        List<User> users = getAllUsers();
        users.removeIf(u -> u.getUserId() == user.getUserId());
        users.add(user);
        saveAllUsers(users);
    }

    private void saveAllUsers(List<User> users) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), users);
        } catch (IOException e) {
            System.err.println("Error during the saving of the users : " + e.getMessage());
        }
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

    public void updatePlanEnum(int userId, PlanEnum newPlan) {
        User user = getUserById(userId);
        if (user != null) {
            user.setPlanEnum(newPlan);
            saveUser(user);
        }
    }

    public void addPlaylistToUser(int userId, int playlistId) {
            User user = getUserById(userId);
            if (user != null) {
                List<Integer> playlists = user.getPlaylists();
                if (playlists == null) {
                    playlists = new ArrayList<>();
                    user.setPlaylists(playlists);
                }
                if (!playlists.contains(playlistId)) {
                    playlists.add(playlistId);
                    saveUser(user);
                }
            }
    }

    public void addFriendToUser(int userId, int friendId) {
        User user = getUserById(userId);
        if (user != null) {
            List<Integer> friends = user.getFriends();
            if (friends == null) {
                friends = new ArrayList<>();
                user.setFriends(friends);
            }
            if (!friends.contains(friendId)) {
                friends.add(friendId);
                saveUser(user);
            }
        }
    }

    public void deleteFriendFromUser(int userId, int friendId) {
        User user = getUserById(userId);
        if (user != null) {
            List<Integer> friends = user.getFriends();
            if (friends != null && friends.contains(friendId)) {
                friends.remove(Integer.valueOf(friendId));
                saveUser(user);
            }
        }
    }
}