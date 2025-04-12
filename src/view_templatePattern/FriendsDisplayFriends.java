package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

public class FriendsDisplayFriends extends AbstractMenuPage{
    public FriendsDisplayFriends(PageFactory pageFactory, SpotifyPlayer spotifyPlayer) {
        super(pageFactory, spotifyPlayer);
    }

    @Override
    void displayPage() {
        System.out.println("Display friends");
        System.out.print("0) Exit\n" +
                "Tip the friend's number to add him a playlist\n");
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
