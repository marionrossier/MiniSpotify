package interfaces_templatePattern;

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

    public void setUpPages() {
        this.choseYourPlaylist = new ChoseYourPlaylist(this);
        this.createPlaylist = new CreatePlaylist(this);
        this.deletePlaylist = new DeletePlaylist(this);
        this.editPlaylist = new EditPlaylist(this);
        this.homePage = new HomePage(this);
        this.homePagePlaylist = new HomePagePlaylist(this);
        this.login = new Login(this);
        this.onPlaylist = new OnPlaylist(this);
        this.search = new Search(this);
        this.searchArtist = new SearchArtist(this);
        this.searchGender = new SearchGender(this);
        this.searchSong = new SearchSong(this);
        this.songPlayer = new SongPlayer(this);
    }

    public void startLogin(){
        login.displayPage();
    }
}
