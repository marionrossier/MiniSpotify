package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class FriendsHomePage extends AbstractMenuPage{

    public FriendsHomePage(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Friends");
        System.out.print("0) Exit\n" +
                "1) display friends\n" +
                "2) see commune playlists\n" +
                "3) add a friend\n" +
                "4) link a playlist to a friend\n");
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
