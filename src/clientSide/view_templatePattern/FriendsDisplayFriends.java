package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;
import serverSide.entities.User;

import java.util.List;

public class FriendsDisplayFriends extends _SimplePageTemplate {
    public FriendsDisplayFriends(PageService pageService, IPlaylistPlayer spotifyPlayer,
                                 ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Display actual Friends Page";
        this.pageContent = icon.zeroBack;
    }

    @Override
    public void displaySpecificContent (){
        System.out.println( "Enter your friend number to see future options.");
        int userId = toolBoxView.getUserServ().getCurrentUserId();
        boolean friendsExists = toolBoxView.getPrintServ().printUserFriends(userId);

        if (!friendsExists){
            pageService.friendsHomePage.displayAllPage();
        }
    }

    @Override
    public void validateInput() {
        User user = toolBoxView.getUserServ().getUserById(toolBoxView.getUserServ().getCurrentUserId());
        List<Integer> friends = user.getFriends();

        String friendIndex = pageService.gotAnInput(scanner.nextLine());

        while(Integer.parseInt(friendIndex)>friends.size()){
            System.out.print(ToolBoxView.PRINT_RED + "Invalide input. Please try again.");
            friendIndex = pageService.gotAnInput(scanner.nextLine());
            displayInput();
        }
        int friendId = friends.get(Integer.parseInt(friendIndex)-1);

        toolBoxView.getUserServ().setCurrentFriendId(friendId);

        pageService.friendOptions.displayAllPage();
    }
}