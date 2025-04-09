package interfaces_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class ChoseYourPlaylist extends AbstractMenuPage {

    public ChoseYourPlaylist(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Chose your playlist");
        System.out.print("0) Exit\n");
        //implement loop for all playlists
        super.displayPage();

    }

    @Override
    void button1() {

    }

    @Override
    void button2() {

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
