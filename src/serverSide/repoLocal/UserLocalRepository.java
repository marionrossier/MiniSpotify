package serverSide.repoLocal;

import middle.IUserRepository;
import serverSide.StockageService;
import serverSide.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserLocalRepository implements IUserRepository {
    private final String filePath;
    private final StockageService stockageService;
    private final List<User> data;

    public UserLocalRepository(String filePath) {
        this.filePath = filePath;
        this.stockageService = new StockageService();
        this.data = stockageService.loadFromJson(this.filePath, new TypeReference<>() {});
    }

    public UserLocalRepository() {
        this(System.getProperty("user.home") + "/MiniSpotifyFlorentMarion/jsons/user.json");
    }

    public Optional<User> authenticate(String pseudonym, String hashedPassword) {
        return data.stream()
                .filter(user -> user.getPseudonym().equals(pseudonym) && user.getPassword().equals(hashedPassword))
                .findFirst();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(data); // Copie défensive
    }

    public void saveUser(User user) {
        data.removeIf(u -> u.getUserId() == user.getUserId());
        data.add(user);
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

    public void addPlaylistToUser(User user, int playlistId) {
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

    public void addFriendToUser(User user, int friendId) {
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

    public void deleteFriendFromUser(User user, int friendId) {
        if (user != null) {
            List<Integer> friends = user.getFriends();
            if (friends != null && friends.contains(friendId)) {
                friends.remove(Integer.valueOf(friendId));
                stockageService.saveToJson(filePath, data);
            }
        }
    }
}
