package utilsAndFakes;

import Utils.StockageService;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.player_StatePattern.playlist_player.PlaylistPlayer;
import clientSide.repoFront.*;
import clientSide.services.*;
import clientSide.socket.SocketClient;
import middle.*;
import serverSide.entities.PlanEnum;
import serverSide.entities.User;
import serverSide.repoBack.*;
import serverSide.repoLocal.*;
import serverSide.socket.AudioSocketServer;
import serverSide.socket.SocketServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.Stack;

public class Initializer {

    Scanner scanner = new Scanner(System.in);

    public Thread serverThread;

    public File tempPlaylistsFile;
    public File tempSongsFile;
    public File tempUsersFile;
    public File tempArtistFile;

    public IPlaylistRepository playlistLocalRepository;
    public ISongRepository songLocalRepository;
    public IUserRepository userLocalRepository;
    public IAudioRepository audioLocalRepository;
    public IArtistRepository artistLocalRepository;

    public ToolBoxService toolBoxService;
    public PlaylistServices playlistService;
    protected StockageService stockageService;
    public PlaylistFunctionalitiesService playlistFunctionalitiesService;
    public TemporaryPlaylistService temporaryPlaylistService;
    public UserService userService;
    public PasswordService passwordService;
    protected ArtistService artistService;
    protected PrintService printService;
    protected SearchService searchService;
    protected PlaylistReorderSongService playlistReorderSongService;
    protected UniqueIdService uniqueIdService;
    public SongService songService;

    protected ToolBoxView toolBoxView;

    public IPlaylistPlayer playlistPlayer;
    public FakeMusicPlayer fakeMusicPlayer;

    public PageService pageService;

    protected Stack<Integer> menuPagesStack;

    //SOCKETS
    protected SocketClient socketClient;

    public final IUserRepository frontUserRepo;
    public final IPlaylistRepository frontPlaylistRepo;
    public final ISongRepository frontSongRepo;
    public final IArtistRepository frontArtistRepo;
    public final IAudioRepository frontAudioRepo;

    public final BackAudioRepo backAudioRepo;
    public final BackPlaylistRepo backPlaylistRepo;
    public final BackUserRepo backUserRepo;
    public final BackSongRepo backSongRepo;
    public final BackArtistRepo backArtistRepo;

    public final AudioSocketServer audioSocketServer;
    public final SocketServer socketServer;

    public Initializer() throws IOException {

        menuPagesStack = new Stack<>();

        tempPlaylistsFile = Files.createTempFile("playlist", ".json").toFile();
        playlistLocalRepository = new PlaylistLocalRepository(tempPlaylistsFile.getAbsolutePath());

        tempArtistFile = Files.createTempFile("artist", ".json").toFile();
        artistLocalRepository = new ArtistLocalRepository(tempPlaylistsFile.getAbsolutePath());

        tempUsersFile = Files.createTempFile("user", ".json").toFile();
        userLocalRepository = new UserLocalRepository(tempUsersFile.getAbsolutePath());

        stockageService = new StockageService();
        tempSongsFile = Files.createTempFile("song", ".json").toFile();
        songLocalRepository = new SongLocalRepository(tempSongsFile.getAbsolutePath(),
                stockageService, artistLocalRepository);

        audioLocalRepository = new AudioLocalRepository();
        passwordService = new PasswordService(userLocalRepository);
        toolBoxService = new ToolBoxService(playlistLocalRepository, userLocalRepository, songLocalRepository,
                artistLocalRepository, audioLocalRepository);

        userService = new UserService(toolBoxService,passwordService);
        temporaryPlaylistService = new TemporaryPlaylistService(toolBoxService,userService);
        songService = new SongService(toolBoxService);
        playlistFunctionalitiesService = new PlaylistFunctionalitiesService(toolBoxService, userService, songService);
        playlistService = new PlaylistServices(toolBoxService, playlistFunctionalitiesService, temporaryPlaylistService);
        artistService = new ArtistService(toolBoxService);
        printService = new PrintService(songService, artistService, playlistService, userService);
        searchService = new SearchService(songService, printService);
        playlistReorderSongService = new PlaylistReorderSongService(toolBoxService, scanner);
        uniqueIdService = new UniqueIdService();
        songService = new SongService(toolBoxService);

        fakeMusicPlayer = new FakeMusicPlayer();
        playlistPlayer = new PlaylistPlayer(
                fakeMusicPlayer, audioLocalRepository, songService, playlistService);

        toolBoxView = new ToolBoxView(playlistService, userService, songService, artistService, printService,
                searchService, passwordService, playlistReorderSongService, temporaryPlaylistService, uniqueIdService, passwordService);

        pageService = new PageService(playlistPlayer, toolBoxView, userService, menuPagesStack);


        //SOCKETS
        socketClient = new SocketClient();
        frontUserRepo = new FrontUserRepo(socketClient);
        frontPlaylistRepo = new FrontPlaylistRepo(socketClient);
        frontSongRepo = new FrontSongRepo(socketClient);
        frontArtistRepo = new FrontArtistRepo(socketClient);
        frontAudioRepo = new FrontAudioRepo();

        backAudioRepo = new BackAudioRepo(userLocalRepository);
        backPlaylistRepo = new BackPlaylistRepo(playlistLocalRepository, userLocalRepository);
        backUserRepo = new BackUserRepo(userLocalRepository);
        backArtistRepo = new BackArtistRepo(artistLocalRepository, userLocalRepository);
        backSongRepo = new BackSongRepo(songLocalRepository, userLocalRepository);

        audioSocketServer = new AudioSocketServer(backAudioRepo);
        socketServer = new SocketServer(backUserRepo, backPlaylistRepo, backSongRepo, backArtistRepo);
    }

    public void populateUsers() {
        User marion = new User("marion", "marion@example.com", "hash", PlanEnum.FREE);
        marion.setUserId(232928320);

        User florent = new User("florent", "florent@example.com", "hash", PlanEnum.FREE);
        florent.setUserId(1726370281);

        userLocalRepository.saveUser(marion);
        userLocalRepository.saveUser(florent);
    }
}
