package view_templatePattern;

import data.entities.Playlist;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

import java.util.Scanner;

public class PlaylistPage extends _SimplePageTemplate {

    Scanner in = new Scanner(System.in);

    public PlaylistPage(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Playlist Page : ";
        this.pageContent = icon.backHomePageMusicPlayer + icon.lineBreak +
                icon.iconNbr(1) + "Rename Playlist" + icon.lineBreak +
                icon.iconNbr(2) + "Add song" + icon.lineBreak +
                icon.iconNbr(3) + "Remove song" + icon.lineBreak +
                icon.iconNbr(4) + "Reorder song (TODO!!)" + icon.iconPremium() + icon.lineBreak +
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
        System.out.println("Playlist name : " + playlist.getPlaylistName());
        System.out.println();
        toolbox.getPrintServ().printSongList(playlist.getPlaylistSongsListWithId());
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
        int songIndex = in.nextInt()-1;

        int currentPlaylistId = toolbox.getPlaylistServ().getCurrentPlaylistId();
        toolbox.getPlaylistServ().deleteSongFromPlaylist(currentPlaylistId, songIndex);
        pageService.playlistPage.displayAllPage();
    }

    @Override
    public void button4() {
        //TODO : reorder song in playlist
    }

    @Override
    public void button5() {
        pageService.playlistDeletion.displayAllPage();
    }
}
