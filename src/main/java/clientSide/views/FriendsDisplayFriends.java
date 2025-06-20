package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.PrintHelper;
import clientSide.services.ToolBoxView;
import common.entities.User;

import static clientSide.services.PrintHelper.*;

import java.util.List;

public class FriendsDisplayFriends extends TemplateSimplePage {
    public FriendsDisplayFriends(PageService pageService, IPlaylistPlayer spotifyPlayer,
                                 ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Display actual Friends Page";
        this.pageContent = PrintHelper.zeroBack + "\n" + PrintHelper.separator;
    }

    @Override
    public void displaySpecificContent (){
        printLNWhite( "Enter your friend number to see future options.");
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

        String friendIndex = pageService.gotAnInputGoBackIf0(scanner.nextLine());

        while(Integer.parseInt(friendIndex)>friends.size()){
            printInvalidInputTryAgain();
            printYourInput();
            friendIndex = pageService.gotAnInputGoBackIf0(scanner.nextLine());
        }
        int friendId = friends.get(Integer.parseInt(friendIndex)-1);

        toolBoxView.getUserServ().setCurrentFriendId(friendId);

        pageService.friendOptions.displayAllPage();
    }
}