package clientSide;

import serverSide.StockageService;
import serverSide.repositories.ArtistRepository;
import serverSide.repositories.PlaylistRepository;
import serverSide.repositories.SongRepository;
import clientSide.player_StatePattern.file_player.MusicPlayer;
import clientSide.player_StatePattern.file_player.IMusicPlayer;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.player_StatePattern.playlist_player.PlaylistPlayer;
import clientSide.services.PageService;

public class Spotify {
    public static void startApp(){
        PlaylistRepository playlistRepository = new PlaylistRepository();
        SongRepository songRepository = new SongRepository();
        ArtistRepository artistRepository = new ArtistRepository();
        IMusicPlayer musicPlayer = new MusicPlayer();

        IPlaylistPlayer spotifyPlayer = new PlaylistPlayer(musicPlayer, songRepository, playlistRepository, artistRepository);

        PageService pageService = new PageService(spotifyPlayer);

        pageService.startLogin();
    }

    public static void copyJsons(){
        StockageService stockageService = new StockageService();

        String directoryPath = stockageService.createWritableDirectory("jsons");

        stockageService.copyResourceToWritableLocation("jsons/artist.json", "artist.json");
        stockageService.copyResourceToWritableLocation("jsons/user.json", "user.json");
        stockageService.copyResourceToWritableLocation("jsons/song.json", "song.json");
        stockageService.copyResourceToWritableLocation("jsons/playlist.json", "playlist.json");
    }

    public static void copySongs(){

    }
}
