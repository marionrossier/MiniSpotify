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

    @Override
    public Optional<User> authenticate(String pseudonym, String hashedPassword) {
        try {
            // 🔥 Utilise la vraie commande login
            Map<String, Object> response = socketClient.sendRequest(Map.of(
                    "command", "login",
                    "userPseudonym", pseudonym,
                    "password", hashedPassword
            ));

            if (!"OK".equals(response.get("status"))) {
                return Optional.empty();
            }

            // Ensuite, une fois connecté, récupère le user
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

            // 🆕 Ajouter identifiants utilisateur pour prouver qu’on est authentifié
            if (Cookies.getInstance().getUserPseudonym() != null && Cookies.getInstance().getUserPassword() != null) {
                request.put("userPseudonym", Cookies.getInstance().getUserPseudonym());
                request.put("password", Cookies.getInstance().getUserPassword());
            }

            Map<String, Object> response = socketClient.sendRequest(request);

            if (!"OK".equals(response.get("status"))) {
                throw new RuntimeException((String) response.get("message"));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            Map<String, Object> response = socketClient.sendRequest(Map.of(
                    "command", "getAllUsers",
                    "userPseudonym", Cookies.getInstance().getUserPseudonym(),
                    "password", Cookies.getInstance().getUserPassword()
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
    public User getUserById(int userId) {
        return getUserFromServer(Map.of(
                "command", "getUserById",
                "userPseudonym", Cookies.getInstance().getUserPseudonym(),
                "password", Cookies.getInstance().getUserPassword(),
                "userId", userId
        ));
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
