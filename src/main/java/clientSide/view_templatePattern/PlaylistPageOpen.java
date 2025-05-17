package clientSide.view_templatePattern;

import clientSide.services.ToolBoxView;
import serverSide.entities.Playlist;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

import static clientSide.services.PrintHelper.*;

import java.util.Scanner;

public class PlaylistPageOpen extends TemplateSimplePage {

    Scanner in = new Scanner(System.in);

    public PlaylistPageOpen(PageService pageService, IPlaylistPlayer spotifyPlayer, ToolBoxView toolBoxView, int pageId) {
        super(pageService, spotifyPlayer);
        this.toolBoxView = toolBoxView;
        this.pageId = pageId;
        this.pageTitle = "Playlist Page : ";
        this.pageContent = icon.backHomePageMusicPlayer + icon.lineBreak + icon.separator + icon.lineBreak +
                icon.nbr1() + "Rename Playlist" + icon.lineBreak +
                icon.nbr2() + "Add song" + icon.lineBreak +
                icon.nbr3() + "Remove song" + icon.lineBreak +
                icon.nbr4() + "Reorder song" + icon.lineBreak +
                icon.nbr5() + "Delete the playlist";
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
        printWhite(icon.zeroBack + icon.lineBreak + "Enter the new name of the playlist : ");
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
        printWhite("Enter the number of the song you want to remove : ");

        int playlistId = toolBoxView.getPlaylistServ().getCurrentPlaylistId();
        int songIndex = toolBoxView.getPlaylistServ().takeAndValidateInputSongChoice(playlistId);
        if (songIndex == 0){
            pageService.playlistHomePage.displayAllPage();
        }
        int currentPlaylistId = toolBoxView.getPlaylistServ().getCurrentPlaylistId();
        toolBoxView.getPlaylistServ().deleteSongFromPlaylist(currentPlaylistId, songIndex-1);
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
