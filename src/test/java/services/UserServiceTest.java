package services;

import common.entities.PlanEnum;
import common.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsAndFakes.TestHelper;
import utilsAndFakes.DependencyProvider;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest{
    private TestHelper testHelper;
    private DependencyProvider dependencyProvider;

    public UserServiceTest(){
    }

    @BeforeEach
    void setUp(){
        testHelper = new TestHelper(45000);
        dependencyProvider = testHelper.dependencyProvider;
    }

    @AfterEach
    void tearDown() {
        if (dependencyProvider.tempUsersFile.exists()) {
            dependencyProvider.tempUsersFile.delete();
        }
    }

    @Test
    void addUser_ShouldCreateANewUser(){
        // Act
        dependencyProvider.userService.addUser("TestUser", "test@test.com", "TestPassword", PlanEnum.FREE);

        // Assert
        List<User> users = dependencyProvider.userLocalRepository.getAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    void verifyUserAuthentification_ShouldReturnTrue_WhenCorrectCredentialAreGiven() {
        // Arrange
        dependencyProvider.userService.addUser("TestUser", "test@test.com", "CorrectPassword", PlanEnum.FREE);

        // Act
        boolean result = dependencyProvider.passwordService.verifyUserAuthentification("TestUser", "CorrectPassword");

        // Assert
        assertTrue(result);
    }

    @Test
    void verifyUserAuthentification_ShouldReturnFalse_WhenWrongCredentialAreGiven() {
        // Arrange
        dependencyProvider.userService.addUser("TestUser", "test@test.com", "CorrectPassword", PlanEnum.FREE);

        // Act
        boolean result = dependencyProvider.passwordService.verifyUserAuthentification("TestUser", "WrongPassword");

        // Assert
        assertFalse(result);
    }
}
