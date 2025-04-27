package data.jsons;

import data.entities.PlanEnum;
import data.entities.User;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private File tempFile;
    private UserRepository repo;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("user", ".json").toFile();
        repo = new UserRepository(tempFile.getAbsolutePath());
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void addUser_shouldSaveTheUser() {
        // Arrange
        User user = new User("TestUser", "test@example.com", "1234", PlanEnum.FREE);

        // Act
        repo.addUser(user);

        // Assert
        List<User> users = repo.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("TestUser", users.get(0).getPseudonym());
    }

    @Test
    void removeUserById_shouldDeleteTheUser() {
        // Arrange
        User userOne = new User("UserOne", "one@example.com", "1234", PlanEnum.FREE);
        User userTwo = new User("UserTwo", "two@example.com", "1234", PlanEnum.FREE);
        repo.addUser(userOne);
        repo.addUser(userTwo);

        // Act
        repo.removeUserById(userOne.getUserId());

        // Assert
        List<User> result = repo.getAllUsers();
        assertEquals(1, result.size());
        assertEquals(userTwo.getUserId(), result.get(0).getUserId());
    }

    @Test
    void getUserById_shouldFindTheUser() {
        // Arrange
        User user = new User("TestUser", "test@example.com", "1234", PlanEnum.FREE);
        repo.addUser(user);

        // Act
        User result = repo.getUserById(user.getUserId());

        // Assert
        assertNotNull(result);
        assertEquals("TestUser", result.getPseudonym());
    }

    @Test
    void getUserByPseudonym_shouldFindTheUser() {
        // Arrange
        User user = new User("TestUser", "test@example.com", "1234", PlanEnum.FREE);
        repo.addUser(user);

        // Act
        User result = repo.getUserByPseudonym("TestUser");

        // Assert
        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());
    }

    @Test
    void updateAccount_shouldModifyTheUser() {
        // Arrange
        User user = new User("OldUser", "old@example.com", "1234", PlanEnum.FREE);
        repo.addUser(user);

        // Act
        user.setPseudonym("NewUser");
        User updatedUser = repo.updateAccount(user);

        // Assert
        assertNotNull(updatedUser);
        assertEquals("NewUser", repo.getUserById(user.getUserId()).getPseudonym());
    }
}
