package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;

public class HomePage extends _SimplePageTemplate {

    public HomePage(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Home Page";
        this.pageContent =
                icon.iconNbr(0) + icon.iconLogout() + icon.lineBreak +
                icon.iconNbr(1) + "Go to playlists" + icon.lineBreak +
                icon.iconNbr(2) + "Search" + icon.lineBreak +
                icon.iconNbr(3) + "Friends" + icon.lineBreak +
                icon.iconNbr(4) + "Go to music player";
    }

    @Override
    public void button0() {
        System.out.println("Logging you out ...");
        spotifyPlayer.stop();

        toolbox.getPlaylistServ().deleteTemporaryPlaylist();

        spotifyPageFactory.login.displayAllPage();
        Cookies_SingletonPattern.resetCookies();
    }

    @Override
    public void button1() {
        spotifyPageFactory.playlistHomePage.displayAllPage();
    }

    @Override
    public void button2() {
        spotifyPageFactory.search.displayAllPage();
    }

    @Override
    public void button3() {
        spotifyPageFactory.friendsHomePage.displayAllPage();
    }

    @Override
    public void button4(){
        spotifyPageFactory.songPlayer.displayAllPage();
    }
}
