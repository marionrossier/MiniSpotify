package utils;

import clientSide.services.*;
import clientSide.services.entities.ArtistService;
import clientSide.services.entities.PlaylistServices;
import clientSide.services.entities.SongService;
import clientSide.services.entities.UserService;
import clientSide.services.entities.playlist.PlaylistFunctionalitiesService;
import clientSide.services.entities.playlist.PlaylistReorderSongService;
import clientSide.services.entities.playlist.TemporaryPlaylistService;
import common.repository.*;
import common.services.StockageService;
import common.services.UniqueIdService;
import serverSide.repoLocal.*;
import clientSide.player.file_player.*;
import clientSide.player.playlist_player.*;

import javazoom.jlgui.basicplayer.BasicPlayer;

import java.util.Scanner;
import java.util.Stack;

public class CompositionRootPatternNoSocket {

    public static void main(String[] args) {
        CompositionRootPatternNoSocket compositionRoot = new CompositionRootPatternNoSocket();
        compositionRoot.startApp();
    }

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

    public CompositionRootPatternNoSocket(){

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

        playlistReorderSongService = new PlaylistReorderSongService();
        temporaryPlaylistService = new TemporaryPlaylistService(userService);

        playlistFunctionalitiesService = new PlaylistFunctionalitiesService(userService,
                songService);
        playlistServices = new PlaylistServices(toolBoxService, playlistFunctionalitiesService, temporaryPlaylistService, playlistReorderSongService);
        printService = new PrintService(songService, artistService, playlistServices, userService);
        searchService = new SearchService(songService, printService, userService);
        uniqueIdService = new UniqueIdService();

        musicPlayer = new MusicPlayer(audioLocalRepository, basicPlayer);
        spotifyPlayer = new PlaylistPlayer(musicPlayer, audioLocalRepository, songService, playlistServices, artistService);
        toolBoxView = new ToolBoxView(playlistServices, userService, songService, artistService,
                printService, searchService, passwordService, uniqueIdService);

        pageService = new PageService(spotifyPlayer, toolBoxView, userService, menuPagesStack);
    }

    public void startApp(){
        this.pageService.startLogin();
    }
}
