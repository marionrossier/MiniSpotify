import data.jsons.PlaylistRepository;
import data.jsons.SongRepository;
import player_StatePattern.file_player.MusicPlayer;
import player_StatePattern.file_player.IMusicPlayer;
import player_StatePattern.playlist_player.IPlaylistPlayer;
import player_StatePattern.playlist_player.PlaylistPlayer;
import services.PageService;

public class Spotify {
    public static void startApp(){
        PlaylistRepository playlistRepository = new PlaylistRepository();
        SongRepository songRepository = new SongRepository();
        IMusicPlayer musicPlayer = new MusicPlayer();

        IPlaylistPlayer spotifyPlayer = new PlaylistPlayer(musicPlayer, songRepository, playlistRepository);

        PageService pageService = new PageService();
        pageService.spotifyPlayer = spotifyPlayer; // Initialisation de SpotifyPlayer dans la factory

        pageService.setUpPages();
        pageService.startLogin();
    }
}
