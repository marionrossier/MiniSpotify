package middle;

import serverSide.entities.Playlist;
import serverSide.entities.PlaylistEnum;

import java.util.List;

public interface IPlaylistRepository {

    List<Playlist> getAllPlaylists();
    void savePlaylist(Playlist playlist);
    void deletePlaylistById(int playlistId);
    Playlist getPlaylistById(int playlistId);
    Playlist getPlaylistByName(String name);
    PlaylistEnum getPlaylistStatus(Playlist playlist);
    Playlist getTemporaryPlaylistOfCurrentUser(int currentUserId);
}
