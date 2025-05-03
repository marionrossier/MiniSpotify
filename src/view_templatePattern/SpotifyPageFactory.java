package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;

//TODO : Modifier ? pour que les pages soient crées au moment ou elles sont appellés.
public class SpotifyPageFactory {
    PlaylistChoseList playlistChoseList;
    PlaylistCreation playlistCreation;
    PlaylistDeletion playlistDeletion;
    FriendAddAFriend friendAddAFriend;
    FriendsCommunePlaylists friendsCommunePlaylists;
    FriendsDisplayFriends friendsDisplayFriends;
    FriendsHomePageTemplate friendsHomePage;
    FriendAddPlaylist friendAddPlaylist;
    HomePage homePage;
    public PlaylistHomePageTemplate playlistHomePage;
    Login login;
    CreateAccount createAccount;
    PlaylistDisplay playlistDisplay;
    Search search;
    SongPlayer songPlayer;
    public IPlaylistPlayer spotifyPlayer;
    SearchGender searchGender;
    FriendInformation friendInformation;
    FriendRemoveAFriend friendRemoveAFriend;
    ActionFoundedSongs actionFoundedSongs;

    public void setUpPages() {
        this.playlistChoseList = new PlaylistChoseList(this, spotifyPlayer, 1);
        this.playlistCreation = new PlaylistCreation(this, spotifyPlayer, 2);
        this.playlistDeletion = new PlaylistDeletion(this, spotifyPlayer, 3);
        this.friendAddAFriend = new FriendAddAFriend(this, spotifyPlayer, 4);
        this.friendsCommunePlaylists = new FriendsCommunePlaylists(this, spotifyPlayer, 5);
        this.friendsDisplayFriends = new FriendsDisplayFriends(this, spotifyPlayer, 6);
        this.friendsHomePage = new FriendsHomePageTemplate(this, spotifyPlayer, 7);
        this.friendAddPlaylist = new FriendAddPlaylist(this, spotifyPlayer, 8);
        this.homePage = new HomePage(this, spotifyPlayer, 9);
        this.playlistHomePage = new PlaylistHomePageTemplate(this, spotifyPlayer, 10);
        this.login = new Login(this, spotifyPlayer, 11);
        this.createAccount = new CreateAccount(this, spotifyPlayer, 12);
        this.playlistDisplay = new PlaylistDisplay(this, spotifyPlayer, 13);
        this.search = new Search(this, spotifyPlayer, 14);
        this.songPlayer = new SongPlayer(this, spotifyPlayer, 15);
        this.searchGender = new SearchGender(this, spotifyPlayer, 16);
        this.friendInformation = new FriendInformation(this, spotifyPlayer, 17);
        this.friendRemoveAFriend = new FriendRemoveAFriend(this, spotifyPlayer, 18);
        this.actionFoundedSongs = new ActionFoundedSongs(this, spotifyPlayer, 19);
    }

    public void startLogin(){
        login.displayAllPage();
    }

    public _MenuInterface pageManager (int menuPageId){
        switch (menuPageId){
            case 1:
                return this.playlistChoseList;
            case 2:
                return this.playlistCreation;
            case 3 :
                return this.playlistDeletion;
            case 4:
                return this.friendAddAFriend;
            case 5 :
                return this.friendsCommunePlaylists;
            case 6 :
                return this.friendsDisplayFriends;
            case 7 :
                return this.friendsHomePage;
            case 8 :
                return this.friendAddPlaylist;
            case 9 :
                return this.homePage;
            case 10 :
                return this.playlistHomePage;
            case 11 :
                return this.login;
            case 12 :
                return this.createAccount;
            case 13 :
                return this.playlistDisplay;
            case 14 :
                return this.search;
            case 15 :
                return this.songPlayer;
            case 16 :
                return this.searchGender;
            case 17 :
                return this.friendInformation;
            case 18 :
                return this.friendRemoveAFriend;
            case 19 :
                return this.actionFoundedSongs;
            default :
                return null;
        }
    }
}
