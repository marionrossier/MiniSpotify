package clientSide;

import clientSide.repositories.Jsons;
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

    public static void copyJsons(){
        Jsons jsons = new Jsons();

        String directoryPath = jsons.createWritableDirectory("jsons");

        jsons.copyResourceToWritableLocation("jsons/artist.json", "artist.json");
        jsons.copyResourceToWritableLocation("jsons/user.json", "user.json");
        jsons.copyResourceToWritableLocation("jsons/song.json", "song.json");
        jsons.copyResourceToWritableLocation("jsons/playlist.json", "playlist.json");
    }
}
