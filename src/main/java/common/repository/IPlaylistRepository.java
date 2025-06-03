package common.repository;

import common.entities.Playlist;
import common.entities.PlaylistEnum;

import java.util.List;

public interface IPlaylistRepository {

    List<Playlist> getAllPlaylists();
    void updateOrInsertPlaylist(Playlist playlist);
    void deletePlaylistById(int playlistId);
    Playlist getPlaylistById(int playlistId);
    Playlist getPlaylistByName(String name);
    PlaylistEnum getPlaylistStatus(Playlist playlist);
    Playlist getTemporaryPlaylistOfCurrentUser(int currentUserId);
}
