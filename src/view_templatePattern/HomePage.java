package view_templatePattern;

import data.jsons.PlaylistRepository;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;
import services.PlaylistServices;

public class HomePage extends AbstractMenuPage {

    public HomePage(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Home Page";
        this.pageContent =
                icon.iconNbr(0) + icon.iconLogout() + icon.lineBreak +
                icon.iconNbr(1) + "Go to playlists" + icon.lineBreak +
                icon.iconNbr(2) + "Search" + icon.lineBreak +
                icon.iconNbr(3) + "Friends" + icon.lineBreak +
                icon.iconNbr(4) + "Go to music player";
    }

    @Override
    void button0() {
        System.out.println("Logging you out ...");
        spotifyPlayer.stop();

        PlaylistServices playlistServices = new PlaylistServices(new PlaylistRepository());
        playlistServices.deleteTemporaryPlaylist();

        spotifyPageFactory.login.templateMethode();
        Cookies_SingletonPattern.resetCookies();
    }

    @Override
    void button1() {
        spotifyPageFactory.playlistHomePage.templateMethode();
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
