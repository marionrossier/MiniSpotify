package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;

public class HomePage extends AbstractMenuPage {

    public HomePage(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
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
        Cookies_SingletonPattern.resetCookies();

    }

    @Override
    void button1() {
        spotifyPageFactory.playlistChoseList.templateMethode();
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
