package clientSide.repoFront;

import serverSide.entities.Playlist;
import serverSide.entities.PlaylistEnum;
import middle.IPlaylistRepository;

import java.util.List;

public class FrontPlaylistRepo implements IPlaylistRepository {
    @Override
    public List<Playlist> getAllPlaylists() {
        return List.of();
    }

    @Override
    public void savePlaylist(Playlist playlist) {

    }

    @Override
    public void deletePlaylistById(int playlistId) {

    }

    @Override
    public Playlist getPlaylistById(int playlistId) {
        return null;
    }

    @Override
    public Playlist getPlaylistByName(String name) {
        return null;
    }

    @Override
    public PlaylistEnum getPlaylistStatus(Playlist playlist) {
        return null;
    }

    @Override
    public Playlist getTemporaryPlaylistOfCurrentUser(int currentUserId) {
        return null;
    }
}
