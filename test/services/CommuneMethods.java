package services;

import serverSide.entities.*;
import serverSide.repositories.ArtistRepository;
import serverSide.repositories.AudioRepository;
import serverSide.repositories.PlaylistRepository;
import clientSide.services.PlaylistServices;
import serverSide.repositories.SongRepository;

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

    Song createSong(int id, String title, String path, AudioRepository audioRepository) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);
        audioRepository.setAudioFilePathAndName(id,path);
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

    public Song createTestSong(int id, String title, String artistName, MusicGender gender,
                               ArtistRepository artistRepository, AudioRepository audioRepository) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);

        Artist artist = new Artist(artistName);
        artist.setArtistId(100 + id); // Unique ID for artist
        artistRepository.saveArtist(artist);

        song.setArtistId(artist.getArtistId());

        song.setDurationSeconds(180);
        song.setGender(gender);
        audioRepository.setAudioFilePathAndName(id, "path/to/song" + id + ".mp3");

        return song;
    }
}