package clientSide.services;

public class ToolBoxView {
    public static final String PRINT_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    final PlaylistServices playlistServ;
    final SongService songServ;
    final SearchService searchServ;
    final UserService userServ;
    final PrintService printServ;
    final PasswordService passwordServ;
    final PlaylistReorderSongService playlistReorderSongServ;
    final ArtistService artistServ;
    final TemporaryPlaylistService temporaryPlaylistService;
    final UniqueIdService uniqueIdServ;

    public ToolBoxView(PlaylistServices playlistServices, UserService userService, SongService songService,
                       ArtistService artistServ, PrintService printService, SearchService searchServ,
                       PasswordService passwordService, PlaylistReorderSongService playlistReorderSongServ,
                       TemporaryPlaylistService temporaryPlaylistService, UniqueIdService uniqueIdService, PasswordService service) {

        this.playlistServ = playlistServices;
        this.userServ = userService;
        this.songServ = songService;
        this.artistServ = artistServ;
        this.printServ = printService;
        this.searchServ = searchServ;
        this.passwordServ = passwordService;
        this.playlistReorderSongServ = playlistReorderSongServ;
        this.temporaryPlaylistService = temporaryPlaylistService;
        this.uniqueIdServ = uniqueIdService;

    }

    public PlaylistServices getPlaylistServ() {
        return playlistServ;
    }

    public SearchService getSearchServ() {
        return searchServ;
    }

    public UserService getUserServ() {
        return userServ;
    }

    public PrintService getPrintServ() {
        return printServ;
    }

    public PlaylistReorderSongService getPlaylistReorderSongServ() {
        return playlistReorderSongServ;
    }

    public SongService getSongServ(){
        return songServ;
    }

    public ArtistService getArtistServ() {
        return artistServ;
    }

    public PasswordService getPasswordServ() {
        return passwordServ;
    }
}
