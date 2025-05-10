package view_templatePattern;

import data.entities.Playlist;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

import java.util.Scanner;

public class PlaylistPageShared extends _SimplePageTemplate {

    Scanner in = new Scanner(System.in);

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
