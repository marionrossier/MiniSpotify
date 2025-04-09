package interfaces_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class CreatePlaylist extends AbstractMenuPage{
    private String playlistName;

    public CreatePlaylist(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Create Playlist Page");
        System.out.println("Enter the name of the playlist:");
        playlistName = in.nextLine();
        //search a song...

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
