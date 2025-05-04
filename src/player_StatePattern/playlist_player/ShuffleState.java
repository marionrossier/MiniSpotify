package player_StatePattern.playlist_player;

import data.entities.Song;
import java.util.LinkedList;

class ShuffleState implements IState {
    private final PlaylistPlayer context;

    public ShuffleState(PlaylistPlayer spotifyService) {
        this.context = spotifyService;
    }

    @Override
    public Song getNextSong() {
        LinkedList<Integer> songsId = context.playlistRepository.getPlaylistById(context.currentPlaylist.getPlaylistId()).getPlaylistSongsListWithId();
        int nextIndex = (int) (Math.random() * songsId.size());
        int nextSongId = songsId.get(nextIndex);
        //TODO : Check if the song is already played and avoid playing it again until all songs are played
        // Be careful if single song playlist
        return context.songRepository.getSongById(nextSongId);
    }

    @Override
    public Song getPreviousSong() {
        return null;
    }
}
