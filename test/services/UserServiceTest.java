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
        super();
    }

    @BeforeEach
    void setUp() throws IOException {
    }

    @AfterEach
    void tearDown() {
        if (initializer.tempUsersFile.exists()) {
            initializer.tempUsersFile.delete();
        }
    }

    @Test
    void addUser_ShouldCreateANewUser(){
        // Act
        initializer.userService.addUser("TestUser", "test@test.com", "TestPassword", PlanEnum.FREE);

        // Assert
        List<User> users = initializer.userLocalRepository.getAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    void getUserIdByPseudo_ShouldReturnTheUserId(){
        // Arrange
        initializer.userService.addUser("TestUser", "test@test.com", "TestPassword", PlanEnum.FREE);

        // Act
        int userId = initializer.userService.getUserIdByPseudo("TestUser");

        // Assert
        assertTrue(userId > 0);
    }

    @Test
    void verifyUserAuthentification_ShouldReturnTrue_WhenCorrectCredentialAreGiven() {
        // Arrange
        initializer.userService.addUser("TestUser", "test@test.com", "CorrectPassword", PlanEnum.FREE);

        // Act
        boolean result = initializer.passwordService.verifyUserAuthentification("TestUser", "CorrectPassword");

        // Assert
        assertTrue(result);
    }

    @Test
    void verifyUserAuthentification_ShouldReturnFalse_WhenWrongCredentialAreGiven() {
        // Arrange
        initializer.userService.addUser("TestUser", "test@test.com", "CorrectPassword", PlanEnum.FREE);

        // Act
        boolean result = initializer.passwordService.verifyUserAuthentification("TestUser", "WrongPassword");

        // Assert
        assertFalse(result);
    }
}
