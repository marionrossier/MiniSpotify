package view_templatePattern;

import data.entities.Playlist;
import data.jsons.PlaylistRepository;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.Cookies_SingletonPattern;
import services.PlaylistServices;
import services.PrintService;
import services.SongService;

import java.util.LinkedList;
import java.util.Scanner;

public class PlaylistDisplay extends AbstractMenuPage {

    SongService songService = new SongService();
    PrintService printService = new PrintService();
    Scanner in = new Scanner(System.in);
    PlaylistRepository playlistRepository = new PlaylistRepository();
    PlaylistServices playlistServices = new PlaylistServices(playlistRepository);

    public PlaylistDisplay(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
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
    void displaySpecificContent(){
        System.out.println();
        System.out.println("Playlist name : " + playlistRepository
                .getPlaylistById(Cookies_SingletonPattern.getInstance().getCurrentPlaylistId())
                .getPlaylistName());
        System.out.println("Playlist songs : ");

        printService.printSongList(playlistRepository
                .getPlaylistById(Cookies_SingletonPattern.getInstance().getCurrentPlaylistId())
                .getPlaylistSongsListWithId());
    }

    @Override
    void button0() {
        spotifyPageFactory.playlistChoseList.templateMethode();
    }

    @Override
    void button1() {
        spotifyPageFactory.songPlayer.templateMethode();
    }

    @Override
    void button2() {
        System.out.print("Enter the new name of the playlist : ");
        String newName = in.next();
        int playlistId = Cookies_SingletonPattern.getInstance().getCurrentPlaylistId();
        playlistServices.renamePlayList(playlistId, newName);
    }

    @Override
    void button3() {
        //TODO : Mettre dans une classe SearchService pour toute la partie logique
        System.out.println();
        System.out.print(icon.iconSearch() + "Enter the title of the song : ");
        String songTitle = in.nextLine();

        LinkedList<Integer> foundedSongs = songService.searchSongByTitle(songTitle);
        printService.printSongFound(foundedSongs, songTitle);
        LinkedList<Integer> chosenSongs = songService.chooseFoundedSongs(foundedSongs);

        playlistServices.createTemporaryPlaylistAndInitCookies(chosenSongs);

        spotifyPageFactory.actionFoundedSongs.templateMethode();
    }

    @Override
    void button4() {
        System.out.print("Enter the number of the song you want to remove : ");
        int songIndex = in.nextInt()-1;

        int currentPlaylistId = Cookies_SingletonPattern.getInstance().getCurrentPlaylistId();
        playlistServices.removeSongFromPlaylist(currentPlaylistId, songIndex);
        spotifyPageFactory.playlistDisplay.templateMethode();
    }

    @Override
    void button5() {
        //TODO : reorder song in playlist
    }

    @Override
    void button6() {
        spotifyPageFactory.playlistDeletion.templateMethode();
    }
}
