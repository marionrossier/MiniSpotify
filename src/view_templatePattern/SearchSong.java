package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class SearchSong extends AbstractMenuPage{
    private String songName;

    public SearchSong(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }
    @Override
    void displayPage() {
        System.out.println("Search Song Page");
        System.out.println("Enter the name of the song:");
        songName = in.nextLine();
        //search a song...
        //SpotifyPlayer.searchSong(songName);
//        0) Exit
//        Songs:
//        1) ...  --> Songplayer
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
