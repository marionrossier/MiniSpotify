package view_templatePattern;

import data.entities.Artist;
import data.jsons.ArtistRepository;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import services.PrintService;
import services.SongService;

import java.util.List;

public class Search extends AbstractMenuPage {

    SongService songService = new SongService();
    PrintService printService = new PrintService();

    public Search(SpotifyPageFactory spotifyPageFactory, IPlaylistPlayer spotifyPlayer) {
        super(spotifyPageFactory, spotifyPlayer);
        this.pageTitle = "Search Page";
        this.pageContent = icon.iconNbr(0) + icon.iconBack() + icon.lineBreak +
                icon.iconNbr(1) + "Search a song" + icon.lineBreak +
                icon.iconNbr(2) + "Search an artist" + icon.lineBreak +
                icon.iconNbr(3) + "Search a song gender";
    }

    @Override
    void button1() {
        System.out.print(icon.iconSearch() + "Enter the title of the song : ");
        String songTitle = in.nextLine();

        List<Integer> foundedSongs = songService.searchSongByTitle(songTitle);
        printService.printSongFound(foundedSongs, songTitle);

        //TODO : terminer
//        List<Integer> chosenSongs = songService.chooseFoundedSongs(foundedSongs);
//
//        Cookies_SingeltonPattern.setTemporaryPlaylist(chosenSongs);
//        spotifyPageFactory.actionFoundedSongs.templateMethode();
    }

    @Override
    void button2() {
        System.out.println(icon.iconSearch() + "Search Artist Page");
        System.out.print(icon.lineBreak + "Enter the name of the artist : ");
        String artistName = in.nextLine();
        // TODO : idem que Song, chercher via le ArtistRep ou il faut créer une méthode
        //  "getArtistByName(String name) return liste" des correspondances

        ArtistRepository artistRepository = new ArtistRepository();
        Artist searchedArtist = artistRepository.getArtistByName(artistName); //Pas juste à 100%
    }

    @Override
    void button3() {
        spotifyPageFactory.searchGender.templateMethode();
    }

}
