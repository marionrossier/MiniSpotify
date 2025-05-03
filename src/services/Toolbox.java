package services;

import data.jsons.ArtistRepository;
import data.jsons.PlaylistRepository;
import data.jsons.SongRepository;
import data.jsons.UserRepository;

public class Toolbox {
    UserRepository userRepo = new UserRepository();
    PlaylistRepository playlistRepo = new PlaylistRepository();
    ArtistRepository artistRepo = new ArtistRepository();
    SongRepository songRepo = new SongRepository();

    PlaylistServices playlistServ = new PlaylistServices(playlistRepo);
    SongService songServ = new SongService(songRepo);
    UserService userServ = new UserService(userRepo);
    PrintService printServ = new PrintService();
    PasswordService passwordServ = new PasswordService();

    public Toolbox() {
    }

    public PlaylistServices getPlaylistServ() {
        return playlistServ;
    }

    public PlaylistRepository getPlaylistRepo() {
        return playlistRepo;
    }

    public SongService getSongServ() {
        return songServ;
    }

    public SongRepository getSongRepo() {
        return songRepo;
    }

    public UserService getUserServ() {
        return userServ;
    }

    public UserRepository getUserRepo() {
        return userRepo;
    }

    public PrintService getPrintServ() {
        return printServ;
    }

    public PasswordService getPasswordServ() {
        return passwordServ;
    }

    public ArtistRepository getArtistRepo() {
        return artistRepo;
    }
}
