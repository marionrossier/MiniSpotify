package clientSide.repositories;

import clientSide.entities.PlanEnum;
import clientSide.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final String filePath;
    private final Jsons jsons;
    private List<User> data;

    public UserRepository(String filePath) {
        this.filePath = filePath;
        this.jsons = new Jsons();
        this.data = jsons.loadFromJson(this.filePath, new TypeReference<>() {});
    }

    public UserRepository() {
        this(System.getProperty("user.home") + "/MiniSpotifyFlorentMarion/jsons/user.json");
    }

    public String getFilePath() {
        return filePath;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(data); // Copie défensive
    }

    public void saveUser(User user) {
        data.removeIf(u -> u.getUserId() == user.getUserId());
        data.add(user);
        jsons.saveToJson(filePath, data);
    }

    public void removeUserById(int userId) {
        data.removeIf(user -> user.getUserId() == userId);
        jsons.saveToJson(filePath, data);
    }

    public User getUserById(int userId) {
        return data.stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .orElse(null);
    }

    public User getUserByPseudonym(String pseudonym) {
        return data.stream()
                .filter(user -> user.getPseudonym().equals(pseudonym))
                .findFirst()
                .orElse(null);
    }

    public void updatePlanEnum(int userId, PlanEnum newPlan) {
        User user = getUserById(userId);
        if (user != null) {
            user.setPlanEnum(newPlan);
            jsons.saveToJson(filePath, data);
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
                jsons.saveToJson(filePath, data);
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
                jsons.saveToJson(filePath, data);
            }
        }
    }

    public void deleteFriendFromUser(int userId, int friendId) {
        User user = getUserById(userId);
        if (user != null) {
            List<Integer> friends = user.getFriends();
            if (friends != null && friends.contains(friendId)) {
                friends.remove(Integer.valueOf(friendId));
                jsons.saveToJson(filePath, data);
            }
        }
    }
}
