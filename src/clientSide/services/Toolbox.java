package clientSide.services;

import serverSide.repositories.ArtistLocalRepository;
import serverSide.repositories.PlaylistLocalRepository;
import serverSide.repositories.SongLocalRepository;
import serverSide.repositories.UserLocalRepository;

public class Toolbox {
    UserLocalRepository userLocalRepository;
    PlaylistLocalRepository playlistLocalRepository;
    ArtistLocalRepository artistLocalRepository;
    SongLocalRepository songLocalRepository;

    PlaylistServices playlistServ;
    SongService songServ;
    SearchService searchService;
    UserService userServ;
    PrintService printServ;
    PasswordService passwordServ;
    PlaylistReorderSongService playlistReorderSongService;
    ArtistService artistService;

    public Toolbox() {
        userLocalRepository = new UserLocalRepository();
        playlistLocalRepository = new PlaylistLocalRepository();
        artistLocalRepository = new ArtistLocalRepository();
        songLocalRepository = new SongLocalRepository();

        playlistServ = new PlaylistServices(playlistLocalRepository, songLocalRepository);
        userServ = new UserService(userLocalRepository);
        songServ = new SongService(songLocalRepository);
        searchService = new SearchService(songServ, artistLocalRepository, printServ);
        passwordServ = new PasswordService(userLocalRepository);
        printServ = new PrintService(songServ, artistService, playlistServ, userServ);
        playlistReorderSongService = new PlaylistReorderSongService();
        artistService = new ArtistService(artistLocalRepository);
    }

    public PlaylistServices getPlaylistServ() {
        return playlistServ;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public UserService getUserServ() {
        return userServ;
    }

    public PrintService getPrintServ() {
        return printServ;
    }

    public PasswordService getPasswordServ() {
        return passwordServ;
    }

    public PlaylistReorderSongService getPlaylistReorderSongService() {
        return playlistReorderSongService;
    }

    public SongService getSongServ(){
        return songServ;
    }

    public ArtistService getArtistServ() {
        return artistService;
    }
}
