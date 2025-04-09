package interfaces_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class Search extends AbstractMenuPage {

    public Search(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Search Page");
        System.out.print("0) Exit\n" +
                "1) Search a song\n" +
                "2) Search an artist\n" +
                "3) Search a song gender\n");
        super.displayPage();
    }

    @Override
    void button1() {
        pageFactory.searchSong.displayPage();
    }

    @Override
    void button2() {
        pageFactory.searchArtist.displayPage();
    }

    @Override
    void button3() {
        pageFactory.searchGender.displayPage();
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
