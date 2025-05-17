package jsons;

import serverSide.entities.*;
import serverSide.repoLocal.*;
import org.junit.jupiter.api.*;
import utilsAndFakes.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserLocalRepositoryTest {

    private File tempFile;
    private UserLocalRepository userLocalRepository;
    private TestHelper testHelper;
    private DependencyProvider dependencyProvider;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("user", ".json").toFile();
        userLocalRepository = new UserLocalRepository(tempFile.getAbsolutePath());
        testHelper = new TestHelper(45000);
        dependencyProvider = testHelper.dependencyProvider;
    }

    @AfterEach
    void tearDown() {
        dependencyProvider.cleanUp();
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
