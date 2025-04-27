package services;

import data.entities.PlanEnum;
import data.entities.User;
import data.jsons.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private File tempFile;
    private UserService service;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("user", ".json").toFile();
        userRepository = new UserRepository(tempFile.getAbsolutePath());
        service = new UserService(userRepository);
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
        service.addUser("TestUser", "test@test.com", "TestPassword", PlanEnum.FREE);

        // Assert
        List<User> users = userRepository.getAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    void getUserIdByPseudo_ShouldReturnTheUserId(){
        // Arrange
        service.addUser("TestUser", "test@test.com", "TestPassword", PlanEnum.FREE);

        // Act
        int userId = service.getUserIdByPseudo("TestUser");

        // Assert
        assertTrue(userId > 0);
    }

    @Test
    void verifyUserAuthentification_ShouldReturnTrue_WhenCorrectCredentialAreGiven() {
        // Arrange
        service.addUser("TestUser", "test@test.com", "CorrectPassword", PlanEnum.FREE);

        // Act
        boolean result = service.verifyUserAuthentification("TestUser", "CorrectPassword");

        // Assert
        assertTrue(result);
    }

    @Test
    void verifyUserAuthentification_ShouldReturnFalse_WhenWrongCredentialAreGiven() {
        // Arrange
        service.addUser("TestUser", "test@test.com", "CorrectPassword", PlanEnum.FREE);

        // Act
        boolean result = service.verifyUserAuthentification("TestUser", "WrongPassword");

        // Assert
        assertFalse(result);
    }
}
