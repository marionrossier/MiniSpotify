package clientSide.services;

import clientSide.views.*;
import common.entities.PlanEnum;
import common.entities.User;
import clientSide.player.playlist_player.*;

import java.util.*;

import static clientSide.services.PrintHelper.*;

public class PageService {

    ArrayList<InterfaceMenu> pages = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private final Stack<Integer> menuPagesStack;

    private final IPlaylistPlayer spotifyPlayer;
    public Login login;
    public CreateAccount createAccount;
    public HomePage homePage;
    public PlaylistHomePage playlistHomePage;
    public PlaylistChoseList playlistChoseList;
    public PlaylistPageShared playlistPageShared;
    public PlaylistPageOpen playlistPageOpen;
    public PlaylistCreation playlistCreation;
    public PlaylistDeletion playlistDeletion;
    public Search search;
    public ActionFoundedSongs actionFoundedSongs;
    public SearchGender searchGender;
    public FriendsPlaylistPage friendsPlaylistPage;
    public FriendsDisplayFriends friendsDisplayFriends;
    public FriendsHomePage friendsHomePage;
    public FriendSearch friendSearch;
    public FriendPlaylists friendPlaylists;
    public FriendOptions friendOptions;
    public SongPlayer songPlayer;


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

        this.friendsPlaylistPage = new FriendsPlaylistPage(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.friendsPlaylistPage);

        this.friendsDisplayFriends = new FriendsDisplayFriends(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.friendsDisplayFriends);

        this.friendsHomePage = new FriendsHomePage(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.friendsHomePage);

        this.friendSearch = new FriendSearch(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.friendSearch);

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

        this.friendPlaylists = new FriendPlaylists(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.friendPlaylists);

        this.friendOptions = new FriendOptions(this, spotifyPlayer, toolBoxView, pageId++);
        pages.add(this.friendOptions);

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

    public String gotAnInputGoBackIf0(String input){
        if (input.equals("0")){
            int menuPageId = menuPagesStack.peek();
            goBack(menuPageId);
            return "";
        }
        return input;
    }

    public int tryParseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public Stack<Integer> getMenuPages() {
        return menuPagesStack;
    }

    public void addToStack(int pageId) {
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
        } while (pageId == lastPageId && pageId != homePage.pageId && !getMenuPages().isEmpty());

        getPageById(lastPageId).displayAllPage();
    }

    public InterfaceMenu getPageById(int id) {
        for (InterfaceMenu page : pages) {
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
                printLNInfo("Premium Client option only.");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                printLNInfo("Upgrade to Premium plan now ? YES or NO");
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
                        printInvalidInput();
                        getPageById(lastPageId).displayAllPage();
                }
            }
        }
    }
}
