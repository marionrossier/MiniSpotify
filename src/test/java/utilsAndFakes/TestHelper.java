package utilsAndFakes;

import java.io.IOException;
import java.net.Socket;

import clientSide.services.*;
import commun.*;

import serverSide.entities.*;

public class TestHelper {

    public DependencyProvider dependencyProvider;

    public TestHelper() {
        try {
            this.dependencyProvider = new DependencyProvider();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSongToPlaylist(int currentPlaylistId, int currentSongId, IPlaylistRepository playlistLocalRepository,
                                  PlaylistServices playlistServices) {
        Playlist playlist = playlistLocalRepository.getPlaylistById(currentPlaylistId);
        playlist.getPlaylistSongsListWithId().add(currentSongId);

        playlistLocalRepository.savePlaylist(playlist);
    }

    public void addSongsToPlaylist(Playlist playlist, int... songIds) {
        for (int id : songIds) {
            Song song = createTestSong(id, "Song " + id);
            this.addSongToPlaylist(playlist.getPlaylistId(),
                    song.getSongId(),
                    dependencyProvider.playlistLocalRepository,
                    dependencyProvider.playlistService);
        }
    }

    public Song createSong(int id, String title, String fileName) {
        Song song = new Song();
        song.setSongId(id);
        song.setTitle(title);
        song.setAudioFileName(fileName);
        return song;
    }

    public Playlist createTestPlaylist(int id, String name, IPlaylistRepository playlistLocalRepository) {
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

    public Song createTestSong(int id, String title, String artistName, MusicGender gender,
                               IArtistRepository artistLocalRepository) {
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

    public void startServer() {
        try (Socket testSocket = new Socket("127.0.0.1", 45000)) {
            System.out.println("✅ Serveur déjà actif.");
        } catch (IOException e) {
            dependencyProvider.serverThread = new Thread(() -> {
                try {
                    dependencyProvider.socketServer.socketServerMain();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            dependencyProvider.serverThread.setDaemon(true);
            dependencyProvider.serverThread.start();

            try {
                Thread.sleep(1000); // Laisse le temps au serveur de démarrer
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        Cookies_SingletonPattern.setUser(232928320, "marion", "hash");
    }
}