package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class FriendsCommunePlaylists extends AbstractMenuPage{
    public FriendsCommunePlaylists(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Commune Playlists");
        System.out.print("0) Exit\n" +
                "Tip the playlist's number to play it\n");
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
