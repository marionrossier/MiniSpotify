package clientSide.view_templatePattern;

import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.ToolBoxView;

import static clientSide.services.PrintHelper.*;

public class FriendsPlaylistPage extends TemplateSimplePage {
    public FriendsPlaylistPage(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Friend Playlist Page";
        this.pageContent = icon.backHomePageMusicPlayer + icon.lineBreak +
                icon.nbr1() + "Listen to playlist" + icon.lineBreak+
                icon.nbr2() + "Add playlist to own playlist";
    }

    @Override
    public void displaySpecificContent(){
        int playlistId = toolBoxView.getPlaylistServ().getCurrentFriendPlaylistId();
        printLNBlue("Current playlist : " + toolBoxView.getPlaylistServ().getPlaylistById(playlistId).getName());
        toolBoxView.getPrintServ().printSongList(toolBoxView.getPlaylistServ().getPlaylistById(playlistId).getPlaylistSongsListWithId());
    }

    @Override
    public void button1(){
        int friendPlaylistId = toolBoxView.getPlaylistServ().getCurrentFriendPlaylistId();
        toolBoxView.getPlaylistServ().setCurrentPlaylistId(friendPlaylistId);
        toolBoxView.getPlaylistServ().setCurrentPlaylistId(friendPlaylistId);
        int currentSongId = toolBoxView.getPlaylistServ().getPlaylistById(friendPlaylistId).getPlaylistSongsListWithId().getFirst();
        toolBoxView.getSongServ().setCurrentSongId(currentSongId);
        pageService.songPlayer.displayAllPage();
    }

    @Override
    public void button2(){
        int friendPlaylistId = toolBoxView.getPlaylistServ().getCurrentFriendPlaylistId();
        toolBoxView.getUserServ().addOnePlaylistToCurrentUser(friendPlaylistId);
        printLNGreen("Playlist has been added.");
        pageService.friendPlaylists.displayAllPage();
    }
}
