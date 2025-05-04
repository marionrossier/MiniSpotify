package services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageServiceTest {

    private PageService pageService;

    @BeforeEach
    void setUp() {
        pageService = new PageService();
        pageService.setUpPages();
    }

    @AfterEach
    void tearDown() {
        pageService.getMenuPages().clear();
    }

    @Test
    void tesGetPageById() {
        //Arrange

        //Act
        int pageId = pageService.search.getPageId();
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
//        pageService.menuPages.push(pageService.login.pageId);
//        pageService.menuPages.push(pageService.homePage.pageId);
//        pageService.menuPages.push(pageService.playlistHomePage.pageId);
//        pageService.menuPages.push(pageService.playlistDisplay.pageId);
//        pageService.menuPages.push(pageService.playlistDisplay.pageId);
//        int playlistHomePageId = pageService.playlistHomePage.pageId;
//        int playlistDisplayId = pageService.playlistDisplay.pageId;
//        //Act
//        pageService.goBack(playlistDisplayId);
//        //Assert
//        assertEquals(playlistHomePageId, pageService.getMenuPages().peek());
//    }

//    @Test
//    void testGotAnInput() {
//        //Arrange
//        pageService.menuPages.push(pageService.login.pageId);
//        pageService.menuPages.push(pageService.homePage.pageId);
//        String input = "0";
//
//        //Act
//        String inputCheck = pageService.gotAnInput(input);
//
//        //Assert
//        assertEquals("", inputCheck);
//    }
}