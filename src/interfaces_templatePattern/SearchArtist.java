package interfaces_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class SearchArtist extends AbstractMenuPage {
    private String artistName;

    public SearchArtist(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }
    @Override
    void displayPage() {
        System.out.println("Search Artist Page");
        System.out.println("Enter the name of the artist:");
        artistName = in.nextLine();
        //search a song...
//        0) Exit
//        Songs:
//        1) ... --> Songplayer
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
