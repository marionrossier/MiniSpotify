package clientSide.view_templatePattern;

import clientSide.services.ToolBoxView;
import common.entities.Playlist;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

import static clientSide.services.PrintHelper.*;

public class PlaylistPageShared extends TemplateSimplePage {

    public PlaylistPageShared(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Shared Playlist Page : ";
        this.pageContent = icon.backHomePageMusicPlayer + icon.lineBreak + icon.separator + icon.lineBreak +
                icon.nbr1() + "Delete the playlist";
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
