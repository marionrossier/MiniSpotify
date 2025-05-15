package clientSide;

import middle.*;
import clientSide.services.*;
import javazoom.jlgui.basicplayer.BasicPlayer;
import serverSide.StockageService;
import serverSide.repoLocal.*;
import clientSide.player_StatePattern.file_player.MusicPlayer;
import clientSide.player_StatePattern.file_player.IMusicPlayer;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.player_StatePattern.playlist_player.PlaylistPlayer;

import java.util.Scanner;
import java.util.Stack;

public class CompositionRootPattern {

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

    //Services
    final UserService userService;
    final ArtistService artistService;
    final SongService songService;

    final PlaylistFunctionalitiesService playlistFunctionalitiesService;
    final PlaylistReorderSongService playlistReorderSongService;
    final TemporaryPlaylistService temporaryPlaylistService;
    final PlaylistServices playlistServices;

    final PrintService printService;
    final SearchService searchService;
    final PasswordService passwordService;
    final UniqueIdService uniqueIdService;

    final IMusicPlayer musicPlayer;
    final IPlaylistPlayer spotifyPlayer;
    final ToolBoxView toolBoxView;
    final BasicPlayer basicPlayer;


    final PageService pageService;
    final ToolBoxService toolBoxService;

    public CompositionRootPattern (){

        //Json-Mp3
        stockageService = new StockageService();
        basicPlayer = new BasicPlayer();

        //Repositories
        playlistLocalRepository = new PlaylistLocalRepository();
        userLocalRepository = new UserLocalRepository();
        artistLocalRepository = new ArtistLocalRepository();
        songLocalRepository = new SongLocalRepository(stockageService, artistLocalRepository);
        audioLocalRepository = new AudioLocalRepository();

        toolBoxService = new ToolBoxService(playlistLocalRepository, userLocalRepository, songLocalRepository,
                artistLocalRepository, audioLocalRepository);

        //Services
        passwordService = new PasswordService(userLocalRepository);

        userService = new UserService(toolBoxService, passwordService);
        artistService = new ArtistService(toolBoxService);
        songService = new SongService(toolBoxService);

        playlistReorderSongService = new PlaylistReorderSongService(toolBoxService, scanner);
        temporaryPlaylistService = new TemporaryPlaylistService(toolBoxService, userService);

        playlistFunctionalitiesService = new PlaylistFunctionalitiesService(toolBoxService, userService,
                songService);
        playlistServices = new PlaylistServices(toolBoxService, playlistFunctionalitiesService, temporaryPlaylistService);
        printService = new PrintService(songService, artistService, playlistServices, userService);
        searchService = new SearchService(songService, printService);
        uniqueIdService = new UniqueIdService();

        musicPlayer = new MusicPlayer(audioLocalRepository, basicPlayer);
        spotifyPlayer = new PlaylistPlayer(musicPlayer, audioLocalRepository, songService, playlistServices);
        toolBoxView = new ToolBoxView(playlistServices, userService, songService, artistService,
                printService, searchService, passwordService, playlistReorderSongService,
                temporaryPlaylistService, uniqueIdService, passwordService);

        pageService = new PageService(spotifyPlayer, toolBoxView, userService, menuPagesStack);
    }

    public void startApp(){

        this.pageService.startLogin();
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
