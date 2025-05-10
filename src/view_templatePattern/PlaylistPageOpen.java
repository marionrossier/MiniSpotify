package view_templatePattern;

import data.entities.Playlist;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

import java.util.Scanner;

public class PlaylistPageOpen extends _SimplePageTemplate {

    Scanner in = new Scanner(System.in);

    public PlaylistPageOpen(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Playlist Page : ";
        this.pageContent = icon.backHomePageMusicPlayer + icon.lineBreak +
                icon.iconNbr(1) + "Rename Playlist" + icon.lineBreak +
                icon.iconNbr(2) + "Add song" + icon.lineBreak +
                icon.iconNbr(3) + "Remove song" + icon.lineBreak +
                icon.iconNbr(4) + "Reorder song" + icon.iconPremium() + icon.lineBreak +
                icon.iconNbr(5) + "Delete the playlist";
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
        toolbox.getPrintServ().printSongList(playlist.getPlaylistSongsListWithId(), toolbox.getSongServ());
        }
    }

    @Override
    public void button1() {
        System.out.print(icon.goBack + icon.lineBreak + "Enter the new name of the playlist : ");
        String newName = pageService.gotAnInput(in.next());
        int playlistId = toolbox.getPlaylistServ().getCurrentPlaylistId();
        toolbox.getPlaylistServ().renamePlayList(playlistId, newName);
    }

    @Override
    public void button2() {
        pageService.search.displayAllPage();
    }

    @Override
    public void button3() {
        System.out.print("Enter the number of the song you want to remove : ");

        int playlistId = toolbox.getPlaylistServ().getCurrentPlaylistId();
        int songIndex = toolbox.getPlaylistServ().takeAndValidateInputSongChoice(playlistId);
        if (songIndex == 0){
            pageService.playlistHomePage.displayAllPage();
        }
        int currentPlaylistId = toolbox.getPlaylistServ().getCurrentPlaylistId();
        toolbox.getPlaylistServ().deleteSongFromPlaylist(currentPlaylistId, songIndex);
        pageService.playlistPageOpen.displayAllPage();
    }

    @Override
    public void button4() {
        int currentPlaylistId = toolbox.getPlaylistServ().getCurrentPlaylistId();
        toolbox.getPlaylistReorderSongService().reorderSongsInPlaylist(currentPlaylistId, toolbox.getPlaylistServ());
    }

    @Override
    public void button5() {
        pageService.playlistDeletion.displayAllPage();
    }
}
