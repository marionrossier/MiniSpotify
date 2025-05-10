package services;

import clientSide.entities.Playlist;
import clientSide.entities.PlaylistEnum;
import clientSide.entities.Song;
import clientSide.repositories.PlaylistRepository;
import clientSide.services.PlaylistServices;

public class CommuneMethods {

    public void addSongToPlaylist(int currentPlaylistId, int currentSongId, PlaylistRepository playlistRepository,
                                  PlaylistServices playlistServices) {
        Playlist playlist = playlistRepository.getPlaylistById(currentPlaylistId);
        playlist.getPlaylistSongsListWithId().add(currentSongId);
        int playlistDuration = playlistServices.setDurationSeconds(playlist.getPlaylistId());
        int playlistSize = playlist.getSize();
        playlist.setPlaylistInformation(playlistDuration, playlistSize);

        playlistRepository.savePlaylist(playlist);
    }

    Song createSong(int id, String title, String path) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);
        song.setAudioFilePath(path);
        return song;
    }

    public Playlist createTestPlaylist(int id, String name, PlaylistRepository playlistRepository) {
        Playlist playlist = new Playlist(name, PlaylistEnum.PRIVATE);
        playlist.setPlaylistId(id);
        playlistRepository.savePlaylist(playlist);
        return playlist;
    }

    private Song createTestSong(int id, String title) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);
        song.setDurationSeconds(180);
        return song;
    }

    public void addSongsToPlaylist(Playlist playlist, PlaylistRepository playlistRepository, PlaylistServices playlistServices, int... songIds) {
        for (int id : songIds) {
            Song song = createTestSong(id, "Song " + id);
            this.addSongToPlaylist(playlist.getPlaylistId(), song.getSongId(), playlistRepository, playlistServices);
        }
    }
}