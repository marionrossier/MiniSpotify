package clientSide.view_templatePattern;

import clientSide.services.ViewToolBox;
import serverSide.entities.Playlist;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.services.PageService;

import java.util.Scanner;

public class PlaylistPageOpen extends _SimplePageTemplate {

    Scanner in = new Scanner(System.in);

    public PlaylistPageOpen(PageService pageService, IPlaylistPlayer spotifyPlayer, ViewToolBox viewToolBox, int pageId) {
        super(pageService, spotifyPlayer);
        this.viewToolBox = viewToolBox;
        this.pageId = pageId;
        this.pageTitle = "Playlist Page : ";
        this.pageContent = icon.backHomePageMusicPlayer + icon.lineBreak +
                icon.nbr(1) + "Rename Playlist" + icon.lineBreak +
                icon.nbr(2) + "Add song" + icon.lineBreak +
                icon.nbr(3) + "Remove song" + icon.lineBreak +
                icon.nbr(4) + "Reorder song" + icon.premium() + icon.lineBreak +
                icon.nbr(5) + "Delete the playlist";
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
        System.out.print(icon.zeroBack + icon.lineBreak + "Enter the new name of the playlist : ");
        String newName = pageService.gotAnInput(in.next());
        int playlistId = viewToolBox.getPlaylistServ().getCurrentPlaylistId();
        viewToolBox.getPlaylistServ().renamePlayList(playlistId, newName);
        pageService.playlistPageOpen.displayAllPage();
    }

    @Override
    public void button2() {
        pageService.search.displayAllPage();
    }

    @Override
    public void button3() {
        System.out.print("Enter the number of the song you want to remove : ");

        int playlistId = viewToolBox.getPlaylistServ().getCurrentPlaylistId();
        int songIndex = viewToolBox.getPlaylistServ().takeAndValidateInputSongChoice(playlistId);
        if (songIndex == 0){
            pageService.playlistHomePage.displayAllPage();
        }
        int currentPlaylistId = viewToolBox.getPlaylistServ().getCurrentPlaylistId();
        viewToolBox.getPlaylistServ().deleteSongFromPlaylist(currentPlaylistId, songIndex);
        pageService.playlistPageOpen.displayAllPage();
    }

    @Override
    public void button4() {
        int currentPlaylistId = viewToolBox.getPlaylistServ().getCurrentPlaylistId();
        viewToolBox.getPlaylistReorderSongServ().reorderSongsInPlaylist(currentPlaylistId, viewToolBox.getPlaylistServ());
        pageService.playlistPageOpen.displayAllPage();
    }

    @Override
    public void button5() {
        pageService.playlistDeletion.displayAllPage();
    }
}
