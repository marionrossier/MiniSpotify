package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;
import common.entities.User;

import java.util.List;

import static clientSide.services.PrintHelper.*;

public class FriendOptions extends TemplateSimplePage {
    public FriendOptions(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Manage your friend Page";
        this.pageContent = icon.zeroBack + icon.lineBreak + icon.separator + icon.lineBreak +
                icon.nbr1() + "Friend Playlist.s" + icon.lineBreak +
                icon.nbr2() + "Delete friend from your list";
    }

    @Override
    public void displaySpecificContent(){
        int friendId = toolBoxView.getUserServ().getCurrentFriendId();
        printLNBlue("Current friend : " + toolBoxView.getUserServ().getUserById(friendId).getPseudonym());
    }

    @Override
    public void button1(){
        pageService.friendPlaylists.displayAllPage();
    }
    @Override
    public void button2(){
        User user = toolBoxView.getUserServ().getUserById(toolBoxView.getUserServ().getCurrentUserId());
        int friendID = toolBoxView.getUserServ().getCurrentFriendId();
        List<Integer> friends = user.getFriends();

        int friendIndex = -1;
        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i) == friendID) {
                friendIndex = i;
                break;
            }
        }
        if (friendIndex == -1){
            printLNInfo("No corresponding friend found.");
        }
        else {
            toolBoxView.getUserServ().deleteFriend(friendID);
            printLNGreen("Friend remove from friend list.");
        }
        pageService.friendsHomePage.displayAllPage();
    }
}
