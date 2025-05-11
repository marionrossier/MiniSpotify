package clientSide.view_templatePattern;

import clientSide.services.ViewToolBox;
import serverSide.entities.Playlist;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

public class PlaylistPageShared extends _SimplePageTemplate {

    public PlaylistPageShared(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
        this.pageId = pageId;
        this.pageTitle = "Shared Playlist Page : ";
        this.pageContent = icon.backHomePageMusicPlayer + icon.lineBreak +
                icon.nbr(1) + "Delete the playlist";
    }

    @Override
    public void displaySpecificContent(){
        System.out.println();
        Playlist playlist = viewToolBox.getPlaylistServ().getPlaylistById(viewToolBox.getPlaylistServ().getCurrentPlaylistId());
        if (playlist == null){
            pageService.playlistHomePage.displayAllPage();
        }
        else {
        System.out.println("Playlist name : " + playlist.getName());
        System.out.println();
        viewToolBox.getPrintServ().printSongList(playlist.getPlaylistSongsListWithId());
        }
    }

    @Override
    public void button1() {
        pageService.playlistDeletion.displayAllPage();
    }
}
