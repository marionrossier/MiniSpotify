package player_StatePattern.playlist_player;

import data.entities.Song;
import java.util.LinkedList;

class ShuffleState implements IState {
    private final PlaylistPlayer playlistPlayer;

    public ShuffleState(PlaylistPlayer spotifyService) {
        this.playlistPlayer = spotifyService;
    }

    @Override
    public Song getNextSong() {
        LinkedList<Integer> songsId = playlistPlayer.playlistRepository
                .getPlaylistById(playlistPlayer.getCurrentPlaylistId())
                .getPlaylistSongsListWithId();
        int nextIndex = (int) (Math.random() * songsId.size());
        int nextSongId = songsId.get(nextIndex);

        return playlistPlayer.songRepository.getSongById(nextSongId);
    }

    @Override
    public Song getPreviousSong() {
        return null;
    }
}
