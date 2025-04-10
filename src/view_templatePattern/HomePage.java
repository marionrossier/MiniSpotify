package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class HomePage extends AbstractMenuPage {

    public HomePage(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Home Page");
        System.out.print("0) Exit\n" +
                "1) Go to playlists\n" +
                "2) Search\n");
        super.displayPage();
    }

    @Override
    void button0() {
        System.out.println("Exiting...");
    }
    @Override
    void button1() {
        pageFactory.homePagePlaylist.displayPage();
    }

    @Override
    void button2() {
        pageFactory.search.displayPage();
    }

    @Override
    void button3() {
    }

    @Override
    void button4() {

    }

    @Override
    void button5() {

    }

    @Override
    void button6() {

    }

    @Override
    void button7() {

    }
}
