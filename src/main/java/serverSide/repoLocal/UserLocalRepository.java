package serverSide.repoLocal;

import common.entities.User;
import common.repository.IUserRepository;
import serverSide.services.StockageService;
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
        stockageService.copyResourceToWritableLocation("jsons/user.json");

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

    public User getUserByPseudonym(String pseudonym) {
        return data.stream()
                .filter(user -> user.getPseudonym().equals(pseudonym))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(data);
    }

    public void updateOrInsertUser(User user) {
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
}
