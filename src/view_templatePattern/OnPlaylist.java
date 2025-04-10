package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class OnPlaylist extends AbstractMenuPage{

    public OnPlaylist(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("On Playlist");
        System.out.print("0) Exit\n" +
                "1) Play the playlist\n" +
                "2) Edit the playlist\n" +
                "3) Delete the playlist\n");
        super.displayPage();
    }

    @Override
    void button0() {
        pageFactory.choseYourPlaylist.displayPage();
    }

    @Override
    void button1() {
        //play the playlist
    }

    @Override
    void button2() {
        pageFactory.editPlaylist.displayPage();
    }

    @Override
    void button3() {
        pageFactory.deletePlaylist.displayPage();
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
