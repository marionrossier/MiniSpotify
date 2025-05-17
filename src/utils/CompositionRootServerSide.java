package utils;

import commun.*;
import serverSide.repoBack.*;
import serverSide.repoLocal.*;
import serverSide.socket.AudioSocketServer;
import serverSide.socket.SocketServer;

public class CompositionRootServerSide {

    //Json-Mp3
    StockageService stockageService;

    //Repositories
    final IPlaylistRepository playlistLocalRepository;
    final IUserRepository userLocalRepository;
    final ISongRepository songLocalRepository;
    final IArtistRepository artistLocalRepository;
    final IAudioRepository audioLocalRepository;

    final BackPlaylistRepo backPlaylistRepo;
    final BackUserRepo backUserRepo;
    final BackSongRepo backSongRepo;
    final BackArtistRepo backArtistRepo;
    final BackAudioRepo backAudioRepo;

    public CompositionRootServerSide(){
        //Json-Mp3
        stockageService = new StockageService();

        //Repositories
        playlistLocalRepository = new PlaylistLocalRepository();
        userLocalRepository = new UserLocalRepository();
        artistLocalRepository = new ArtistLocalRepository();
        songLocalRepository = new SongLocalRepository(stockageService, artistLocalRepository);
        audioLocalRepository = new AudioLocalRepository();

        backPlaylistRepo = new BackPlaylistRepo(playlistLocalRepository, userLocalRepository);
        backUserRepo = new BackUserRepo(userLocalRepository);
        backArtistRepo = new BackArtistRepo(artistLocalRepository, userLocalRepository);
        backSongRepo = new BackSongRepo(songLocalRepository, userLocalRepository);
        backAudioRepo = new BackAudioRepo(userLocalRepository);
    }

    public void startApp(SocketServer socketServer, AudioSocketServer audioSocketServer) {
        new Thread(socketServer::socketServerMain).start();
        new Thread(audioSocketServer::audioSocketMain).start();

        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
