package clientSide.repoFront;

import com.fasterxml.jackson.databind.ObjectMapper;

import clientSide.services.*;
import clientSide.socket.*;
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
            Map<String, Object> response = socketClient.sendRequest(Map.of(
                    "command", "getUserByPseudonym",
                    "userPseudonym", pseudonym,
                    "password", hashedPassword,
                    "pseudonym", pseudonym
            ));
            if (!response.get("status").equals("OK")) return Optional.empty();
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
    public User getUserByPseudonym(String pseudonym) {
        return getUserFromServer(Map.of(
                "command", "getUserByPseudonym",
                "pseudonym", pseudonym
        ));
    }

    @Override
    public void updateOrInsertUser(User user) {
        try {
            Map<String, Object> request = Map.of(
                    "command", "updateOrInsertUser",
                    "user", mapper.convertValue(user, Map.class)
            );
            socketClient.sendRequest(request);
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
            if (!response.get("status").equals("OK")){
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
