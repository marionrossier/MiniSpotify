package services;

import data.jsons.PlaylistRepository;
import data.jsons.UserRepository;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import view_templatePattern.*;

import java.util.ArrayList;
import java.util.Stack;

public class PageService {

    ArrayList<_MenuInterface> pages = new ArrayList<>();
    public final Stack<Integer> menuPages = new Stack<>();
    private UserService userService = new UserService(new UserRepository());

    public PlaylistChoseList playlistChoseList;
    public PlaylistCreation playlistCreation;
    public PlaylistDeletion playlistDeletion;
    public FriendAddAFriend friendAddAFriend;
    public FriendsCommunePlaylists friendsCommunePlaylists;
    public FriendsDisplayFriends friendsDisplayFriends;
    public FriendsHomePageTemplate friendsHomePage;
    public FriendAddPlaylist friendAddPlaylist;
    public HomePage homePage;
    public PlaylistHomePage playlistHomePage;
    public LoginOK login;
    public CreateAccount createAccount;
    public PlaylistDisplay playlistDisplay;
    public Search search;
    public SongPlayer songPlayer;
    public IPlaylistPlayer spotifyPlayer;
    public SearchGender searchGender;
    public FriendInformation friendInformation;
    public FriendRemoveAFriend friendRemoveAFriend;
    public ActionFoundedSongs actionFoundedSongs;

    public void setUpPages() {
        int pageId = 1;

        this.playlistChoseList = new PlaylistChoseList(this, spotifyPlayer, pageId++);
        pages.add(this.playlistChoseList);

        this.playlistCreation = new PlaylistCreation(this, spotifyPlayer, pageId++);
        pages.add(this.playlistCreation);

        this.playlistDeletion = new PlaylistDeletion(this, spotifyPlayer, pageId++);
        pages.add(this.playlistDeletion);

        this.friendAddAFriend = new FriendAddAFriend(this, spotifyPlayer, pageId++);
        pages.add(this.friendAddAFriend);

        this.friendsCommunePlaylists = new FriendsCommunePlaylists(this, spotifyPlayer, pageId++);
        pages.add(this.friendsCommunePlaylists);

        this.friendsDisplayFriends = new FriendsDisplayFriends(this, spotifyPlayer, pageId++);
        pages.add(this.friendsDisplayFriends);

        this.friendsHomePage = new FriendsHomePageTemplate(this, spotifyPlayer, pageId++);
        pages.add(this.friendsHomePage);

        this.friendAddPlaylist = new FriendAddPlaylist(this, spotifyPlayer, pageId++);
        pages.add(this.friendAddPlaylist);

        this.homePage = new HomePage(this, spotifyPlayer, pageId++);
        pages.add(this.homePage);

        this.playlistHomePage = new PlaylistHomePage(this, spotifyPlayer, pageId++);
        pages.add(this.playlistHomePage);

        this.login = new LoginOK(this, spotifyPlayer, pageId++);
        pages.add(this.login);

        this.createAccount = new CreateAccount(this, spotifyPlayer, pageId++);
        pages.add(this.createAccount);

        this.playlistDisplay = new PlaylistDisplay(this, spotifyPlayer, pageId++);
        pages.add(this.playlistDisplay);

        this.search = new Search(this, spotifyPlayer, pageId++);
        pages.add(this.search);

        this.songPlayer = new SongPlayer(this, spotifyPlayer, pageId++);
        pages.add(this.songPlayer);

        this.searchGender = new SearchGender(this, spotifyPlayer, pageId++);
        pages.add(this.searchGender);

        this.friendInformation = new FriendInformation(this, spotifyPlayer, pageId++);
        pages.add(this.friendInformation);

        this.friendRemoveAFriend = new FriendRemoveAFriend(this, spotifyPlayer, pageId++);
        pages.add(this.friendRemoveAFriend);

        this.actionFoundedSongs = new ActionFoundedSongs(this, spotifyPlayer, pageId++);
        pages.add(this.actionFoundedSongs);
    }

    public void startLogin(){
        menuPages.push(login.pageId);
        userService.resetCookie();
        login.displayAllPage();
    }

    public _MenuInterface getPageById(int id) {
       for (_MenuInterface page : pages) {
            if (page.getPageId() == id) {
                return page;
            }
        }
        return null;
    }

    public Stack<Integer> getMenuPages() {
        return menuPages;
    }

    public final void goBack(int pageId) {
        int lastPageId;
        do {
            lastPageId = getMenuPages().pop();
        } while (lastPageId == pageId && !getMenuPages().isEmpty());

        getPageById(lastPageId).displayAllPage();
    }

    public String gotAnInput(String input){
        if (input.equals("0")){
            goBack(this.getMenuPages().peek());
            return "";
        }
        return input;
    }
}
