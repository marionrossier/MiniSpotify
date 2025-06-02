package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.PrintHelper;
import clientSide.services.ToolBoxView;
import common.entities.User;

import java.util.List;

import static clientSide.services.PrintHelper.*;

public class FriendPlaylists extends TemplateSimplePage {
    public FriendPlaylists(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Friend playlist Page";
        this.pageContent = PrintHelper.zeroBack + PrintHelper.separator + PrintHelper.lineBreak;
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
        String playlistIndex = pageService.gotAnInputGoBackIf0(scanner.nextLine());

        List<Integer> publicPlaylists = toolBoxView.getPlaylistServ().getUserPublicPlaylists(friend);
        int playlistId = publicPlaylists.get(Integer.parseInt(playlistIndex));

        toolBoxView.getPlaylistServ().setCurrentFriendPlaylistId(playlistId);

        pageService.friendsPlaylistPage.displayAllPage();
    }
}