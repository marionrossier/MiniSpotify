package view_templatePattern;

import data.jsons.PlaylistRepository;
import data.jsons.SongRepository;
import player_commandPattern.SpotifyPlayer;
import services.Cookies_SingeltonPattern;
import services.PlaylistServices;
import services.SongService;

import java.util.List;
import java.util.Scanner;

public class PlaylistDisplay extends AbstractMenuPage {

    SongService songService = new SongService();
    Scanner in = new Scanner(System.in);
    PlaylistRepository playlistRepository = new PlaylistRepository();
    PlaylistServices playlistServices = new PlaylistServices();
    SongRepository songRepository = new SongRepository();

    public PlaylistDisplay(SpotifyPageFactory spotifyPageFactory, SpotifyPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Playlist Page : ";
        this.pageContent = backLineWith0 + lineBreak +
                nb1 + "Play the playlist" + lineBreak +
                nb2 + "Rename Playlist" + lineBreak +
                nb3 + "Add song" + lineBreak +
                nb4 + "Remove song" + lineBreak +
                nb5 + "Reorder song" + lineBreak +
                nb6 + "Delete the playlist";
    }

    @Override
    void displaySpecificContent(){
        System.out.println();
        System.out.println("Playlist name : " + playlistRepository
                .getPlaylistById(Cookies_SingeltonPattern.getInstance().getCurrentPlaylistId())
                .getPlaylistName());
        System.out.println("Playlist songs : ");

        songService.printSongList(playlistRepository
                .getPlaylistById(Cookies_SingeltonPattern.getInstance().getCurrentPlaylistId())
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
        playlistServices.editPlayListName();
    }

    @Override
    void button3() {
        //TODO : voir si besoin d'en faire une méga méthode pour search.. si réutiliser
        System.out.print(icons.iconSearch() + "Enter the title of the song : ");
        String songTitle = in.nextLine();

        List<Integer> foundedSongs = songService.searchSongByTitle(songTitle);
        songService.printSongFound(foundedSongs, songTitle);
        List<Integer> chosenSongs = songService.chooseFoundedSongs(foundedSongs);

        Cookies_SingeltonPattern.setTemporaryPlaylist(chosenSongs);
        spotifyPageFactory.actionFoundedSongs.templateMethode();
    }

    @Override
    void button4() {
        System.out.print("Enter the number of the song you want to remove : ");
        int songIndex = in.nextInt()-1;

        playlistServices.removeSongFromPlaylist(songIndex);
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
