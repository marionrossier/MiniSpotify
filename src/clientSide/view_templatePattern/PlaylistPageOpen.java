package clientSide.view_templatePattern;

import clientSide.services.ToolBoxView;
import serverSide.entities.Playlist;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

import java.util.Scanner;

public class PlaylistPageOpen extends _SimplePageTemplate {

    Scanner in = new Scanner(System.in);

    public PlaylistPageOpen(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Playlist Page : ";
        this.pageContent = icon.backHomePageMusicPlayer + icon.lineBreak +
                icon.nbr(1) + "Rename Playlist" + icon.lineBreak +
                icon.nbr(2) + "Add song" + icon.lineBreak +
                icon.nbr(3) + "Remove song" + icon.lineBreak +
                icon.nbr(4) + "Reorder song" + icon.lineBreak +
                icon.nbr(5) + "Delete the playlist";
    }

    @Override
    public void displaySpecificContent(){
        System.out.println();
        Playlist playlist = toolBoxView.getPlaylistServ().getPlaylistById(toolBoxView.getPlaylistServ().getCurrentPlaylistId());
        if (playlist == null){
            pageService.playlistHomePage.displayAllPage();
        }
        else {
        System.out.println("Playlist name : " + playlist.getName());
        System.out.println();
        toolBoxView.getPrintServ().printSongList(playlist.getPlaylistSongsListWithId());
        }
    }

    @Override
    public void button1() {
        System.out.print(icon.zeroBack + icon.lineBreak + "Enter the new name of the playlist : ");
        String newName = pageService.gotAnInput(in.nextLine());
        int playlistId = toolBoxView.getPlaylistServ().getCurrentPlaylistId();
        toolBoxView.getPlaylistServ().renamePlayList(playlistId, newName);
        pageService.playlistPageOpen.displayAllPage();
    }

    @Override
    public void button2() {
        pageService.search.displayAllPage();
    }

    @Override
    public void button3() {
        System.out.print("Enter the number of the song you want to remove : ");

        int playlistId = toolBoxView.getPlaylistServ().getCurrentPlaylistId();
        int songIndex = toolBoxView.getPlaylistServ().takeAndValidateInputSongChoice(playlistId);
        if (songIndex == 0){
            pageService.playlistHomePage.displayAllPage();
        }
        int currentPlaylistId = toolBoxView.getPlaylistServ().getCurrentPlaylistId();
        toolBoxView.getPlaylistServ().deleteSongFromPlaylist(currentPlaylistId, songIndex);
        pageService.playlistPageOpen.displayAllPage();
    }

    @Override
    public void button4() {
        int currentPlaylistId = toolBoxView.getPlaylistServ().getCurrentPlaylistId();
        toolBoxView.getPlaylistReorderSongServ().reorderSongsInPlaylist(currentPlaylistId, toolBoxView.getPlaylistServ());
        pageService.playlistPageOpen.displayAllPage();
    }

    @Override
    public void button5() {
        pageService.playlistDeletion.displayAllPage();
    }
}
