package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;
import serverSide.entities.User;

import java.util.List;

import static clientSide.services.PrintHelper.*;

public class FriendPlaylists extends TemplateSimplePage {
    public FriendPlaylists(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Friend playlist Page";
        this.pageContent = icon.zeroBack + icon.separator + icon.lineBreak;
    }

    @Override
    public void displaySpecificContent (){
        int friendId = toolBoxView.getUserServ().getCurrentFriendId();
        printLNBlue("Public playlist.s of : " + toolBoxView.getUserServ().getUserById(friendId).getPseudonym());
        toolBoxView.getPrintServ().printUserPublicPlaylists(friendId);
    }

    @Override
    public void validateInput() {
        User friend  = toolBoxView.getUserServ().getUserById(toolBoxView.getUserServ().getCurrentFriendId());
        String playlistIndex = pageService.gotAnInput(scanner.nextLine());

        List<Integer> publicPlaylists = toolBoxView.getPlaylistServ().getUserPublicPlaylists(friend);
        int playlistId = publicPlaylists.get(Integer.parseInt(playlistIndex));

        toolBoxView.getPlaylistServ().setCurrentFriendPlaylistId(playlistId);

        pageService.friendsPlaylistPage.displayAllPage();
    }
}