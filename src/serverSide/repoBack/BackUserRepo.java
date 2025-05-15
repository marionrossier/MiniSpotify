package serverSide.repoBack;

import com.fasterxml.jackson.databind.ObjectMapper;
import middle.IUserRepository;
import serverSide.entities.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BackUserRepo {
    private final ObjectMapper mapper = new ObjectMapper();
    private final IUserRepository userRepo;

    public BackUserRepo(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public String handleRequest(Map<String, Object> request) {
        try {
            String command = (String) request.get("command");
            String username = (String) request.get("username");
            String password = (String) request.get("password");

            Optional<User> optUser = userRepo.authenticate(username, password);
            if (optUser.isEmpty()) {
                return "{\"status\": \"ERROR\", \"message\": \"Authentication failed\"}";
            }

            switch (command) {
                case "getAllUsers" -> {
                    List<User> all = userRepo.getAllUsers();
                    return mapper.writeValueAsString(Map.of("status", "OK", "users", all));
                }
                case "getUserById" -> {
                    int id = (int) request.get("userId");
                    User user = userRepo.getUserById(id);
                    return user != null
                            ? mapper.writeValueAsString(Map.of("status", "OK", "user", user))
                            : "{\"status\": \"ERROR\", \"message\": \"User not found\"}";
                }
                case "getUserByPseudonym" -> {
                    String pseudo = (String) request.get("pseudonym");
                    User user = userRepo.getUserByPseudonym(pseudo);
                    return user != null
                            ? mapper.writeValueAsString(Map.of("status", "OK", "user", user))
                            : "{\"status\": \"ERROR\", \"message\": \"User not found\"}";
                }
                case "saveUser" -> {
                    Map<String, Object> userMap = (Map<String, Object>) request.get("user");
                    User user = mapper.convertValue(userMap, User.class);
                    userRepo.saveUser(user);
                    return "{\"status\": \"OK\"}";
                }
                case "addPlaylistToUser" -> {
                    User user = mapper.convertValue(request.get("user"), User.class);
                    int playlistId = (int) request.get("playlistId");
                    userRepo.addPlaylistToUser(user, playlistId);
                    return "{\"status\": \"OK\"}";
                }
                case "addFriendToUser" -> {
                    User user = mapper.convertValue(request.get("user"), User.class);
                    int friendId = (int) request.get("friendId");
                    userRepo.addFriendToUser(user, friendId);
                    return "{\"status\": \"OK\"}";
                }
                case "deleteFriendFromUser" -> {
                    User user = mapper.convertValue(request.get("user"), User.class);
                    int friendId = (int) request.get("friendId");
                    userRepo.deleteFriendFromUser(user, friendId);
                    return "{\"status\": \"OK\"}";
                }
                default -> {
                    return "{\"status\": \"ERROR\", \"message\": \"Unknown command (user)\"}";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\": \"ERROR\", \"message\": \"Internal server error\"}";
        }
    }
}
