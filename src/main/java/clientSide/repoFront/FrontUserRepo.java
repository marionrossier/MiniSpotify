package clientSide.repoFront;

import com.fasterxml.jackson.databind.ObjectMapper;
import clientSide.services.Cookies;
import clientSide.socket.SocketClient;
import common.entities.User;
import common.repository.IUserRepository;

import java.util.*;

public class FrontUserRepo implements IUserRepository {
    private final ObjectMapper mapper = new ObjectMapper();
    private final SocketClient socketClient;

    public FrontUserRepo(SocketClient socketClient) {
        this.socketClient = socketClient;
    }

    // Helper pour ajouter l'auth seulement si connecté
    private Map<String, Object> withAuth(Map<String, Object> request) {
        if (Cookies.getInstance().getUserPseudonym() != null && Cookies.getInstance().getUserPassword() != null) {
            request.put("userPseudonym", Cookies.getInstance().getUserPseudonym());
            request.put("password", Cookies.getInstance().getUserPassword());
        }
        return request;
    }

    @Override
    public Optional<User> authenticate(String pseudonym, String hashedPassword) {
        try {
            Map<String, Object> response = socketClient.sendRequest(Map.of(
                    "command", "login",
                    "userPseudonym", pseudonym,
                    "password", hashedPassword
            ));

            if (!"OK".equals(response.get("status"))) {
                return Optional.empty();
            }

            return Optional.ofNullable(getUserByPseudonym(pseudonym));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByPseudonym(String pseudonym) {
        return getUserFromServer(Map.of(
                "command", "getUserByPseudonym",
                "pseudonym", pseudonym
        ));
    }

    @Override
    public void updateOrInsertUser(User user) {
        try {
            Map<String, Object> userMap = mapper.convertValue(user, Map.class);

            Map<String, Object> request = new HashMap<>();
            request.put("command", "updateOrInsertUser");
            request.put("user", userMap);

            socketClient.sendRequest(request); // Pas d'auth pour créer un compte

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("command", "getAllUsers");

            Map<String, Object> response = socketClient.sendRequest(withAuth(request));

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
    public User getUserById(int userId) {
        Map<String, Object> request = new HashMap<>();
        request.put("command", "getUserById");
        request.put("userId", userId);

        return getUserFromServer(withAuth(request));
    }

    private User getUserFromServer(Map<String, Object> request) {
        try {
            Map<String, Object> response = socketClient.sendRequest(request);
            if (!"OK".equals(response.get("status"))) {
                System.out.println(response);
                return null;
            }
            Object userObj = response.get("user");
            String json = mapper.writeValueAsString(userObj);
            return mapper.readValue(json, User.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
