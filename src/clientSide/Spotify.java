package clientSide;

import clientSide.repositories.PlaylistRepository;
import clientSide.repositories.SongRepository;
import clientSide.player_StatePattern.file_player.MusicPlayer;
import clientSide.player_StatePattern.file_player.IMusicPlayer;
import clientSide.player_StatePattern.playlist_player.IPlaylistPlayer;
import clientSide.player_StatePattern.playlist_player.PlaylistPlayer;
import clientSide.services.PageService;

public class Spotify {
    public static void startApp(){
        PlaylistRepository playlistRepository = new PlaylistRepository();
        SongRepository songRepository = new SongRepository();
        IMusicPlayer musicPlayer = new MusicPlayer();

        IPlaylistPlayer spotifyPlayer = new PlaylistPlayer(musicPlayer, songRepository, playlistRepository);

        PageService pageService = new PageService(spotifyPlayer);

        pageService.startLogin();
    }
}
