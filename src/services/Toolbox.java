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
    UserService userServ;
    PrintService printServ;
    PasswordService passwordServ;
    PlaylistReorderSongService playlistReorderSongService;

    public Toolbox() {
        userRepo = new UserRepository();
        playlistRepo = new PlaylistRepository();
        artistRepo = new ArtistRepository();
        songRepo = new SongRepository();

        playlistServ = new PlaylistServices(playlistRepo);
        userServ = new UserService(userRepo);
        songServ = new SongService(songRepo);
        passwordServ = new PasswordService(userRepo);
        printServ = new PrintService();
        playlistReorderSongService = new PlaylistReorderSongService();
    }

    public PlaylistServices getPlaylistServ() {
        return playlistServ;
    }

    public SongService getSongServ() {
        return songServ;
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
}
