package clientSide.views;

import clientSide.services.PrintHelper;
import clientSide.services.ToolBoxView;
import common.entities.Playlist;
import clientSide.player.playlist_player.IPlaylistPlayer;
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
        this.pageContent = PrintHelper.backHomePageMusicPlayer + PrintHelper.lineBreak + PrintHelper.separator + PrintHelper.lineBreak +
                PrintHelper.nbr1 + "Rename Playlist" + PrintHelper.lineBreak +
                PrintHelper.nbr2 + "Add song" + PrintHelper.lineBreak +
                PrintHelper.nbr3 + "Remove song" + PrintHelper.lineBreak +
                PrintHelper.nbr4 + "Reorder song" + PrintHelper.lineBreak +
                PrintHelper.nbr5 + "Delete the playlist";
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
        printWhite(PrintHelper.zeroBack + PrintHelper.lineBreak + "Enter the new name of the playlist : ");
        String newName = pageService.gotAnInputGoBackIf0(in.nextLine());
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
        printWhite("Enter the number of the song you want to remove or \"0\" to go back: ");

        int playlistId = toolBoxView.getPlaylistServ().getCurrentPlaylistId();
        int playlistSize = toolBoxView.getPlaylistServ().getPlaylistById(playlistId).getPlaylistSongsListWithId().size();
        int songIndex = toolBoxView.getPlaylistServ().takeAndValidateInputChoice(playlistSize, pageService);

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
