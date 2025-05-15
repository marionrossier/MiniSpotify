package middle;

import serverSide.entities.User;

import java.util.List;

public interface IUserRepository {

    List<User> getAllUsers();
    void saveUser(User user);
    User getUserById(int userId);
    User getUserByPseudonym(String pseudonym);
    void addPlaylistToUser(User user, int playlistId);
    void addFriendToUser(User user, int friendId);
    void deleteFriendFromUser(User user, int friendId);
}
