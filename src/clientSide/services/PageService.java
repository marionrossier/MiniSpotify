package clientSide.services;

import clientSide.view_templatePattern.*;
import serverSide.entities.PlanEnum;
import serverSide.entities.User;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class PageService {

    ArrayList<_MenuInterface> pages = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private final Stack<Integer> menuPagesStack;

    private final IPlaylistPlayer spotifyPlayer;
    public PlaylistChoseList playlistChoseList;
    public PlaylistCreation playlistCreation;
    public PlaylistDeletion playlistDeletion;
    public FriendAddAFriend friendAddAFriend;
    public FriendsCommunePlaylists friendsCommunePlaylists;
    public FriendsDisplayFriends friendsDisplayFriends;
    public FriendsHomePage friendsHomePage;
    public FriendAddPlaylist friendAddPlaylist;
    public HomePage homePage;
    public PlaylistHomePage playlistHomePage;
    public Login login;
    public CreateAccount createAccount;
    public PlaylistPageOpen playlistPageOpen;
    public Search search;
    public SongPlayer songPlayer;
    public SearchGender searchGender;
    public FriendInformation friendInformation;
    public FriendRemoveAFriend friendRemoveAFriend;
    public ActionFoundedSongs actionFoundedSongs;
    public PlaylistPageShared playlistPageShared;


    private final UserService userService;
    private final ToolBoxView toolBoxView;

    public PageService(IPlaylistPlayer spotifyPlayer,
                       ToolBoxView toolBoxView,
                       UserService userService,
                       Stack<Integer> menuPagesStack) {
        this.spotifyPlayer = spotifyPlayer;
        this.toolBoxView = toolBoxView;
        this.userService = userService;
        this.menuPagesStack = menuPagesStack;
        setUpPages();
    }

    private void setUpPages() {
        int pageId = 1;

        this.playlistChoseList = new PlaylistChoseList(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.playlistChoseList);

        this.playlistCreation = new PlaylistCreation(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.playlistCreation);

        this.playlistDeletion = new PlaylistDeletion(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.playlistDeletion);

        this.friendAddAFriend = new FriendAddAFriend(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.friendAddAFriend);

        this.friendsCommunePlaylists = new FriendsCommunePlaylists(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.friendsCommunePlaylists);

        this.friendsDisplayFriends = new FriendsDisplayFriends(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.friendsDisplayFriends);

        this.friendsHomePage = new FriendsHomePage(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.friendsHomePage);

        this.friendAddPlaylist = new FriendAddPlaylist(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.friendAddPlaylist);

        this.homePage = new HomePage(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.homePage);

        this.playlistHomePage = new PlaylistHomePage(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.playlistHomePage);

        this.login = new Login(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.login);

        this.createAccount = new CreateAccount(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.createAccount);

        this.playlistPageOpen = new PlaylistPageOpen(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.playlistPageOpen);

        this.search = new Search(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.search);

        this.songPlayer = new SongPlayer(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.songPlayer);

        this.searchGender = new SearchGender(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.searchGender);

        this.friendInformation = new FriendInformation(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.friendInformation);

        this.friendRemoveAFriend = new FriendRemoveAFriend(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.friendRemoveAFriend);

        this.actionFoundedSongs = new ActionFoundedSongs(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.actionFoundedSongs);

        this.playlistPageShared = new PlaylistPageShared(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.playlistPageShared);
    }

    public void startLogin(){
        menuPagesStack.push(login.pageId);
        userService.resetCookie();
        login.displayAllPage();
    }

    public String gotAnInput(String input){
        if (input.equals("0")){
            int menuPageId = menuPagesStack.peek();
            goBack(menuPageId);
            return "";
        }
        return input;
    }

    public Stack<Integer> getMenuPages() {
        return menuPagesStack;
    }

    public void addToStack(int pageId) {
        //TODO : compléter la liste des pages sur lesquelles on devrait pas pouvoir faire retour.
        int createAccount = this.createAccount.pageId;
        int actionFoundedSongs = this.actionFoundedSongs.pageId;
        int playlistCreation = this.playlistCreation.pageId;
        int searchGender = this.searchGender.pageId;
        int playlistDeletion = this.playlistDeletion.pageId;
        int [] pageIdNotToAdd = new int[] {createAccount, actionFoundedSongs, playlistCreation, searchGender, playlistDeletion};

        for (int id : pageIdNotToAdd) {
            if (id == pageId) {
                return;
            }
        }
        getMenuPages().push(pageId);
    }

    public void goBack(int pageId) {
        int lastPageId;
        do {
            lastPageId = getMenuPages().pop();
        } while ((lastPageId == pageId || lastPageId != homePage.pageId) && !getMenuPages().isEmpty());

        getPageById(lastPageId).displayAllPage();
    }

    public _MenuInterface getPageById(int id) {
        for (_MenuInterface page : pages) {
            if (page.getPageId() == id) {
                return page;
            }
        }
        return null;
    }

    public void pageIsPremium (boolean isFree){
        if (!isFree){
            int userId = toolBoxView.getUserServ().getCurrentUserId();
            User user = toolBoxView.getUserServ().getUserById(userId);

            if (user.getPlanEnum().equals(PlanEnum.FREE)) {
                System.err.println("Premium Client option only.");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Upgrade to Premium plan now ? YES or NO");
                String input = scanner.nextLine();
                int lastPageId = this.getMenuPages().pop();
                input = input.toLowerCase();

                switch (input) {
                    case "yes":
                        user.setPlanEnum(PlanEnum.PREMIUM);
                        toolBoxView.getUserServ().saveUser(user);
                        break;
                    case "no":
                        getPageById(lastPageId).displayAllPage();
                        break;
                    default:
                        System.err.println("Invalid input.");
                        getPageById(lastPageId).displayAllPage();
                }

            }
        }
    }
}
