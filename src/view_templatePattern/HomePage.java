package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class HomePage extends AbstractMenuPage {

    public HomePage(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Home Page";
        this.pageContent =
                logoutLineWith0 + lineBreak +
                nb1 + "Go to playlists" + lineBreak +
                nb2 + "Search" + lineBreak +
                nb3 + "Friends";
    }

    @Override
    void button0() {
        System.out.println("Logging you out ...");
        spotifyPageFactory.login.templateMethode();
    }

    @Override
    void button1() {
        spotifyPageFactory.homePagePlaylist.templateMethode();
    }

    @Override
    void button2() {
        spotifyPageFactory.search.templateMethode();
    }

    @Override
    void button3() {
        spotifyPageFactory.friendsHomePage.templateMethode();
    }
}
