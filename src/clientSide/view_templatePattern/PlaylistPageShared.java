package clientSide.view_templatePattern;

import serverSide.entities.Playlist;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

public class PlaylistPageShared extends _SimplePageTemplate {

    public PlaylistPageShared(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Shared Playlist Page : ";
        this.pageContent = icon.backHomePageMusicPlayer + icon.lineBreak +
                icon.iconNbr(1) + "Delete the playlist";
    }

    @Override
    public void displaySpecificContent(){
        System.out.println();
        Playlist playlist = toolbox.getPlaylistServ().getPlaylistById(toolbox.getPlaylistServ().getCurrentPlaylistId());
        if (playlist == null){
            pageService.playlistHomePage.displayAllPage();
        }
        else {
        System.out.println("Playlist name : " + playlist.getName());
        System.out.println();
        toolbox.getPrintServ().printSongList(playlist.getPlaylistSongsListWithId(), toolbox.getSearchService());
        }
    }

    @Override
    public void button1() {
        pageService.playlistDeletion.displayAllPage();
    }
}
