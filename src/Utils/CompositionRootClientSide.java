package Utils;

import clientSide.player_StatePattern.file_player.IMusicPlayer;
import clientSide.player_StatePattern.file_player.MusicPlayer;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.player_StatePattern.playlist_player.PlaylistPlayer;
import clientSide.repoFront.*;
import clientSide.services.*;
import javazoom.jlgui.basicplayer.BasicPlayer;
import middle.*;

import java.util.Scanner;
import java.util.Stack;

public class CompositionRootClientSide {

    Scanner scanner = new Scanner(System.in);
    public final Stack<Integer> menuPagesStack = new Stack<>();

    //Repositories
    final IPlaylistRepository frontPlaylistRepo;
    final IUserRepository frontUserRepo;
    final ISongRepository frontSongRepo;
    final IArtistRepository frontArtistRepo;
    final IAudioRepository frontAudioRepo;

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
        frontPlaylistRepo = new FrontPlaylistRepo();
        frontUserRepo = new FrontUserRepo();
        frontArtistRepo = new FrontArtistRepo();
        frontSongRepo = new FrontSongRepo();
        frontAudioRepo = new FrontAudioRepo();

        toolBoxService = new ToolBoxService(frontPlaylistRepo, frontUserRepo, frontSongRepo,
                frontArtistRepo, frontAudioRepo);

        //Services
        passwordService = new PasswordService(frontUserRepo);

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

        musicPlayer = new MusicPlayer(frontAudioRepo, basicPlayer);
        spotifyPlayer = new PlaylistPlayer(musicPlayer, frontAudioRepo, songService, playlistServices);
        toolBoxView = new ToolBoxView(playlistServices, userService, songService, artistService,
                printService, searchService, passwordService, playlistReorderSongService,
                temporaryPlaylistService, uniqueIdService, passwordService);

        pageService = new PageService(spotifyPlayer, toolBoxView, userService, menuPagesStack);
    }

    public void startApp(){
        this.pageService.startLogin();
    }
}
