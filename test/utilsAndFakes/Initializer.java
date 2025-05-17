package utilsAndFakes;

import commun.StockageService;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.player_StatePattern.playlist_player.PlaylistPlayer;
import clientSide.repoFront.*;
import clientSide.services.*;
import clientSide.socket.SocketClient;
import commun.*;
import serverSide.entities.*;
import serverSide.repoBack.*;
import serverSide.repoLocal.*;
import serverSide.socket.AudioSocketServer;
import serverSide.socket.SocketServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
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

    public Stack<Integer> menuPagesStack;

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
        searchService = new SearchService(songService, printService, userService);
        playlistReorderSongService = new PlaylistReorderSongService(toolBoxService, scanner);
        uniqueIdService = new UniqueIdService();
        songService = new SongService(toolBoxService);

        fakeMusicPlayer = new FakeMusicPlayer();
        playlistPlayer = new PlaylistPlayer(
                fakeMusicPlayer, audioLocalRepository, songService, playlistService);

        toolBoxView = new ToolBoxView(playlistService, userService, songService, artistService, printService,
                searchService, passwordService, playlistReorderSongService, temporaryPlaylistService, uniqueIdService);

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

    public void populateLocalUsers() {
        User marion = new User("marion", "marion@example.com", "hash", PlanEnum.FREE);
        marion.setUserId(232928320);

        User florent = new User("florent", "florent@example.com", "hash", PlanEnum.FREE);
        florent.setUserId(1726370281);

        userLocalRepository.saveUser(marion);
        userLocalRepository.saveUser(florent);
    }

    public void populateLocalArtist(){
        LinkedList<Integer> amy = new LinkedList<>(Arrays.asList(1108071776,342105258,625427469,661206135));
        Artist amyWinehouse = new Artist(960571432, "Amy Winehouse", amy);

        artistLocalRepository.saveArtist(amyWinehouse);
    }

    public void populateLocalSong(){
        Song song = new Song(1108071776, "Rehab",
                artistLocalRepository.getArtistByName("Amy Winehouse").getArtistId() ,
                214, MusicGender.SOUL_RNB,
                "Rehab - Amy Winehouse - Back to Black - 2006 - Soul _ R&B - 0334.mp3");

        songLocalRepository.addSong(song);
    }

    public void populateLocalPlaylists(){
        Playlist playlist1 = new Playlist("Girls", 1940298216,
                new LinkedList<>(Arrays.asList(1108071776, 342105258, 625427469, 661206135, 1986076679, 2084461505,
                        1290739974, 1951451340, 469321884, 1252829874, 1988790520, 700468481)),
                2754, 12, 232928320, PlaylistEnum.PUBLIC);

        Playlist playlist2 = new Playlist("Rock Legends", 546441990,
                new LinkedList<>(Arrays.asList(243871940, 1824616046, 1287974581, 614172035, 494087492, 515539482)),
                1317, 6, 1, PlaylistEnum.PUBLIC);

        playlistLocalRepository.savePlaylist(playlist1);
        playlistLocalRepository.savePlaylist(playlist2);
    }

    public void cleanUp() {
        if (tempPlaylistsFile.exists()) {
            tempPlaylistsFile.delete();
        }
        if (tempSongsFile.exists()) {
            tempSongsFile.delete();
        }
        if (tempUsersFile.exists()) {
            tempUsersFile.delete();
        }
        if (tempArtistFile.exists()){
            tempArtistFile.delete();
        }
    }
}
