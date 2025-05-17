package clientSide.services;

public class ToolBoxView {

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
                       TemporaryPlaylistService temporaryPlaylistService, UniqueIdService uniqueIdService) {

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
