package clientSide.services;

import common.repository.*;

public class ToolBoxService {

    public final IUserRepository userRepository;
    public final IPlaylistRepository playlistRepository;
    public final ISongRepository songRepository;
    public final IArtistRepository artistRepository;
    public final IAudioRepository audioRepository;


    public ToolBoxService(IPlaylistRepository playlistRepository,
                          IUserRepository userRepository,
                          ISongRepository songRepository,
                          IArtistRepository artistRepository,
                          IAudioRepository audioRepository){

        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.audioRepository = audioRepository;
    }
}
