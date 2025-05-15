package services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsAndFakes.CommuneMethods;
import utilsAndFakes.Initializer;

import static org.junit.jupiter.api.Assertions.*;

class PageServiceTest{

    private CommuneMethods communeMethods;
    private Initializer initializer;

    public PageServiceTest(){
    }

    @BeforeEach
    void setUp() {
        communeMethods = new CommuneMethods();
        initializer = communeMethods.initializer;
    }

    @AfterEach
    void tearDown() {
        initializer.pageService.getMenuPages().clear();
    }

    @Test
    void testGetPageById() {
        //Arrange
        //Act
        int pageId = initializer.pageService.homePage.getPageId();
        boolean exist = false;
        if (pageId > 0){
            exist = true;
        }
        //Assert
        assertTrue(exist);
    }

    //TODO : régler ces 2 tests
//    @Test
//    void testGoBack() {
//        //Arrange
//        initializer.pageService.getMenuPages().push(initializer.pageService.login.pageId);
//        initializer.pageService.getMenuPages().push(initializer.pageService.homePage.pageId);
//        initializer.pageService.getMenuPages().push(initializer.pageService.playlistHomePage.pageId);
//        initializer.pageService.getMenuPages().push(initializer.pageService.playlistPageOpen.pageId);
//        initializer.pageService.getMenuPages().push(initializer.pageService.playlistPageOpen.pageId);
//        int playlistHomePageId = initializer.pageService.playlistHomePage.pageId;
//        int playlistPageOpen = initializer.pageService.playlistPageOpen.pageId;
//        //Act
//        initializer.pageService.goBack(playlistPageOpen);
//        //Assert
//        assertEquals(playlistHomePageId, initializer.pageService.getMenuPages().peek());
//    }
//
//    @Test
//    void testGotAnInput() {
//        //Arrange
//        initializer.menuPagesStack.push(initializer.pageService.playlistHomePage.pageId);
//        String input = "0";
//
//        //Act
//        initializer.pageService.gotAnInput(input);
//        String inputCheck = initializer.pageService.gotAnInput(input);
//
//        //Assert
//        assertEquals("", inputCheck);
//    }
}