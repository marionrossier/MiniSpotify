package services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilsAndFakes.CommuneMethods;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PageServiceTest extends CommuneMethods {

    public PageServiceTest() throws IOException {
        super();
    }

    @BeforeEach
    void setUp() {
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

//    @Test
//    void testGoBack() {
//        //Arrange
//        pageService.getMenuPages().push(pageService.login.pageId);
//        pageService.getMenuPages().push(pageService.homePage.pageId);
//        pageService.getMenuPages().push(pageService.playlistHomePage.pageId);
//        pageService.getMenuPages().push(pageService.playlistPageOpen.pageId);
//        pageService.getMenuPages().push(pageService.playlistPageOpen.pageId);
//        int playlistHomePageId = pageService.playlistHomePage.pageId;
//        int playlistPageOpen = pageService.playlistPageOpen.pageId;
//        //Act
//        pageService.goBack(playlistPageOpen);
//        //Assert
//        assertEquals(playlistHomePageId, pageService.getMenuPages().peek());
//    }

//    @Test
//    void testGotAnInput() {
//        //Arrange
//        menuPagesStack.push(pageService.playlistHomePage.pageId);
//        String input = "0";
//
//        //Act
//        pageService.gotAnInput(input);
//        String inputCheck = pageService.gotAnInput(input);
//
//        //Assert
//        assertEquals("", inputCheck);
//    }
}