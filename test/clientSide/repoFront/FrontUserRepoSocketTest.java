package clientSide.repoFront;

import middle.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serverSide.entities.User;
import utilsAndFakes.CommuneMethods;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FrontUserRepoSocketTest extends CommuneMethods {

    public IUserRepository userRepo;
    public CommuneMethods communeMethods;

    public FrontUserRepoSocketTest() throws IOException {
    }

    @BeforeEach
    void setup() throws IOException {
        initializer.populateUsers();
        communeMethods = new CommuneMethods() {};
        userRepo = communeMethods.startServerAndInitRepo(() -> initializer.frontUserRepo);
    }

    @Test
    void getUserById_shouldReturnUser() {
        User user = userRepo.getUserById(232928320); // Marion
        assertNotNull(user);
        assertEquals("marion", user.getPseudonym());
    }

    @Test
    void getUserByPseudonym_shouldReturnUser() {
        User user = userRepo.getUserByPseudonym("florent");
        assertNotNull(user);
        assertEquals(1726370281, user.getUserId());
    }

    @Test
    void getAllUsers_shouldReturnList() {
        List<User> users = userRepo.getAllUsers();
        assertNotNull(users);
        assertTrue(users.size() >= 2);
    }
}

//    @Test
//    void addAndDeleteFriend_shouldModifyFriendList() {
//        User user = userRepo.getUserById(232928320); // Marion
//        int newFriendId = 1; // Admin
//
//        userRepo.addFriendToUser(user, newFriendId);
//        User updated = userRepo.getUserById(232928320);
//        assertTrue(updated.getFriends().contains(newFriendId));
//
//        userRepo.deleteFriendFromUser(user, newFriendId);
//        updated = userRepo.getUserById(232928320);
//        assertFalse(updated.getFriends().contains(newFriendId));
//    }
//}
