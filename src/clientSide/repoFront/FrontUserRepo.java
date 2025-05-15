package clientSide.repoFront;

import clientSide.socket.SocketClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import middle.IUserRepository;
import serverSide.entities.User;

import java.util.*;

public class FrontUserRepo implements IUserRepository {
    private final ObjectMapper mapper = new ObjectMapper();

    private final String username = "marion";
    private final String password = "ipmUvIFpi5NU/dhSPJuy49ikJM9yHSWfzKict97V/gU=";
    private final SocketClient socketClient;

    public FrontUserRepo(SocketClient socketClient) {
        this.socketClient = new SocketClient();
    }

    @Override
    public Optional<User> authenticate(String pseudonym, String hashedPassword) {
        try {
            Map<String, Object> response = socketClient.sendRequest(Map.of(
                    "command", "getUserByPseudonym",
                    "username", username,
                    "password", password,
                    "pseudonym", pseudonym
            ));
            if (!"OK".equals(response.get("status"))) return Optional.empty();
            Object userObj = response.get("user");
            String json = mapper.writeValueAsString(userObj);
            User user = mapper.readValue(json, User.class);
            if (user != null && user.getPassword().equals(hashedPassword)) {
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            Map<String, Object> response = socketClient.sendRequest(Map.of(
                    "command", "getAllUsers",
                    "username", username,
                    "password", password
            ));

            if (!"OK".equals(response.get("status"))) return null;

            List<?> raw = (List<?>) response.get("users");
            String json = mapper.writeValueAsString(raw);
            User[] users = mapper.readValue(json, User[].class);
            return Arrays.asList(users);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(User user) {
        try {
            Map<String, Object> request = Map.of(
                    "command", "saveUser",
                    "username", username,
                    "password", password,
                    "user", mapper.convertValue(user, Map.class)
            );
            socketClient.sendRequest(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserById(int userId) {
        return getUserFromServer(Map.of(
                "command", "getUserById",
                "username", username,
                "password", password,
                "userId", userId
        ));
    }

    @Override
    public User getUserByPseudonym(String pseudonym) {
        return getUserFromServer(Map.of(
                "command", "getUserByPseudonym",
                "username", username,
                "password", password,
                "pseudonym", pseudonym
        ));
    }

    @Override
    public void addPlaylistToUser(User user, int playlistId) {
        try {
            Map<String, Object> request = Map.of(
                    "command", "addPlaylistToUser",
                    "username", username,
                    "password", password,
                    "user", mapper.convertValue(user, Map.class),
                    "playlistId", playlistId
            );
            socketClient.sendRequest(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addFriendToUser(User user, int friendId) {
        try {
            Map<String, Object> request = Map.of(
                    "command", "addFriendToUser",
                    "username", username,
                    "password", password,
                    "user", mapper.convertValue(user, Map.class),
                    "friendId", friendId
            );
            socketClient.sendRequest(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFriendFromUser(User user, int friendId) {
        try {
            Map<String, Object> request = Map.of(
                    "command", "deleteFriendFromUser",
                    "username", username,
                    "password", password,
                    "user", mapper.convertValue(user, Map.class),
                    "friendId", friendId
            );
            socketClient.sendRequest(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private User getUserFromServer(Map<String, Object> request) {
        try {
            Map<String, Object> response = socketClient.sendRequest(request);
            if (!"OK".equals(response.get("status"))) return null;
            Object userObj = response.get("user");
            String json = mapper.writeValueAsString(userObj);
            return mapper.readValue(json, User.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
