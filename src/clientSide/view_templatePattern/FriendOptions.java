package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;
import serverSide.entities.User;

import java.util.List;

public class FriendOptions extends _SimplePageTemplate {
    public FriendOptions(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Manage your friend Page";
        this.pageContent = icon.zeroBack + icon.lineBreak +
                icon.nbr(1) + "Friend Playlist.s" + icon.lineBreak +
                icon.nbr(2) + "Delete friend from your list";
    }

    @Override
    public void displaySpecificContent(){
        int friendId = toolBoxView.getUserServ().getCurrentFriendId();
        System.out.println("Current friend : " + toolBoxView.getUserServ().getUserById(friendId).getPseudonym());
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
            System.err.println("No corresponding friend found.");
        }
        else {
            toolBoxView.getUserServ().deleteFriend(friendID);
            System.out.println("Friend remove from friend list.");
        }
        pageService.friendsHomePage.displayAllPage();
    }
}
