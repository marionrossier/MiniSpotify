package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class HomePagePlaylist extends AbstractMenuPage {


    public HomePagePlaylist(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Home Page Playlist");
        System.out.print("0) Exit\n" +
                "1) Choose your playlist\n" +
                "2) Create a playlist\n");
        super.displayPage();
    }

    @Override
    void button1() {
        pageFactory.choseYourPlaylist.displayPage();
    }

    @Override
    void button2() {
        pageFactory.createPlaylist.displayPage();
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
