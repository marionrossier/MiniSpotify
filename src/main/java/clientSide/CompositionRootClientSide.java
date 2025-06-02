package clientSide;

import clientSide.repoFront.*;
import clientSide.services.*;
import clientSide.player.file_player.*;
import clientSide.player.playlist_player.*;
import clientSide.services.ArtistService;
import clientSide.services.PlaylistServices;
import clientSide.services.SongService;
import clientSide.services.UserService;
import clientSide.services.playlist.PlaylistFunctionalitiesService;
import clientSide.services.playlist.PlaylistReorderSongService;
import clientSide.services.playlist.TemporaryPlaylistService;
import clientSide.socket.*;

import common.repository.*;
import common.services.UniqueIdService;
import javazoom.jlgui.basicplayer.BasicPlayer;

import java.util.Scanner;
import java.util.Stack;

public class CompositionRootClientSide {

    public static void main(String[] args) {
        CompositionRootClientSide compositionRoot = new CompositionRootClientSide();
        compositionRoot.startApp();
    }

    Scanner scanner = new Scanner(System.in);
    public final Stack<Integer> menuPagesStack = new Stack<>();

    //Repositories
    final IPlaylistRepository frontPlaylistRepo;
    final IUserRepository frontUserRepo;
    final ISongRepository frontSongRepo;
    final IArtistRepository frontArtistRepo;
    final IAudioRepository frontAudioRepo;

    final SocketClient socketClient;

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

    public CompositionRootClientSide(){

        //Json-Mp3
        basicPlayer = new BasicPlayer();

        //Repositories
        socketClient = new SocketClient();
        frontPlaylistRepo = new FrontPlaylistRepo(socketClient);
        frontUserRepo = new FrontUserRepo(socketClient);
        frontArtistRepo = new FrontArtistRepo(socketClient);
        frontSongRepo = new FrontSongRepo(socketClient);
        frontAudioRepo = new FrontAudioRepo();

        toolBoxService = new ToolBoxService(frontPlaylistRepo, frontUserRepo, frontSongRepo,
                frontArtistRepo, frontAudioRepo);

        //Services
        passwordService = new PasswordService(frontUserRepo);

        userService = new UserService(toolBoxService, passwordService);
        artistService = new ArtistService(toolBoxService);
        songService = new SongService(toolBoxService);

        playlistReorderSongService = new PlaylistReorderSongService();
        temporaryPlaylistService = new TemporaryPlaylistService(userService);

        musicPlayer = new MusicPlayer(frontAudioRepo, basicPlayer);
        playlistFunctionalitiesService = new PlaylistFunctionalitiesService(userService,
                songService);
        playlistServices = new PlaylistServices(toolBoxService, playlistFunctionalitiesService, temporaryPlaylistService, playlistReorderSongService);
        spotifyPlayer = new PlaylistPlayer(musicPlayer, songService, playlistServices, artistService);
        printService = new PrintService(songService, artistService, playlistServices, userService);
        searchService = new SearchService(songService, printService, userService);
        uniqueIdService = new UniqueIdService();
        toolBoxView = new ToolBoxView(playlistServices, userService, songService, artistService,
                printService, searchService, passwordService, uniqueIdService);
        pageService = new PageService(spotifyPlayer, toolBoxView, userService, menuPagesStack);
    }

    public void startApp(){
        this.pageService.startLogin();
    }
}
