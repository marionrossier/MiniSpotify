package data.jsons;

import clientSide.entities.PlanEnum;
import clientSide.entities.User;
import clientSide.repositories.UserRepository;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private File tempFile;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("user", ".json").toFile();
        userRepository = new UserRepository(tempFile.getAbsolutePath());
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void saveUser_shouldSaveTheUser() {
        // Arrange
        User user = new User("TestUser", "test@example.com", "1234", PlanEnum.FREE);

        // Act
        userRepository.saveUser(user);

        // Assert
        List<User> users = userRepository.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("TestUser", users.get(0).getPseudonym());
    }

    @Test
    void removeUserById_shouldDeleteTheUser() {
        // Arrange
        User userOne = new User("UserOne", "one@example.com", "1234", PlanEnum.FREE);
        User userTwo = new User("UserTwo", "two@example.com", "1234", PlanEnum.FREE);
        userRepository.saveUser(userOne);
        userRepository.saveUser(userTwo);

        // Act
        userRepository.removeUserById(userOne.getUserId());

        // Assert
        List<User> result = userRepository.getAllUsers();
        assertEquals(1, result.size());
        assertEquals(userTwo.getUserId(), result.get(0).getUserId());
    }

    @Test
    void getUserById_shouldFindTheUser() {
        // Arrange
        User user = new User("TestUser", "test@example.com", "1234", PlanEnum.FREE);
        userRepository.saveUser(user);

        // Act
        User result = userRepository.getUserById(user.getUserId());

        // Assert
        assertNotNull(result);
        assertEquals("TestUser", result.getPseudonym());
    }

    @Test
    void getUserByPseudonym_shouldFindTheUser() {
        // Arrange
        User user = new User("TestUser", "test@example.com", "1234", PlanEnum.FREE);
        userRepository.saveUser(user);

        // Act
        User result = userRepository.getUserByPseudonym("TestUser");

        // Assert
        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());
    }

    @Test
    void updateAccount_shouldModifyTheUser() {
        // Arrange
        User user = new User("OldUser", "old@example.com", "1234", PlanEnum.FREE);
        userRepository.saveUser(user);
        int userId = user.getUserId();

        // Act
        user.setPseudonym("NewUser");
        userRepository.saveUser(user);
        User updatedUser = userRepository.getUserById(userId);

        // Assert
        assertNotNull(updatedUser);
        assertEquals("NewUser", userRepository.getUserById(user.getUserId()).getPseudonym());
    }
}
