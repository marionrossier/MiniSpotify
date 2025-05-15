package middle;

import serverSide.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    Optional<User> authenticate(String pseudonym, String hashedPassword);
    List<User> getAllUsers();
    void saveUser(User user);
    User getUserById(int userId);
    User getUserByPseudonym(String pseudonym);
    void addPlaylistToUser(User user, int playlistId);
    void addFriendToUser(User user, int friendId);
    void deleteFriendFromUser(User user, int friendId);
}
