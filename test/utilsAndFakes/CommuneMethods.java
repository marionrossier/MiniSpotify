package utilsAndFakes;

import middle.*;
import clientSide.services.*;
import serverSide.entities.*;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Supplier;

public abstract class CommuneMethods {

    public Initializer initializer;

    public CommuneMethods() throws IOException {
        this.initializer = new Initializer();
    }

    public void addSongToPlaylist(int currentPlaylistId, int currentSongId, IPlaylistRepository playlistLocalRepository,
                                  PlaylistServices playlistServices) {
        Playlist playlist = playlistLocalRepository.getPlaylistById(currentPlaylistId);
        playlist.getPlaylistSongsListWithId().add(currentSongId);
        int playlistDuration = playlistServices.setDurationSeconds(playlist.getPlaylistId());
        int playlistSize = playlist.getSize();
        playlist.setPlaylistInformation(playlistDuration, playlistSize);

        playlistLocalRepository.savePlaylist(playlist);
    }

    public void addSongsToPlaylist(Playlist playlist, int... songIds) {
        for (int id : songIds) {
            Song song = createTestSong(id, "Song " + id);
            this.addSongToPlaylist(playlist.getPlaylistId(),
                    song.getSongId(),
                    initializer.playlistLocalRepository,
                    initializer.playlistService);
        }
    }

    protected Song createSong(int id, String title, String fileName) {
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

    public <T> T startServerAndInitRepo(Supplier<T> repoSupplier) {
        try (Socket testSocket = new Socket("127.0.0.1", 45000)) {
            System.out.println("✅ Serveur déjà actif.");
        } catch (IOException e) {
            initializer.serverThread = new Thread(() -> {
                try {
                    initializer.socketServer.main();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            initializer.serverThread.setDaemon(true);
            initializer.serverThread.start();

            try {
                Thread.sleep(1000); // Laisse le temps au serveur de démarrer
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        Cookies_SingletonPattern.setUser(232928320);
        return repoSupplier.get();
    }
}