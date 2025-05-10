package clientSide.services;

import clientSide.repositories.ArtistRepository;
import clientSide.repositories.PlaylistRepository;
import clientSide.repositories.SongRepository;
import clientSide.repositories.UserRepository;

public class Toolbox {
    UserRepository userRepo;
    PlaylistRepository playlistRepo;
    ArtistRepository artistRepo;
    SongRepository songRepo;

    PlaylistServices playlistServ;
    SongService songServ;
    SearchService searchService;
    UserService userServ;
    PrintService printServ;
    PasswordService passwordServ;
    PlaylistReorderSongService playlistReorderSongService;

    public Toolbox() {
        userRepo = new UserRepository();
        playlistRepo = new PlaylistRepository();
        artistRepo = new ArtistRepository();
        songRepo = new SongRepository();

        playlistServ = new PlaylistServices(playlistRepo, songRepo);
        userServ = new UserService(userRepo);
        songServ = new SongService(songRepo);
        searchService = new SearchService(songRepo, songServ);
        passwordServ = new PasswordService(userRepo);
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
