package services;

import serverSide.entities.PlanEnum;
import serverSide.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsAndFakes.CommuneMethods;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends CommuneMethods {

    public UserServiceTest() throws IOException {
    }

    @BeforeEach
    void setUp() throws IOException {
    }

    @AfterEach
    void tearDown() {
        if (tempUsersFile.exists()) {
            tempUsersFile.delete();
        }
    }

    @Test
    void addUser_ShouldCreateANewUser(){
        // Act
        userService.addUser("TestUser", "test@test.com", "TestPassword", PlanEnum.FREE);

        // Assert
        List<User> users = userLocalRepository.getAllUsers();
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
