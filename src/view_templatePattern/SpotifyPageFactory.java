package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class SpotifyPageFactory {
    ChoseYourPlaylist choseYourPlaylist;
    CreatePlaylist createPlaylist;
    DeletePlaylist deletePlaylist;
    EditPlaylist editPlaylist;
    FriendsFollowFriend friendsFollowFriend;
    FriendsCommunePlaylists friendsCommunePlaylists;
    FriendsDisplayFriends friendsDisplayFriends;
    FriendsHomePage friendsHomePage;
    FriendsLinkPlaylistFriend friendsLinkPlaylistFriend;
    HomePage homePage;
    HomePagePlaylist homePagePlaylist;
    Login login;
    CreateAccount createAccount;
    OnPlaylist onPlaylist;
    Search search;
    SongPlayer songPlayer;
    SpotifyPlayer spotifyPlayer;

    public void setUpPages() {
        this.choseYourPlaylist = new ChoseYourPlaylist(this, spotifyPlayer);
        this.createPlaylist = new CreatePlaylist(this, spotifyPlayer);
        this.deletePlaylist = new DeletePlaylist(this, spotifyPlayer);
        this.editPlaylist = new EditPlaylist(this, spotifyPlayer);
        this.friendsFollowFriend = new FriendsFollowFriend(this, spotifyPlayer);
        this.friendsCommunePlaylists = new FriendsCommunePlaylists(this, spotifyPlayer);
        this.friendsDisplayFriends = new FriendsDisplayFriends(this, spotifyPlayer);
        this.friendsHomePage = new FriendsHomePage(this, spotifyPlayer);
        this.friendsLinkPlaylistFriend = new FriendsLinkPlaylistFriend(this, spotifyPlayer);
        this.homePage = new HomePage(this, spotifyPlayer);
        this.homePagePlaylist = new HomePagePlaylist(this, spotifyPlayer);
        this.login = new Login(this, spotifyPlayer);
        this.createAccount = new CreateAccount(this, spotifyPlayer);
        this.onPlaylist = new OnPlaylist(this, spotifyPlayer);
        this.search = new Search(this, spotifyPlayer);
        this.songPlayer = new SongPlayer(this, spotifyPlayer);
    }

    public void startLogin(){
        login.templateMethode();
    }
}
