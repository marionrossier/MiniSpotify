package services;

import data.entities.PlanEnum;
import data.entities.User;
import data.jsons.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private File tempFile;
    private UserService userService;
    private UserRepository userRepository;
    private PasswordService passwordService;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("user", ".json").toFile();
        userRepository = new UserRepository(tempFile.getAbsolutePath());
        userService = new UserService(userRepository);
        passwordService = new PasswordService(userRepository);

    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void addUser_ShouldCreateANewUser(){
        // Act
        userService.addUser("TestUser", "test@test.com", "TestPassword", PlanEnum.FREE);

        // Assert
        List<User> users = userRepository.getAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    void getUserIdByPseudo_ShouldReturnTheUserId(){
        // Arrange
        userService.addUser("TestUser", "test@test.com", "TestPassword", PlanEnum.FREE);

        // Act
        int userId = userService.getUserIdByPseudo("TestUser");

        // Assert
        assertTrue(userId > 0);
    }

    @Test
    void verifyUserAuthentification_ShouldReturnTrue_WhenCorrectCredentialAreGiven() {
        // Arrange
        userService.addUser("TestUser", "test@test.com", "CorrectPassword", PlanEnum.FREE);

        // Act
        boolean result = passwordService.verifyUserAuthentification("TestUser", "CorrectPassword");

        // Assert
        assertTrue(result);
    }

    @Test
    void verifyUserAuthentification_ShouldReturnFalse_WhenWrongCredentialAreGiven() {
        // Arrange
        userService.addUser("TestUser", "test@test.com", "CorrectPassword", PlanEnum.FREE);

        // Act
        boolean result = passwordService.verifyUserAuthentification("TestUser", "WrongPassword");

        // Assert
        assertFalse(result);
    }
}
