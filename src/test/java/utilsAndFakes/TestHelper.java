package utilsAndFakes;

import java.io.IOException;
import java.net.Socket;

import clientSide.services.*;
import common.*;

import common.entities.*;

public class TestHelper {

    private int serverPort;
    public DependencyProvider dependencyProvider;

    public TestHelper(int serverPort) {
        this.serverPort = serverPort;
        try {
            this.dependencyProvider = new DependencyProvider(serverPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSongToPlaylist(int currentPlaylistId, int currentSongId, IPlaylistRepository playlistLocalRepository) {
        Playlist playlist = playlistLocalRepository.getPlaylistById(currentPlaylistId);
        playlist.getPlaylistSongsListWithId().add(currentSongId);

        playlistLocalRepository.updateOrInsertPlaylist(playlist);
    }

    public void addSongsToPlaylist(Playlist playlist, int... songIds) {
        for (int id : songIds) {
            Song song = createTestSong(id, "Song " + id);
            this.addSongToPlaylist(playlist.getPlaylistId(),
                    song.getSongId(),
                    dependencyProvider.playlistLocalRepository
            );
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
        playlistLocalRepository.updateOrInsertPlaylist(playlist);
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
        artistLocalRepository.updateOrInsertArtist(artist);

        song.setArtistId(artist.getArtistId());

        song.setDurationSeconds(180);
        song.setGender(gender);

        return song;
    }

    public void startServer() {
        try (Socket testSocket = new Socket("127.0.0.1", serverPort)) {
            System.out.println("✅ Server all ready running.");
        } catch (IOException e) {
            dependencyProvider.serverThread = new Thread(() -> {
                try {
                    dependencyProvider.socketServer.setPort(serverPort);
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
        Cookies.initializeInstance(232928320, "marion", "hash");
    }
}