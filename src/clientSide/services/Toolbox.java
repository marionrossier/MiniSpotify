package clientSide.services;

import serverSide.repositories.ArtistRepository;
import serverSide.repositories.PlaylistRepository;
import serverSide.repositories.SongRepository;
import serverSide.repositories.UserRepository;

public class Toolbox {
    UserRepository userRepository;
    PlaylistRepository playlistRepository;
    ArtistRepository artistRepository;
    SongRepository songRepository;

    PlaylistServices playlistServ;
    SongService songServ;
    SearchService searchService;
    UserService userServ;
    PrintService printServ;
    PasswordService passwordServ;
    PlaylistReorderSongService playlistReorderSongService;

    public Toolbox() {
        userRepository = new UserRepository();
        playlistRepository = new PlaylistRepository();
        artistRepository = new ArtistRepository();
        songRepository = new SongRepository();

        playlistServ = new PlaylistServices(playlistRepository, songRepository);
        userServ = new UserService(userRepository);
        songServ = new SongService(songRepository);
        searchService = new SearchService(songRepository, songServ, artistRepository);
        passwordServ = new PasswordService(userRepository);
        printServ = new PrintService();
        playlistReorderSongService = new PlaylistReorderSongService();
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
}
