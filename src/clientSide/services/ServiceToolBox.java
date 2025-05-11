package clientSide.services;

import serverSide.repositories.*;

public class ServiceToolBox {

    public final UserLocalRepository userLocalRepository;
    public final PlaylistLocalRepository playlistLocalRepository;
    public final SongLocalRepository songLocalRepository;
    public final ArtistLocalRepository artistLocalRepository;
    public final IAudioRepository audioLocalRepository;


    public ServiceToolBox (PlaylistLocalRepository playlistLocalRepository,
                           UserLocalRepository userLocalRepository,
                           SongLocalRepository songLocalRepository,
                           ArtistLocalRepository artistLocalRepository,
                           IAudioRepository audioLocalRepository){

        this.playlistLocalRepository = playlistLocalRepository;
        this.userLocalRepository = userLocalRepository;
        this.songLocalRepository = songLocalRepository;
        this.artistLocalRepository = artistLocalRepository;
        this.audioLocalRepository = audioLocalRepository;
    }
}
