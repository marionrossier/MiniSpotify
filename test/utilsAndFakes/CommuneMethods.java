package utilsAndFakes;

import middle.*;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.player_StatePattern.playlist_player.PlaylistPlayer;
import clientSide.services.*;
import serverSide.StockageService;
import serverSide.entities.*;
import serverSide.repoLocal.*;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Supplier;

public abstract class CommuneMethods {

    Scanner scanner = new Scanner(System.in);

    static Thread serverThread;

    protected File tempPlaylistsFile;
    protected File tempSongsFile;
    protected File tempUsersFile;
    protected File tempArtistFile;

    protected IPlaylistRepository playlistLocalRepository;
    protected ISongRepository songLocalRepository;
    protected IUserRepository userLocalRepository;
    protected IAudioRepository audioLocalRepository;
    protected IArtistRepository artistLocalRepository;

    protected ToolBoxService toolBoxService;
    protected PlaylistServices playlistService;
    protected StockageService stockageService;
    protected PlaylistFunctionalitiesService playlistFunctionalitiesService;
    protected TemporaryPlaylistService temporaryPlaylistService;
    protected UserService userService;
    protected PasswordService passwordService;
    protected ArtistService artistService;
    protected PrintService printService;
    protected SearchService searchService;
    protected PlaylistReorderSongService playlistReorderSongService;
    protected UniqueIdService uniqueIdService;
    protected SongService songService;

    protected ToolBoxView toolBoxView;

    protected IPlaylistPlayer playlistPlayer;
    protected FakeMusicPlayer fakeMusicPlayer;

    protected PageService pageService;

    public final Stack<Integer> menuPagesStack;


    public CommuneMethods() throws IOException {

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

    }

    public void addSongToPlaylist(int currentPlaylistId, int currentSongId, IPlaylistRepository playlistLocalRepository,
                                  PlaylistServices playlistServices) {
        Playlist playlist = playlistLocalRepository.getPlaylistById(currentPlaylistId);
        playlist.getPlaylistSongsListWithId().add(currentSongId);
        int playlistDuration = playlistServices.setDurationSeconds(playlist.getPlaylistId());
        int playlistSize = playlist.getSize();
        playlist.setPlaylistInformation(playlistDuration, playlistSize);

        playlistLocalRepository.savePlaylist(playlist);
    }

    public void addSongsToPlaylist(Playlist playlist, int... songIds) {
        for (int id : songIds) {
            Song song = createTestSong(id, "Song " + id);
            this.addSongToPlaylist(playlist.getPlaylistId(), song.getSongId(), playlistLocalRepository, playlistService);
        }
    }

    protected Song createSong(int id, String title, String fileName) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);
        song.setAudioFileName(fileName);
        return song;
    }

    public Playlist createTestPlaylist(int id, String name, IPlaylistRepository playlistLocalRepository) {
        Playlist playlist = new Playlist(name, PlaylistEnum.PRIVATE);
        playlist.setPlaylistId(id);
        playlistLocalRepository.savePlaylist(playlist);
        return playlist;
    }

    private Song createTestSong(int id, String title) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);
        song.setDurationSeconds(180);
        return song;
    }

    public Song createTestSong(int id, String title, String artistName, MusicGender gender,
                               IArtistRepository artistLocalRepository) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);

        Artist artist = new Artist(artistName);
        artist.setArtistId(100 + id); // Unique ID for artist
        artistLocalRepository.saveArtist(artist);

        song.setArtistId(artist.getArtistId());

        song.setDurationSeconds(180);
        song.setGender(gender);

        return song;
    }

    public <T> T startServerAndInitRepo(Supplier<T> repoSupplier) {
        try (Socket testSocket = new Socket("127.0.0.1", 45000)) {
            System.out.println("✅ Serveur déjà actif.");
        } catch (IOException e) {
            serverThread = new Thread(() -> {
                try {
                    serverSide.socket.SocketServer.main(null);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            serverThread.setDaemon(true);
            serverThread.start();

            try {
                Thread.sleep(1000); // Laisse le temps au serveur de démarrer
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        Cookies_SingletonPattern.setUser(232928320);
        return repoSupplier.get();
    }

}