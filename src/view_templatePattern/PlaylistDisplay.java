package view_templatePattern;

import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PageService;

import java.util.LinkedList;
import java.util.Scanner;

public class PlaylistDisplay extends _SimplePageTemplate {

    Scanner in = new Scanner(System.in);

    public PlaylistDisplay(PageService pageManager, IPlaylistPlayer spotifyPlayer, int pageId) {
        super(pageManager, spotifyPlayer);
        this.pageId = pageId;
        this.pageTitle = "Playlist Page : ";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                icon.iconNbr(1) + "Play the playlist" + icon.lineBreak +
                icon.iconNbr(2) + "Rename Playlist" + icon.lineBreak +
                icon.iconNbr(3) + "Add song" + icon.lineBreak +
                icon.iconNbr(4) + "Remove song" + icon.lineBreak +
                icon.iconNbr(5) + "Reorder song" + icon.lineBreak +
                icon.iconNbr(6) + "Delete the playlist";
    }

    @Override
    public void displaySpecificContent(){
        System.out.println();
        System.out.println("Playlist name : " + toolbox.getPlaylistRepo()
                .getPlaylistById(toolbox.getPlaylistServ().getCurrentPlaylistId())
                .getPlaylistName());
        System.out.println("Playlist songs : ");

        toolbox.getPrintServ().printSongList(toolbox.getPlaylistRepo()
                .getPlaylistById(toolbox.getPlaylistServ().getCurrentPlaylistId())
                .getPlaylistSongsListWithId());
    }

    @Override
    public void button1() {
        pageService.songPlayer.displayAllPage();
    }

    @Override
    public void button2() {
        System.out.print("Enter the new name of the playlist : ");
        String newName = in.next();
        int playlistId = toolbox.getPlaylistServ().getCurrentPlaylistId();
        toolbox.getPlaylistServ().renamePlayList(playlistId, newName);
    }

    @Override
    public void button3() {
        //TODO : Mettre dans une classe SearchService pour toute la partie logique
        System.out.println();
        System.out.print(icon.iconSearch() + "Enter the title of the song : ");
        String songTitle = in.nextLine();

        LinkedList<Integer> foundedSongs = toolbox.getSongServ().searchSongByTitle(songTitle);
        if (foundedSongs.isEmpty()){
            goBack();
        }
        toolbox.getPrintServ().printSongFound(foundedSongs, songTitle);
        LinkedList<Integer> chosenSongs = toolbox.getSongServ().chooseFoundedSongs(foundedSongs);

        toolbox.getPlaylistServ().createTemporaryPlaylistAndInitCookies(chosenSongs);

        pageService.actionFoundedSongs.displayAllPage();
    }

    @Override
    public void button4() {
        System.out.print("Enter the number of the song you want to remove : ");
        int songIndex = in.nextInt()-1;

        int currentPlaylistId = toolbox.getPlaylistServ().getCurrentPlaylistId();
        toolbox.getPlaylistServ().removeSongFromPlaylist(currentPlaylistId, songIndex);
        pageService.playlistDisplay.displayAllPage();
    }

    @Override
    public void button5() {
        //TODO : reorder song in playlist
    }

    @Override
    public void button6() {
        pageService.playlistDeletion.displayAllPage();
    }
}
