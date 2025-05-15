package clientSide.repoFront;

import serverSide.entities.User;
import middle.IUserRepository;

import java.util.List;

public class FrontUserRepo implements IUserRepository {
    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public User getUserByPseudonym(String pseudonym) {
        return null;
    }

    @Override
    public void addPlaylistToUser(User user, int playlistId) {

    }

    @Override
    public void addFriendToUser(User user, int friendId) {

    }

    @Override
    public void deleteFriendFromUser(User user, int friendId) {

    }
}
