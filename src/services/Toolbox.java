package services;

import data.jsons.ArtistRepository;
import data.jsons.PlaylistRepository;
import data.jsons.SongRepository;
import data.jsons.UserRepository;

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
    TemporaryPlaylistService temporaryPlaylistServ;

    public Toolbox() {
        userRepo = new UserRepository();
        playlistRepo = new PlaylistRepository();
        artistRepo = new ArtistRepository();
        songRepo = new SongRepository();

        playlistServ = new PlaylistServices(playlistRepo);
        userServ = new UserService(userRepo);
        songServ = new SongService(songRepo);
        searchService = new SearchService(songRepo);
        passwordServ = new PasswordService(userRepo);
        printServ = new PrintService();
        playlistReorderSongService = new PlaylistReorderSongService();
        temporaryPlaylistServ = new TemporaryPlaylistService(playlistRepo, userRepo);
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

    public TemporaryPlaylistService getTemporaryPlaylistServ() {
        return temporaryPlaylistServ;
    }
}
