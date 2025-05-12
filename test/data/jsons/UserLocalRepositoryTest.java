package data.jsons;

import serverSide.entities.PlanEnum;
import serverSide.entities.User;
import serverSide.repositoriesPattern.UserLocalRepository;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserLocalRepositoryTest {

    private File tempFile;
    private UserLocalRepository userLocalRepository;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("user", ".json").toFile();
        userLocalRepository = new UserLocalRepository(tempFile.getAbsolutePath());
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
        userLocalRepository.saveUser(user);

        // Assert
        List<User> users = userLocalRepository.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("TestUser", users.get(0).getPseudonym());
    }

    @Test
    void removeUserById_shouldDeleteTheUser() {
        // Arrange
        User userOne = new User("UserOne", "one@example.com", "1234", PlanEnum.FREE);
        User userTwo = new User("UserTwo", "two@example.com", "1234", PlanEnum.FREE);
        userLocalRepository.saveUser(userOne);
        userLocalRepository.saveUser(userTwo);

        // Act
        userLocalRepository.removeUserById(userOne.getUserId());

        // Assert
        List<User> result = userLocalRepository.getAllUsers();
        assertEquals(1, result.size());
        assertEquals(userTwo.getUserId(), result.get(0).getUserId());
    }

    @Test
    void getUserById_shouldFindTheUser() {
        // Arrange
        User user = new User("TestUser", "test@example.com", "1234", PlanEnum.FREE);
        userLocalRepository.saveUser(user);

        // Act
        User result = userLocalRepository.getUserById(user.getUserId());

        // Assert
        assertNotNull(result);
        assertEquals("TestUser", result.getPseudonym());
    }

    @Test
    void getUserByPseudonym_shouldFindTheUser() {
        // Arrange
        User user = new User("TestUser", "test@example.com", "1234", PlanEnum.FREE);
        userLocalRepository.saveUser(user);

        // Act
        User result = userLocalRepository.getUserByPseudonym("TestUser");

        // Assert
        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());
    }

    @Test
    void updateAccount_shouldModifyTheUser() {
        // Arrange
        User user = new User("OldUser", "old@example.com", "1234", PlanEnum.FREE);
        userLocalRepository.saveUser(user);
        int userId = user.getUserId();

        // Act
        user.setPseudonym("NewUser");
        userLocalRepository.saveUser(user);
        User updatedUser = userLocalRepository.getUserById(userId);

        // Assert
        assertNotNull(updatedUser);
        assertEquals("NewUser", userLocalRepository.getUserById(user.getUserId()).getPseudonym());
    }
}
