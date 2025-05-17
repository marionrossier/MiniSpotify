package clientSide.repoFront;

import commun.IUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serverSide.entities.User;
import utilsAndFakes.CommuneMethods;
import utilsAndFakes.Initializer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FrontUserRepoSocketTest {

    public IUserRepository userRepo;
    public CommuneMethods communeMethods;
    public Initializer initializer;

    @BeforeEach
    void setup() {
        communeMethods = new CommuneMethods();
        initializer = communeMethods.initializer;
        initializer.populateLocalUsers();
        userRepo = initializer.frontUserRepo;

        communeMethods.startServer();
    }

    @AfterEach
    void tearDown() {
        initializer.cleanUp();
    }

    @Test
    void getUserById_shouldReturnUser() {
        // Arrange

        // Act
        User user = userRepo.getUserById(232928320); // Marion

        //Assert
        assertNotNull(user);
        assertEquals("marion", user.getPseudonym());
    }

    @Test
    void getUserByPseudonym_shouldReturnUser() {
        // Arrange

        // Act
        User user = userRepo.getUserByPseudonym("florent");

        //Assert
        assertNotNull(user);
        assertEquals(1726370281, user.getUserId());
    }

    @Test
    void getAllUsers_shouldReturnList() {
        // Arrange

        // Act
        List<User> users = userRepo.getAllUsers();

        //Assert
        assertNotNull(users);
        assertTrue(users.size() >= 2);
    }

//    @Test
//    void addAndDeleteFriend_shouldModifyFriendList() {
//        // Arrange
//        User user = userRepo.getUserById(232928320); // Marion
//        int newFriendId = 1; // Admin
//
//        // Act1
//        userRepo.addFriendToUser(user, newFriendId);
//        User updated = userRepo.getUserById(232928320);
//
//        //Assert1
//        assertTrue(updated.getFriends().contains(newFriendId));
//
//        // Act2
//        userRepo.deleteFriendFromUser(user, newFriendId);
//        updated = userRepo.getUserById(232928320);
//
//        //Assert2
//        assertFalse(updated.getFriends().contains(newFriendId));
//    }
}
