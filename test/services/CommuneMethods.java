package services;

import serverSide.entities.*;
import serverSide.repositories.ArtistLocalRepository;
import serverSide.repositories.PlaylistLocalRepository;
import clientSide.services.PlaylistServices;

public class CommuneMethods {

    public void addSongToPlaylist(int currentPlaylistId, int currentSongId, PlaylistLocalRepository playlistLocalRepository,
                                  PlaylistServices playlistServices) {
        Playlist playlist = playlistLocalRepository.getPlaylistById(currentPlaylistId);
        playlist.getPlaylistSongsListWithId().add(currentSongId);
        int playlistDuration = playlistServices.setDurationSeconds(playlist.getPlaylistId());
        int playlistSize = playlist.getSize();
        playlist.setPlaylistInformation(playlistDuration, playlistSize);

        playlistLocalRepository.savePlaylist(playlist);
    }

    Song createSong(int id, String title, String fileName) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);
        song.setAudioFileName(fileName);
        return song;
    }

    public Playlist createTestPlaylist(int id, String name, PlaylistLocalRepository playlistLocalRepository) {
        Playlist playlist = new Playlist(name, PlaylistEnum.PRIVATE);
        playlist.setPlaylistId(id);
        playlistLocalRepository.savePlaylist(playlist);
        return playlist;
    }

    private Song createTestSong(int id, String title) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);
        song.setDurationSeconds(180);
        return song;
    }

    public void addSongsToPlaylist(Playlist playlist, PlaylistLocalRepository playlistLocalRepository, PlaylistServices playlistServices, int... songIds) {
        for (int id : songIds) {
            Song song = createTestSong(id, "Song " + id);
            this.addSongToPlaylist(playlist.getPlaylistId(), song.getSongId(), playlistLocalRepository, playlistServices);
        }
    }

    public Song createTestSong(int id, String title, String artistName, MusicGender gender,
                               ArtistLocalRepository artistLocalRepository) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);

        Artist artist = new Artist(artistName);
        artist.setArtistId(100 + id); // Unique ID for artist
        artistLocalRepository.saveArtist(artist);

        song.setArtistId(artist.getArtistId());

        song.setDurationSeconds(180);
        song.setGender(gender);

        return song;
    }
}