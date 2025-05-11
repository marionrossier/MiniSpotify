package clientSide;

import clientSide.services.*;
import serverSide.StockageService;
import serverSide.repositories.*;
import clientSide.player_StatePattern.file_player.MusicPlayer;
import clientSide.player_StatePattern.file_player.IMusicPlayer;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.player_StatePattern.playlist_player.PlaylistPlayer;

public class Spotify {
    public static void startApp(){
        PlaylistLocalRepository playlistLocalRepository = new PlaylistLocalRepository();
        SongLocalRepository songLocalRepository = new SongLocalRepository();
        ArtistLocalRepository artistLocalRepository = new ArtistLocalRepository();
        AudioLocalRepository audioLocalRepository = new AudioLocalRepository();
        UserLocalRepository userLocalRepository = new UserLocalRepository();
        IMusicPlayer musicPlayer = new MusicPlayer(audioLocalRepository);
        SongService songService = new SongService(songLocalRepository);
        ArtistService artistService = new ArtistService(artistLocalRepository);
        UserService userService = new UserService(userLocalRepository);
        PlaylistServices playlistServices = new PlaylistServices(playlistLocalRepository, userLocalRepository,songLocalRepository);
        PrintService printService = new PrintService(songService, artistService, playlistServices, userService);

        IPlaylistPlayer spotifyPlayer = new PlaylistPlayer(musicPlayer, songLocalRepository, playlistLocalRepository,
                artistLocalRepository, audioLocalRepository, printService);

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
        StockageService stockageService = new StockageService();

        stockageService.copyAllSongsToWritableLocation("songsfiles");
    }
}
