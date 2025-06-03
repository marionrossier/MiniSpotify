package common.repository;

import common.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    Optional<User> authenticate(String pseudonym, String hashedPassword);
    User getUserByPseudonym(String pseudonym);
    List<User> getAllUsers();
    void updateOrInsertUser(User user);
    User getUserById(int userId);
}
