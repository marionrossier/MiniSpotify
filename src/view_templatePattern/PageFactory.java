package view_templatePattern;

import services.player_commandPattern.SpotifyPlayer;

import java.util.Scanner;

public class PageFactory {
    Scanner in = new Scanner(System.in);
    ChoseYourPlaylist choseYourPlaylist;
    CreatePlaylist createPlaylist;
    DeletePlaylist deletePlaylist;
    EditPlaylist editPlaylist;
    HomePage homePage;
    HomePagePlaylist homePagePlaylist;
    Login login;
    OnPlaylist onPlaylist;
    Search search;
    SearchArtist searchArtist;
    SearchGender searchGender;
    SearchSong searchSong;
    SongPlayer songPlayer;
    SpotifyPlayer spotifyPlayer;

    public void setUpPages() {
        this.choseYourPlaylist = new ChoseYourPlaylist(this, spotifyPlayer);
        this.createPlaylist = new CreatePlaylist(this, spotifyPlayer);
        this.deletePlaylist = new DeletePlaylist(this, spotifyPlayer);
        this.editPlaylist = new EditPlaylist(this, spotifyPlayer);
        this.homePage = new HomePage(this, spotifyPlayer);
        this.homePagePlaylist = new HomePagePlaylist(this, spotifyPlayer);
        this.login = new Login(this, spotifyPlayer);
        this.onPlaylist = new OnPlaylist(this, spotifyPlayer);
        this.search = new Search(this, spotifyPlayer);
        this.searchArtist = new SearchArtist(this, spotifyPlayer);
        this.searchGender = new SearchGender(this, spotifyPlayer);
        this.searchSong = new SearchSong(this, spotifyPlayer);
        this.songPlayer = new SongPlayer(this, spotifyPlayer);
    }

    public void startLogin(){
        login.displayPage();
    }
}
