package Utils;

import middle.*;
import serverSide.repoBack.*;
import serverSide.repoLocal.*;

import java.util.Scanner;
import java.util.Stack;

public class CompositionRootServerSide {

    Scanner scanner = new Scanner(System.in);
    public final Stack<Integer> menuPagesStack = new Stack<>();

    //Json-Mp3
    StockageService stockageService;

    //Repositories
    final IPlaylistRepository playlistLocalRepository;
    final IUserRepository userLocalRepository;
    final ISongRepository songLocalRepository;
    final IArtistRepository artistLocalRepository;
    final IAudioRepository audioLocalRepository;

    final BackPlaylistRepo backPlaylistRepo;
    final BackUserRepo backUserRepo;
    final BackSongRepo backSongRepo;
    final BackArtistRepo backArtistRepo;
    final BackAudioRepo backAudioRepo;

    public CompositionRootServerSide(){
        //Json-Mp3
        stockageService = new StockageService();

        //Repositories
        playlistLocalRepository = new PlaylistLocalRepository();
        userLocalRepository = new UserLocalRepository();
        artistLocalRepository = new ArtistLocalRepository();
        songLocalRepository = new SongLocalRepository(stockageService, artistLocalRepository);
        audioLocalRepository = new AudioLocalRepository();

        backPlaylistRepo = new BackPlaylistRepo(playlistLocalRepository, userLocalRepository);
        backUserRepo = new BackUserRepo(userLocalRepository);
        backArtistRepo = new BackArtistRepo(artistLocalRepository, userLocalRepository);
        backSongRepo = new BackSongRepo(songLocalRepository, userLocalRepository);
        backAudioRepo = new BackAudioRepo(userLocalRepository);
    }

    public void copyJsons(){
        StockageService stockageService = new StockageService();

        stockageService.copyResourceToWritableLocation("jsons/artist.json", "artist.json");
        stockageService.copyResourceToWritableLocation("jsons/user.json", "user.json");
        stockageService.copyResourceToWritableLocation("jsons/song.json", "song.json");
        stockageService.copyResourceToWritableLocation("jsons/playlist.json", "playlist.json");
    }

    public void copySongs(){
        StockageService stockageService = new StockageService();

        stockageService.copyAllSongsToWritableLocation("songsfiles");
    }
}
