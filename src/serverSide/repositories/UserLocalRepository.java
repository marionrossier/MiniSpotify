package serverSide.repositories;

import serverSide.StockageService;
import serverSide.entities.PlanEnum;
import serverSide.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class UserLocalRepository {
    private final String filePath;
    private final StockageService stockageService;
    private List<User> data;

    public UserLocalRepository(String filePath) {
        this.filePath = filePath;
        this.stockageService = new StockageService();
        this.data = stockageService.loadFromJson(this.filePath, new TypeReference<>() {});
    }

    public UserLocalRepository() {
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
        stockageService.saveToJson(filePath, data);
    }

    public void removeUserById(int userId) {
        data.removeIf(user -> user.getUserId() == userId);
        stockageService.saveToJson(filePath, data);
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
            stockageService.saveToJson(filePath, data);
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
                stockageService.saveToJson(filePath, data);
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
                stockageService.saveToJson(filePath, data);
            }
        }
    }

    public void deleteFriendFromUser(int userId, int friendId) {
        User user = getUserById(userId);
        if (user != null) {
            List<Integer> friends = user.getFriends();
            if (friends != null && friends.contains(friendId)) {
                friends.remove(Integer.valueOf(friendId));
                stockageService.saveToJson(filePath, data);
            }
        }
    }
}
