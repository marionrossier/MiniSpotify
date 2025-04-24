package view_templatePattern;

import player_commandPattern.SpotifyPlayer;

public class SpotifyPageFactory {
    PlaylistChoseList playlistChoseList;
    PlaylistCreation playlistCreation;
    PlaylistDeletion playlistDeletion;
    FriendAddAFriend friendAddAFriend;
    FriendsCommunePlaylists friendsCommunePlaylists;
    FriendsDisplayFriends friendsDisplayFriends;
    FriendsHomePage friendsHomePage;
    FriendAddPlaylist friendAddPlaylist;
    HomePage homePage;
    PlaylistHomePage playlistHomePage;
    Login login;
    CreateAccount createAccount;
    PlaylistDisplay playlistDisplay;
    Search search;
    SongPlayer songPlayer;
    public SpotifyPlayer spotifyPlayer;
    SearchGender searchGender;
    FriendInformation friendInformation;
    FriendRemoveAFriend friendRemoveAFriend;

    public void setUpPages() {
        this.playlistChoseList = new PlaylistChoseList(this, spotifyPlayer);
        this.playlistCreation = new PlaylistCreation(this, spotifyPlayer);
        this.playlistDeletion = new PlaylistDeletion(this, spotifyPlayer);
        this.friendAddAFriend = new FriendAddAFriend(this, spotifyPlayer);
        this.friendsCommunePlaylists = new FriendsCommunePlaylists(this, spotifyPlayer);
        this.friendsDisplayFriends = new FriendsDisplayFriends(this, spotifyPlayer);
        this.friendsHomePage = new FriendsHomePage(this, spotifyPlayer);
        this.friendAddPlaylist = new FriendAddPlaylist(this, spotifyPlayer);
        this.homePage = new HomePage(this, spotifyPlayer);
        this.playlistHomePage = new PlaylistHomePage(this, spotifyPlayer);
        this.login = new Login(this, spotifyPlayer);
        this.createAccount = new CreateAccount(this, spotifyPlayer);
        this.playlistDisplay = new PlaylistDisplay(this, spotifyPlayer);
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
