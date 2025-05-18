package clientSide.services;

import common.*;

public class ToolBoxService {

    public final IUserRepository userLocalRepository;
    public final IPlaylistRepository playlistLocalRepository;
    public final ISongRepository songLocalRepository;
    public final IArtistRepository artistLocalRepository;
    public final IAudioRepository audioLocalRepository;


    public ToolBoxService(IPlaylistRepository playlistLocalRepository,
                          IUserRepository userLocalRepository,
                          ISongRepository songLocalRepository,
                          IArtistRepository artistLocalRepository,
                          IAudioRepository audioLocalRepository){

        this.playlistLocalRepository = playlistLocalRepository;
        this.userLocalRepository = userLocalRepository;
        this.songLocalRepository = songLocalRepository;
        this.artistLocalRepository = artistLocalRepository;
        this.audioLocalRepository = audioLocalRepository;
    }
}
