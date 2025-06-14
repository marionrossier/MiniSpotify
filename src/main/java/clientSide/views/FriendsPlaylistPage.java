package clientSide.views;

import clientSide.player.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;
import clientSide.services.PrintHelper;
import clientSide.services.ToolBoxView;

import static clientSide.services.PrintHelper.*;

public class FriendsPlaylistPage extends TemplateSimplePage {
    public FriendsPlaylistPage(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.isFree = false;
        this.pageTitle = "Friend Playlist Page";
        this.pageContent = PrintHelper.backHomePageMusicPlayer + "\n" +  PrintHelper.separator + "\n" +
                PrintHelper.b1 + "Listen to playlist" + "\n"+
                PrintHelper.b2 + "Add playlist to own playlist";
    }

    @Override
    public void displaySpecificContent(){
        int playlistId = toolBoxView.getPlaylistServ().getCurrentFriendPlaylistId();
        printLNBlue("Current playlist : " + toolBoxView.getPlaylistServ().getPlaylistById(playlistId).getName());
        printLNBlue("Song list : ");
        toolBoxView.getPrintServ().printSongList(toolBoxView.getPlaylistServ().getPlaylistById(playlistId).getPlaylistSongsListWithId());
    }

    @Override
    public void button1(){
        int friendPlaylistId = toolBoxView.getPlaylistServ().getCurrentFriendPlaylistId();
        toolBoxView.getPlaylistServ().setCurrentPlaylistId(friendPlaylistId);
        int currentSongId = toolBoxView.getPlaylistServ().getPlaylistById(friendPlaylistId).getPlaylistSongsListWithId().getFirst();
        toolBoxView.getSongServ().setCurrentSongId(currentSongId);
        pageService.songPlayer.displayAllPage();
    }

    @Override
    public void button2(){
        int friendPlaylistId = toolBoxView.getPlaylistServ().getCurrentFriendPlaylistId();
        toolBoxView.getUserServ().addOnePlaylistToCurrentUser(friendPlaylistId);
        pageService.friendPlaylists.displayAllPage();
    }
}
