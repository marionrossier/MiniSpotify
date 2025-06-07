package clientSide.services;

import common.services.UniqueIdService;

public class ToolBoxView {

    final PlaylistServices playlistServ;
    final SongService songServ;
    final SearchService searchServ;
    final UserService userServ;
    final PrintService printServ;
    final PasswordGenerator passwordServ;
    final ArtistService artistServ;
    final UniqueIdService uniqueIdServ;
    final AuthentificationService authentificationServ;

    public ToolBoxView(PlaylistServices playlistServices, UserService userService, SongService songService,
                       ArtistService artistServ, PrintService printService, SearchService searchServ,
                       PasswordGenerator passwordGenerator, UniqueIdService uniqueIdService,
                       AuthentificationService authentificationService) {

        this.playlistServ = playlistServices;
        this.userServ = userService;
        this.songServ = songService;
        this.artistServ = artistServ;
        this.printServ = printService;
        this.searchServ = searchServ;
        this.passwordServ = passwordGenerator;
        this.uniqueIdServ = uniqueIdService;
        this.authentificationServ = authentificationService;
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

    public SongService getSongServ(){
        return songServ;
    }

    public ArtistService getArtistServ() {
        return artistServ;
    }

    public AuthentificationService getAuthentificationService() {
        return authentificationServ;
    }
}
