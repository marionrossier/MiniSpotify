package view_templatePattern;

import player_commandPattern.SpotifyPlayer;
import services.Cookies_SingeltonPattern;

public class HomePage extends AbstractMenuPage {

    public HomePage(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Home Page";
        this.pageContent =
                logoutLineWith0 + lineBreak +
                nb1 + "Go to playlists" + lineBreak +
                nb2 + "Search" + lineBreak +
                nb3 + "Friends" + lineBreak +
                nb4 + "Go to music player";
    }

    @Override
    void button0() {
        System.out.println("Logging you out ...");
        spotifyPageFactory.login.templateMethode();
        Cookies_SingeltonPattern.resetCookies();
    }

    @Override
    void button1() {
        spotifyPageFactory.choseYourPlaylist.templateMethode();
    }

    @Override
    void button2() {
        spotifyPageFactory.search.templateMethode();
    }

    @Override
    void button3() {
        spotifyPageFactory.friendsHomePage.templateMethode();
    }

    @Override
    void button4(){
        spotifyPageFactory.songPlayer.templateMethode();
    }
}
