package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class SpotifyPageFactory {
    ChoseYourPlaylist choseYourPlaylist;
    CreatePlaylist createPlaylist;
    DeletePlaylist deletePlaylist;
    EditPlaylist editPlaylist;
    FriendAddAFriend friendAddAFriend;
    FriendsCommunePlaylists friendsCommunePlaylists;
    FriendsDisplayFriends friendsDisplayFriends;
    FriendsHomePage friendsHomePage;
    AddFriendPlaylist addFriendPlaylist;
    HomePage homePage;
    HomePagePlaylist homePagePlaylist;
    Login login;
    CreateAccount createAccount;
    OnPlaylist onPlaylist;
    Search search;
    SongPlayer songPlayer;
    SpotifyPlayer spotifyPlayer;
    SearchGender searchGender;
    FriendInformation friendInformation;
    FriendRemoveAFriend friendRemoveAFriend;

    public void setUpPages() {
        this.choseYourPlaylist = new ChoseYourPlaylist(this, spotifyPlayer);
        this.createPlaylist = new CreatePlaylist(this, spotifyPlayer);
        this.deletePlaylist = new DeletePlaylist(this, spotifyPlayer);
        this.editPlaylist = new EditPlaylist(this, spotifyPlayer);
        this.friendAddAFriend = new FriendAddAFriend(this, spotifyPlayer);
        this.friendsCommunePlaylists = new FriendsCommunePlaylists(this, spotifyPlayer);
        this.friendsDisplayFriends = new FriendsDisplayFriends(this, spotifyPlayer);
        this.friendsHomePage = new FriendsHomePage(this, spotifyPlayer);
        this.addFriendPlaylist = new AddFriendPlaylist(this, spotifyPlayer);
        this.homePage = new HomePage(this, spotifyPlayer);
        this.homePagePlaylist = new HomePagePlaylist(this, spotifyPlayer);
        this.login = new Login(this, spotifyPlayer);
        this.createAccount = new CreateAccount(this, spotifyPlayer);
        this.onPlaylist = new OnPlaylist(this, spotifyPlayer);
        this.search = new Search(this, spotifyPlayer);
        this.songPlayer = new SongPlayer(this, spotifyPlayer);
        this.searchGender = new SearchGender(this, spotifyPlayer);
        this.friendInformation = new FriendInformation(this, spotifyPlayer);
        this.friendRemoveAFriend = new FriendRemoveAFriend(this, spotifyPlayer);
    }

    public void startLogin(){
        login.templateMethode();
    }
}
