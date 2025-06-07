package serverSide.repoBack;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.repository.*;
import common.entities.User;
import serverSide.services.PasswordVerifier;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BackUserRepo {
    private final ObjectMapper mapper = new ObjectMapper();
    private final IUserRepository userRepo;
    private final PasswordVerifier passwordVerifier;

    public BackUserRepo(IUserRepository userRepo, PasswordVerifier passwordVerifier) {
        this.userRepo = userRepo;
        this.passwordVerifier = passwordVerifier;
    }

    public String handleRequest(Map<String, Object> request) {
        try {
            String command = (String) request.get("command");

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
                    User user = null;
                    try {
                        user = userRepo.getUserByPseudonym(pseudo);
                    } catch (Exception ignored) {}

                    Map<String, Object> response = new java.util.HashMap<>();
                    response.put("status", "OK");
                    response.put("user", user); // peut être null sans problème ici
                    return mapper.writeValueAsString(response);
                }
                case "updateOrInsertUser" -> {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> userMap = (Map<String, Object>) request.get("user");
                    User user = mapper.convertValue(userMap, User.class);

                    // 🔥 VERIFIER SI LE USER EXISTE
                    User existingUser = userRepo.getUserByPseudonym(user.getPseudonym());
                    if (existingUser != null) {
                        // USER EXISTE => FAIRE UN UPDATE
                        user.setUserId(existingUser.getUserId()); // Garde l'ID existant
                        userRepo.updateOrInsertUser(user); // Update
                    } else {
                        // USER N'EXISTE PAS => INSERT
                        userRepo.updateOrInsertUser(user);
                    }
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

    public boolean authenticate(String pseudonym, String password) {
        User user = userRepo.getUserByPseudonym(pseudonym);  // <-- simple récupération
        if (user == null) {
            return false;
        }
        return passwordVerifier.verifyPassword(password, user);  // <-- vrai contrôle avec salt + hash
    }

    public User getUserByPseudonym(String pseudonym) {
        return userRepo.getUserByPseudonym(pseudonym);
    }
}
