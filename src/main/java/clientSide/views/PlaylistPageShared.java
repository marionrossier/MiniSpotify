package clientSide.views;

import clientSide.services.PageService;
import clientSide.services.PrintHelper;
import clientSide.services.ToolBoxView;
import common.entities.Playlist;
import clientSide.player.playlist_player.IPlaylistPlayer;

import static clientSide.services.PrintHelper.*;

public class PlaylistPageShared extends TemplateSimplePage {

    public PlaylistPageShared(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Shared Playlist Page : ";
        this.pageContent = PrintHelper.backHomePageMusicPlayer + PrintHelper.lineBreak + PrintHelper.separator + PrintHelper.lineBreak +
                PrintHelper.nbr1 + "Delete the playlist";
    }

    @Override
    public void displaySpecificContent(){
        printLN();
        Playlist playlist = toolBoxView.getPlaylistServ().getPlaylistById(toolBoxView.getPlaylistServ().getCurrentPlaylistId());
        if (playlist == null){
            pageService.playlistHomePage.displayAllPage();
        }
        else {
        printLNBlue("Playlist name : " + playlist.getName());
        printLN();
        toolBoxView.getPrintServ().printSongList(playlist.getPlaylistSongsListWithId());
        }
    }

    @Override
    public void button1() {
        pageService.playlistDeletion.displayAllPage();
    }
}
