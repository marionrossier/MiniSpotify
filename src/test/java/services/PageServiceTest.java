package services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsAndFakes.TestHelper;
import utilsAndFakes.DependencyProvider;

import static org.junit.jupiter.api.Assertions.*;

class PageServiceTest{

    private TestHelper testHelper;
    private DependencyProvider dependencyProvider;

    public PageServiceTest(){
    }

    @BeforeEach
    void setUp() {
        testHelper = new TestHelper(45000);
        dependencyProvider = testHelper.dependencyProvider;
    }

    @AfterEach
    void tearDown() {
        dependencyProvider.pageService.getMenuPages().clear();
    }

    @Test
    void testGetPageById() {
        //Arrange
        //Act
        int pageId = dependencyProvider.pageService.homePage.getPageId();
        boolean exist = false;
        if (pageId > 0){
            exist = true;
        }
        //Assert
        assertTrue(exist);
    }
}