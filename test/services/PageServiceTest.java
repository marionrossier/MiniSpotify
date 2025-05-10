package services;

import clientSide.services.NavigationStackService;
import clientSide.services.PageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageServiceTest {

    private PageService pageService;
    private NavigationStackService navigationStackService;

    @BeforeEach
    void setUp() {
        pageService = new PageService(null);
        navigationStackService = new NavigationStackService(pageService);
        System.out.println("");
    }

    @AfterEach
    void tearDown() {
        pageService.getMenuPages().clear();
    }

    @Test
    void testGetPageById() {
        //Arrange

        //Act
        int pageId = pageService.homePage.getPageId();
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
//        navigationStackService.menuPages.push(pageService.login.pageId);
//        navigationStackService.menuPages.push(pageService.homePage.pageId);
//        navigationStackService.menuPages.push(pageService.playlistHomePage.pageId);
//        navigationStackService.menuPages.push(pageService.playlistPageOpen.pageId);
//        navigationStackService.menuPages.push(pageService.playlistPageOpen.pageId);
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
//        navigationStackService.menuPages.push(pageService.login.pageId);
//        navigationStackService.menuPages.push(pageService.homePage.pageId);
//        String input = "0";
//
//        //Act
//        String inputCheck = pageService.gotAnInput(input);
//
//        //Assert
//        assertEquals("", inputCheck);
//    }
}